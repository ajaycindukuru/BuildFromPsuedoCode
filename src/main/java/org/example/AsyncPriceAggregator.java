package org.example;

import org.example.helper.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class AsyncPriceAggregator {

    public static void main(String[] args) {

        List<CompletableFuture<Double>> futures =
                List.of(
                    CompletableFuture.supplyAsync(() -> new AmazonVendorAPI().getPrice()),
                    CompletableFuture.supplyAsync(() -> new BestBuyVendorAPI().getPrice()),
                    CompletableFuture.supplyAsync(() -> new CostcoVendorAPI().getPrice()),
                    CompletableFuture.supplyAsync(() -> new TargetVendorAPI().getPrice()),
                    CompletableFuture.supplyAsync(() -> new WalmartVendorAPI().getPrice())
                );

        List<Double> allPrices =
               CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                       .thenApply(v ->
                               futures.stream().map(f -> f.getNow(null)).filter(Objects::nonNull).toList()
                       ).join();

        System.out.println("Highest price: " + allPrices.stream().max(Double::compare).get());
        System.out.println("Lowest price: " + allPrices.stream().min(Double::compare).get());
        System.out.println("Average price: " + allPrices.stream().mapToDouble(p->p).average().getAsDouble());



    }
}
