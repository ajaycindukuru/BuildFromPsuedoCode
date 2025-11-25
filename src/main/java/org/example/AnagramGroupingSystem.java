package org.example;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class AnagramGroupingSystem {

    public static void main(String[] args) {
        String[] words ={"eat", "tea", "tan", "ate", "nat", "bat"};
        largestAnagram(words);
    }

    private static void largestAnagram(String[] words) {
        Map<String, List<String>> hashMap = new HashMap<>();

        for(String word: words) {
            var chars = word.toCharArray();
            Arrays.sort(chars);
            var signature = new String(chars);
            hashMap.computeIfAbsent(signature, k -> new ArrayList<>()).add(word);
        }

        hashMap.values().stream().sorted(Comparator.comparing(List::size)).limit(3).forEach(System.out::println);

    }
}
