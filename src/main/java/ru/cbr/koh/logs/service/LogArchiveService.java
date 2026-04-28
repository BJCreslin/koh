package ru.cbr.koh.logs.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class LogArchiveService {

    public Path unpackAndFindDossierLog(Path archivePath) {
        Path targetDir = Path.of("koh-log-viewer-db", "archives", stripZipExtension(archivePath.getFileName().toString()));
        unzip(archivePath, targetDir);
        return findDossierLog(targetDir);
    }

    private void unzip(Path archivePath, Path targetDir) {
        try {
            Files.createDirectories(targetDir);
            try (InputStream inputStream = Files.newInputStream(archivePath);
                 ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    Path outputPath = safeResolve(targetDir, entry.getName());
                    if (entry.isDirectory()) {
                        Files.createDirectories(outputPath);
                    } else {
                        Files.createDirectories(outputPath.getParent());
                        Files.copy(zipInputStream, outputPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    }
                    zipInputStream.closeEntry();
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось распаковать архив: " + archivePath.toAbsolutePath(), e);
        }
    }

    private Path safeResolve(Path targetDir, String entryName) throws IOException {
        String normalizedEntryName = normalizeZipEntryName(entryName);
        Path normalizedTargetDir = targetDir.toAbsolutePath().normalize();
        Path outputPath = normalizedTargetDir.resolve(normalizedEntryName).normalize();
        if (!outputPath.startsWith(normalizedTargetDir)) {
            throw new IOException("Zip entry выходит за пределы директории распаковки: " + entryName);
        }
        return outputPath;
    }

    private String normalizeZipEntryName(String entryName) {
        String normalized = entryName.replace('\\', '/');
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        return normalized;
    }

    private Path findDossierLog(Path targetDir) {
        try (var paths = Files.walk(targetDir)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals("dossier-ko.log"))
                    .filter(path -> path.toString().contains("opt2"))
                    .filter(path -> path.toString().contains("log"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("dossier-ko.log не найден в архиве"));
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось найти dossier-ko.log", e);
        }
    }

    private String stripZipExtension(String fileName) {
        if (fileName.endsWith(".zip")) {
            return fileName.substring(0, fileName.length() - 4);
        }
        return fileName;
    }
}
