package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class WordFrequencyAnalyzer {



    public static void main(String[] args) throws IOException {
        wordCountParallelism();
    }

    private static void wordCount() throws IOException {
        Map<String, Integer> wordFrequency = new HashMap<>();
        try(BufferedReader reader =new BufferedReader(new FileReader("/Users/ajaycindukuru/Downloads/big.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", " ").split("\\s+");
              for (String word: words) {
                  if (word.isEmpty()) continue;
                  wordFrequency.merge(word,1, Integer::sum);
              }
            }
        }

        wordFrequency.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(5).forEach(map -> System.out.println("Word -> [" +map.getKey()+ "] count is "+map.getValue()));
    }

    private static void wordCountParallelism() throws IOException {
        Map<String, Integer> wordFrequency = new ConcurrentHashMap<>();
        try(Stream<String> lineStream = Files.lines(Path.of("/Users/ajaycindukuru/Downloads/big.txt"))) {
            lineStream
                .parallel()
                .flatMap(line -> Arrays.stream(line.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", " ").split("\\s+")))
                .filter(word -> !word.isEmpty())
                .forEach(word -> wordFrequency.merge(word, 1, Integer::sum));
        }
        wordFrequency.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(5).forEach(map -> System.out.println("Word -> [" +map.getKey()+ "] count is "+map.getValue()));
    }
}
