package org.example;

import org.example.helper.ErrorHandler;
import org.example.helper.ErrorLogger;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AsyncSearchEngineAggregator {

    static Random random = new Random();
    static ErrorHandler errorHandler = new ErrorLogger();

    public static void main(String[] args) {
        var googleFuture = CompletableFuture.supplyAsync(AsyncSearchEngineAggregator::google)
                .exceptionally(ex -> {
                    errorHandler.handle("google", ex);
                    return List.of();
                });
        var bingFuture = CompletableFuture.supplyAsync(AsyncSearchEngineAggregator::bing)
                .exceptionally(ex -> {
                    errorHandler.handle("bing", ex);
                    return List.of();
                });;
        var duckduckgoFuture = CompletableFuture.supplyAsync(AsyncSearchEngineAggregator::duckduckgo)
                .exceptionally(ex -> {
                    errorHandler.handle("duckduckgo", ex);
                    return List.of();
                });;


        var sortedResults =
            CompletableFuture.allOf(googleFuture, bingFuture, duckduckgoFuture)
                    .thenApply(v ->
                            Stream.of(googleFuture, bingFuture, duckduckgoFuture)
                                    .map(CompletableFuture::join)
                                    .flatMap(List::stream)
                                    .collect(Collectors.toCollection(TreeSet::new)))
                    .thenApply(ArrayList::new)
                    .join();

        System.out.println("Total number of unique results are " +sortedResults.size());
    }

    public static List<String> google() {
        if (random.nextInt(3) == 0) {
            throw new IllegalArgumentException("Boom!...");
        }
        return List.of(
                // Duplicates with List 2
                "Inception", "The Dark Knight", "Interstellar", "The Matrix", "Gladiator",
                "Mad Max: Fury Road", "The Terminator", "Terminator 2: Judgment Day",
                "The Prestige", "The Departed", "Heat", "Django Unchained",
                "The Wolf of Wall Street", "The Bourne Identity", "The Bourne Ultimatum",
                "John Wick", "John Wick: Chapter 2", "The Batman", "Dune", "Avatar",

                // Duplicates with List 3
                "Forrest Gump", "Titanic", "The Lion King", "La La Land", "The Notebook",
                "The Social Network", "Good Will Hunting", "The Truman Show",
                "The Grand Budapest Hotel", "Coco", "Up", "Inside Out",
                "Finding Nemo", "Monsters, Inc.", "Toy Story", "Toy Story 3",
                "Ratatouille", "Soul", "Moana", "Zootopia",

                // Unique to List 1
                "The Shawshank Redemption", "Pulp Fiction", "Fight Club", "Se7en",
                "The Green Mile", "Saving Private Ryan", "The Godfather",
                "The Godfather Part II", "The Lord of the Rings: The Fellowship of the Ring",
                "The Lord of the Rings: The Two Towers",
                "The Lord of the Rings: The Return of the King",
                "Whiplash", "The Pianist", "City of God", "The Silence of the Lambs",
                "The Usual Suspects", "Back to the Future", "Alien", "Blade Runner",
                "Blade Runner 2049", "Her", "Arrival", "Gravity", "Cast Away",
                "The Martian", "A Beautiful Mind", "No Country for Old Men",
                "The Big Lebowski", "Scarface", "Casino", "Jaws", "Jurassic Park",
                "E.T. the Extra-Terrestrial", "The Sixth Sense", "Shutter Island",
                "The Curious Case of Benjamin Button", "1917", "The Hurt Locker",
                "Black Hawk Down", "The Last Samurai", "Slumdog Millionaire",
                "Million Dollar Baby", "The King's Speech", "Argo", "Lincoln",
                "The Aviator", "Catch Me If You Can", "Minority Report", "Rain Man"
        );
    }

    public static List<String> bing() {
        if (random.nextInt(5) == 0) {
            throw new IllegalArgumentException("Boom!...");
        }
        return List.of(
                // Duplicates with List 1
                "Inception", "The Dark Knight", "Interstellar", "The Matrix", "Gladiator",
                "Mad Max: Fury Road", "The Terminator", "Terminator 2: Judgment Day",
                "The Prestige", "The Departed", "Heat", "Django Unchained",
                "The Wolf of Wall Street", "The Bourne Identity", "The Bourne Ultimatum",
                "John Wick", "John Wick: Chapter 2", "The Batman", "Dune", "Avatar",

                // Duplicates with List 3
                "Iron Man", "Iron Man 2", "Iron Man 3", "Captain America: Civil War",
                "Captain America: The Winter Soldier", "Captain America: The First Avenger",
                "Thor", "Thor: Ragnarok", "Black Panther", "Doctor Strange",
                "Guardians of the Galaxy", "Guardians of the Galaxy Vol. 2",
                "Avengers: Infinity War", "Avengers: Endgame", "Spider-Man",
                "Spider-Man 2", "Spider-Man 3", "The Amazing Spider-Man",
                "The Amazing Spider-Man 2", "Spider-Man: Homecoming",

                // Unique to List 2
                "Man of Steel", "Batman Begins", "The Dark Knight Rises",
                "Wonder Woman", "Aquaman", "Justice League", "The Flash",
                "Rogue One: A Star Wars Story", "Star Wars: A New Hope",
                "Star Wars: The Empire Strikes Back", "Star Wars: Return of the Jedi",
                "Star Wars: The Force Awakens", "Star Wars: The Last Jedi",
                "Star Wars: The Rise of Skywalker", "Oblivion", "Edge of Tomorrow",
                "War of the Worlds", "I Am Legend", "Independence Day",
                "Men in Black", "Men in Black II", "Men in Black 3",
                "Pacific Rim", "Pacific Rim: Uprising", "Godzilla",
                "Godzilla: King of the Monsters", "Godzilla vs. Kong",
                "Kong: Skull Island", "Transformers", "Transformers: Revenge of the Fallen",
                "Transformers: Dark of the Moon", "Transformers: Age of Extinction",
                "Transformers: The Last Knight", "Bumblebee", "Top Gun",
                "Top Gun: Maverick", "Mission: Impossible", "Mission: Impossible II",
                "Mission: Impossible III", "Mission: Impossible – Ghost Protocol",
                "Mission: Impossible – Rogue Nation", "Mission: Impossible – Fallout",
                "Mission: Impossible – Dead Reckoning", "Mad Max", "Mad Max 2",
                "Mad Max Beyond Thunderdome", "Speed", "Point Break", "The Rock",
                "Con Air", "Face/Off", "True Lies", "Total Recall", "Robocop"
        );
    }

    public static List<String> duckduckgo() {
        if (random.nextInt(5) == 0) {
            throw new IllegalArgumentException("Boom!...");
        }
        return List.of(
                // Duplicates with List 1
                "Forrest Gump", "Titanic", "The Lion King", "La La Land", "The Notebook",
                "The Social Network", "Good Will Hunting", "The Truman Show",
                "The Grand Budapest Hotel", "Coco", "Up", "Inside Out",
                "Finding Nemo", "Monsters, Inc.", "Toy Story", "Toy Story 3",
                "Ratatouille", "Soul", "Moana", "Zootopia",

                // Duplicates with List 2
                "Iron Man", "Iron Man 2", "Iron Man 3", "Captain America: Civil War",
                "Captain America: The Winter Soldier", "Captain America: The First Avenger",
                "Thor", "Thor: Ragnarok", "Black Panther", "Doctor Strange",
                "Guardians of the Galaxy", "Guardians of the Galaxy Vol. 2",
                "Avengers: Infinity War", "Avengers: Endgame", "Spider-Man",
                "Spider-Man 2", "Spider-Man 3", "The Amazing Spider-Man",
                "The Amazing Spider-Man 2", "Spider-Man: Homecoming",

                // Unique to List 3
                "The Holiday", "Love Actually", "Notting Hill", "Crazy Rich Asians",
                "10 Things I Hate About You", "The Devil Wears Prada", "The Intern",
                "Mean Girls", "Clueless", "Superbad", "Step Brothers",
                "Anchorman", "The Hangover", "The Hangover Part II",
                "The Hangover Part III", "Wedding Crashers", "Old School",
                "Dumb and Dumber", "Ace Ventura: Pet Detective",
                "Ace Ventura: When Nature Calls", "Bruce Almighty", "Liar Liar",
                "The Mask", "School of Rock", "Napoleon Dynamite", "Elf",
                "Home Alone", "Home Alone 2", "Big", "Mrs. Doubtfire",
                "Groundhog Day", "Lost in Translation", "Moonrise Kingdom",
                "Isle of Dogs", "Fantastic Mr. Fox", "The Royal Tenenbaums",
                "The French Dispatch", "The Lego Movie", "The Lego Movie 2",
                "Shrek", "Shrek 2", "Shrek the Third", "Shrek Forever After",
                "Kung Fu Panda", "Kung Fu Panda 2", "Kung Fu Panda 3",
                "How to Train Your Dragon", "How to Train Your Dragon 2",
                "How to Train Your Dragon: The Hidden World", "Frozen", "Frozen II",
                "Lilo & Stitch", "The Little Mermaid", "Beauty and the Beast",
                "Aladdin", "Mulan", "Hercules", "Tarzan",
                "The Emperor's New Groove", "Atlantis: The Lost Empire",
                "Treasure Planet", "The Iron Giant", "Coraline",
                "Kubo and the Two Strings"
        );

    }
}
