package org.example;

import org.example.model.Vertex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WordLadderValidator {

    private static final String ALPHABETS ="abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        HashSet<String> dictionary = new HashSet<>(List.of("cold", "card", "cord", "ward", "warm"));
        System.out.println(isWordLadder("cold", "warm", dictionary));
    }

    public static boolean isWordLadder(String start, String end, HashSet<String> dictionary) {
        if (start.length()!=end.length()) return false;

        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        HashSet<String> visited = new HashSet<>();
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) return true;
            for (String neighbor: generateNeighbors(current)) {
                if (dictionary.contains(neighbor) && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    private static List<String> generateNeighbors(String word) {
        List<String> neighbors = new LinkedList<>();
        char[] chars = word.toCharArray();

        for (int i = 0; i< chars.length; i++) {
            char original = chars[i];
            for (char c : ALPHABETS.toCharArray()) {
                if (c==original) continue;
                chars[i] = c;
                neighbors.add(new String(chars));
            }
            chars[i] = original;
        }
        return neighbors;
    }
}
