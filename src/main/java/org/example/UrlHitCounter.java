package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UrlHitCounter {
    private static final Map<String, Integer> hitCount = new ConcurrentHashMap<>();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws IOException, InterruptedException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/ajaycindukuru/Downloads/urlHitLog.txt"))) {
            String[] urls = {"/home", "/contact", "/about", "/news", "/services", "/testimonials"};
            Random random = new Random();
            for(int i=1; i<=1000; i++) {
                int randomIndex = random.nextInt(urls.length);
                writer.write(urls[randomIndex] + "\n");
            }
        }

        urlHitCount();
    }

    private static void urlHitCount() throws IOException, InterruptedException {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/ajaycindukuru/Downloads/urlHitLog.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String finalLine = line;
                executorService.submit(() -> {
                    hitCount.merge(finalLine, 1, Integer::sum);
                });
            }
        }

        executorService.shutdown();
        var result = executorService.awaitTermination(1, TimeUnit.HOURS);
        if (result)
            hitCount.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(5).forEach(hit -> System.out.println(hit.getKey() +" was hit " +hit.getValue()));
    }
}
