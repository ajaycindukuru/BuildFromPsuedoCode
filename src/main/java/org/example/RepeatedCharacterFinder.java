package org.example;

import org.example.model.CharFinder;

import java.util.LinkedHashMap;
import java.util.Map;

public class RepeatedCharacterFinder {

    // You are given a string (e.g. programming)
    // Find the first non-repeating character and the first repeating character.

    // Concepts tested: HashMap, iteration order, edge case handling, algorithm efficiency


    public static void main(String[] args) {
        var string = "kavya";

        var charFinder = characterFinder(string) ;
        System.out.println("First repeating character is :: " +charFinder.repeating());
        System.out.println("First non repeating character is :: " +charFinder.nonRepeating());
    }

    private static CharFinder characterFinder(String string) {
        if (string==null || string.isEmpty()) return new CharFinder(null, null);
        var map = new LinkedHashMap<Character, Integer>();

        // Find the first non-repeating
        for (char c: string.toCharArray()) {
            //For each character check if it exists in map. If not put. If it exists increment the value by 1
            map.compute(c, ((ch, count) -> count==null? 1 : count + 1));
        }

        Character repeating = null;
        Character nonRepeating = null;
        for (Map.Entry<Character, Integer> entry: map.entrySet()) {
            if (entry.getValue() > 1 && repeating == null) {
                repeating = entry.getKey();
            }
            if (entry.getValue()==1 && nonRepeating == null) {
                nonRepeating = entry.getKey();
            }
            if (repeating ==null && nonRepeating == null) break;
        }

        return new CharFinder(repeating, nonRepeating);
    }
}
