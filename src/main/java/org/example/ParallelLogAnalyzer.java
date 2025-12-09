package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParallelLogAnalyzer {

    static ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    static Map<String, Long> global = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException, FileNotFoundException, ExecutionException {
        //createLogFiles(100);
        var directoryPath = "/Users/ajaycindukuru/Downloads/Logs/";
        var directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files!=null) {
                List<Future<Map<String, Long>>> futures = new ArrayList<>();
                for (File file : files) {
                        futures.add(
                            service.submit(() -> {
                                Map<String, Long> local = new HashMap<>();
                                try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                                    return in.lines().map(line -> line.split(":", 2)[0])
                                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                        );
                }

                service.shutdown();
                service.awaitTermination(1, TimeUnit.MINUTES);

                for(Future<Map<String, Long>> future: futures) {
                    future.get().forEach((k, v) -> global.merge(k, v, Long::sum));
                }

                global.entrySet().stream().max(Map.Entry.comparingByValue()).ifPresent(System.out::println);
            }
        }



    }

    public static void createLogFiles(int numberOfFiles) throws InterruptedException {

        String[] logs = {
                "ERROR: Failed to connect",
                "INFO: User logged in",
                "WARN: Low memory",
                "ERROR: Database timeout",
                "INFO: Configuration loaded successfully",
                "WARN: Deprecated API usage detected",
                "ERROR: Unauthorized access attempt",
                "INFO: Session terminated by user",
                "WARN: Disk space running low",
                "ERROR: Null pointer exception",
                "INFO: Cache cleared"
        };

        Random random = new Random();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i=1; i<=numberOfFiles; i++) {
            executor.submit(() -> {
                var numberOfLines = 1000;
                try (BufferedWriter out = new BufferedWriter(new FileWriter("/Users/ajaycindukuru/Downloads/Logs/log-" +LocalDateTime.now().getNano()))) {
                    for (int j=0; j<=numberOfLines; j++) {
                        var log = logs[random.nextInt(logs.length)];
                        out.write(log + "\n");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
