package ru.cbr.koh.app.service;

import ru.cbr.koh.app.validation.MigrationValidator;
import ru.cbr.koh.panes_storage.panels.permission_migration.excel.excelParser.FileReader;
import ru.cbr.koh.panes_storage.panels.permission_migration.information.domain.Information;
import ru.cbr.koh.panes_storage.panels.permission_migration.output.GeneratedFile;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.Permission;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.base_clases.ChangeLog;
import ru.cbr.koh.panes_storage.panels.permission_migration.permission.domain.dialog_objects.PermissionDialogObject;
import ru.cbr.koh.panes_storage.panels.permission_migration.profile.Profile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class PermissionMigrationService {

    public MigrationPreview previewFromDialogObjects(List<PermissionDialogObject> permissionDialogObjects,
                                                     List<Profile> profiles,
                                                     Information information) {
        MigrationValidator.validateInformation(information);
        MigrationValidator.validateMigrationInput(permissionDialogObjects);

        List<Permission> permissions = permissionDialogObjects.stream()
                .map(it -> new Permission(it, profiles))
                .toList();

        return buildPreview(information, permissions);
    }

    public MigrationPreview previewFromExcel(File file,
                                             char rowSelector,
                                             int profileStartColumn,
                                             Information information) {
        MigrationValidator.validateInformation(information);

        FileReader reader = new FileReader(file, rowSelector, profileStartColumn);
        List<Permission> permissions = reader.read();
       // MigrationValidator.validateMigrationInput(permissions);

        return buildPreview(information, permissions);
    }

    public void savePreview(MigrationPreview preview) {
        if (preview == null || preview.files().isEmpty()) {
            throw new IllegalArgumentException("Нет файлов для сохранения");
        }
        for (GeneratedFile file : preview.files()) {
            if (file.path() == null || file.content() == null) {
                throw new IllegalArgumentException("Некорректный файл для сохранения");
            }
        }
        saveFiles(preview.files());
    }

    private MigrationPreview buildPreview(Information information, List<Permission> permissions) {
        ChangeLog changeLog = new ChangeLog(information, permissions);
        return new MigrationPreview(changeLog.buildFiles());
    }

    private void saveFiles(List<GeneratedFile> files) {
        List<Path> tempFiles = new ArrayList<>();
        List<BackupFile> backups = new ArrayList<>();
        List<Path> replacedTargets = new ArrayList<>();

        for (GeneratedFile file : files) {
            try {
                Path parent = resolveParent(file.path());
                Path tempFile = Files.createTempFile(parent, file.path().getFileName().toString(), ".tmp");
                Files.writeString(tempFile, file.content());
                tempFiles.add(tempFile);
            } catch (IOException e) {
                cleanupPaths(tempFiles);
                throw new IllegalStateException("Не удалось подготовить временный файл для: " + file.path().toAbsolutePath(), e);
            }
        }

        try {
            for (int index = 0; index < files.size(); index++) {
                GeneratedFile file = files.get(index);
                Path target = file.path();
                Path tempFile = tempFiles.get(index);

                backups.add(createBackupIfExists(target));
                moveWithReplace(tempFile, target);
                replacedTargets.add(target);
            }
        } catch (IOException e) {
            rollback(replacedTargets, backups);
            throw new IllegalStateException("Не удалось сохранить набор файлов атомарно", e);
        } finally {
            cleanupPaths(tempFiles);
            cleanupBackups(backups);
        }
    }

    private Path resolveParent(Path path) {
        Path absolutePath = path.toAbsolutePath();
        Path parent = absolutePath.getParent();
        if (parent == null) {
            throw new IllegalStateException("Не удалось определить директорию для файла: " + absolutePath);
        }
        if (Files.notExists(parent)) {
            throw new IllegalStateException("Директория не существует: " + parent);
        }
        return parent;
    }

    private BackupFile createBackupIfExists(Path target) throws IOException {
        Path absoluteTarget = target.toAbsolutePath();
        if (Files.notExists(absoluteTarget)) {
            return new BackupFile(absoluteTarget, null);
        }

        Path backup = Files.createTempFile(resolveParent(absoluteTarget), absoluteTarget.getFileName().toString(), ".bak");
        Files.copy(absoluteTarget, backup, StandardCopyOption.REPLACE_EXISTING);
        return new BackupFile(absoluteTarget, backup);
    }

    void moveWithReplace(Path source, Path target) throws IOException {
        Path absoluteTarget = target.toAbsolutePath();
        try {
            Files.move(source, absoluteTarget, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            Files.move(source, absoluteTarget, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void rollback(List<Path> replacedTargets, List<BackupFile> backups) {
        for (int index = replacedTargets.size() - 1; index >= 0; index--) {
            Path target = replacedTargets.get(index);
            BackupFile backupFile = findBackup(backups, target);
            try {
                if (backupFile == null || backupFile.backupPath() == null) {
                    Files.deleteIfExists(target.toAbsolutePath());
                } else {
                    moveWithReplace(backupFile.backupPath(), backupFile.targetPath());
                }
            } catch (IOException ignored) {
                // Rollback is best-effort; the original failure is reported to the caller.
            }
        }
    }

    private BackupFile findBackup(List<BackupFile> backups, Path target) {
        Path absoluteTarget = target.toAbsolutePath();
        return backups.stream()
                .filter(backup -> backup.targetPath().equals(absoluteTarget))
                .findFirst()
                .orElse(null);
    }

    private void cleanupPaths(List<Path> paths) {
        for (Path path : paths) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException ignored) {
                // Temporary cleanup is best-effort.
            }
        }
    }

    private void cleanupBackups(List<BackupFile> backups) {
        for (BackupFile backup : backups) {
            if (backup.backupPath() == null) {
                continue;
            }
            try {
                Files.deleteIfExists(backup.backupPath());
            } catch (IOException ignored) {
                // Backup cleanup is best-effort.
            }
        }
    }

    private record BackupFile(Path targetPath, Path backupPath) {
    }
}
