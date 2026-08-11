package ru.cbr.koh.logs.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Comparator;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class RemoteLogArchiveService {

    private static final String SHARE_ID = "bEYYcqor7bacZLD";
    private static final String DAV_URL = "https://cloud.cod.tom.ru/public.php/dav/files/" + SHARE_ID + "/";
    private static final Pattern HREF = Pattern.compile("<d:href>(.*?)</d:href>|<a:href>(.*?)</a:href>|<href>(.*?)</href>");

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public Path downloadLatestZpeArchive(String password) {
        String authorization = buildAuthorization(password);
        String fileName = findLatestZpeArchiveName(authorization);
        Path targetPath = resolveDownloadPath(fileName);
        if (isValidCachedArchive(targetPath)) {
            return targetPath;
        }
        return downloadArchive(fileName, targetPath, authorization);
    }

    String buildAuthorization(String password) {
        return "Basic " + Base64.getEncoder()
                .encodeToString((SHARE_ID + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

    Path resolveDownloadPath(String fileName) {
        return Path.of("koh-log-viewer-db", "downloads", fileName);
    }

    boolean isValidCachedArchive(Path targetPath) {
        try {
            return Files.isRegularFile(targetPath) && Files.size(targetPath) > 0;
        } catch (IOException e) {
            return false;
        }
    }

    Path downloadArchive(String fileName, Path targetPath, String authorization) {
        try {
            Files.createDirectories(targetPath.getParent());
            HttpRequest request = HttpRequest.newBuilder(URI.create(DAV_URL + fileName))
                    .GET()
                    .header("Authorization", authorization)
                    .timeout(Duration.ofMinutes(5))
                    .build();
            HttpResponse<Path> response = httpClient.send(request, HttpResponse.BodyHandlers.ofFile(targetPath));
            if (response.statusCode() >= 400) {
                throw new IllegalStateException("Не удалось скачать архив, HTTP " + response.statusCode());
            }
            return targetPath;
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка скачивания архива " + fileName, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Скачивание архива прервано", e);
        }
    }

    String findLatestZpeArchiveName(String authorization) {
        String listing = readDavListing(authorization);
        Optional<String> latest = HREF.matcher(listing).results()
                .map(matchResult -> firstNotNull(matchResult.group(1), matchResult.group(2), matchResult.group(3)))
                .map(this::extractFileName)
                .filter(fileName -> fileName.endsWith("zpe-all-logs.zip"))
                .max(Comparator.naturalOrder());
        return latest.orElseThrow(() -> new IllegalStateException("Не найден файл *zpe-all-logs.zip в WebDAV"));
    }

    private String readDavListing(String authorization) {
        String body = """
                <?xml version="1.0" encoding="utf-8" ?>
                <d:propfind xmlns:d="DAV:">
                  <d:prop>
                    <d:getlastmodified/>
                    <d:getcontentlength/>
                    <d:resourcetype/>
                  </d:prop>
                </d:propfind>
                """;
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(DAV_URL))
                    .method("PROPFIND", HttpRequest.BodyPublishers.ofString(body))
                    .header("Authorization", authorization)
                    .header("Depth", "1")
                    .header("Content-Type", "application/xml")
                    .timeout(Duration.ofMinutes(2))
                    .build();
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() >= 400) {
                throw new IllegalStateException("Не удалось получить список файлов, HTTP " + response.statusCode());
            }
            return new String(response.body().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка чтения списка WebDAV", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Чтение списка WebDAV прервано", e);
        }
    }

    private String extractFileName(String href) {
        String normalized = href.endsWith("/") ? href.substring(0, href.length() - 1) : href;
        int slash = normalized.lastIndexOf('/');
        return slash >= 0 ? normalized.substring(slash + 1) : normalized;
    }

    private String firstNotNull(String first, String second, String third) {
        if (first != null) {
            return first;
        }
        if (second != null) {
            return second;
        }
        return third;
    }
}
