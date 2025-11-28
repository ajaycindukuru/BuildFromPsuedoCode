package org.example;

import org.w3c.dom.ls.LSOutput;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AsynchronousFileDownloader {

    public static void main(String[] args) {
        List<String> urls = List.of(
                "https://www.irs.gov/pub/irs-pdf/p2104.pdf",
                "https://www.irs.gov/pub/irs-pdf/p4012.pdf",
                "https://www.irs.gov/pub/irs-pdf/p4012a.pdf",
                "https://www.irs.gov/pub/irs-pdf/p2104c.pdf",
                "https://www.irs.gov/pub/irs-pdf/p5274.pdf");

        List<Integer> fileSizes = Collections.synchronizedList(new ArrayList<>());
        HttpClient client = HttpClient.newHttpClient();
        var futures =
        urls.stream().map(url -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            return client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                    .thenAccept(response -> {
                        var fileName = response.uri().getPath().substring(response.uri().getPath().lastIndexOf("/") +1);
                        var fileSize = Integer.parseInt(response.headers().firstValue("Content-Length").orElse("0"));
                        System.out.println("File " + fileName + " completed -- size: " + fileSize);
                        fileSizes.add(fileSize);
                    })
                    .exceptionally(ex -> {
                        System.out.println("Download failed for " +url+ ": " +ex.getMessage());
                        return null;
                    });
        }).toList();

        var all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        all.join();

        fileSizes.sort(Comparator.reverseOrder());
        System.out.println(fileSizes);
    }


}
