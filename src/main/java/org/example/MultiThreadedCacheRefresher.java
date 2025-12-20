package org.example;

import org.example.helper.LoggingRefreshListener;
import org.example.helper.ProductService;
import org.example.helper.RefreshListener;
import org.example.model.ProductInfo;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MultiThreadedCacheRefresher {

    static Map<String, ProductInfo> products = new ConcurrentHashMap<>();
    static ProductService productService = new ProductService();
    static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    static ExecutorService reader = Executors.newFixedThreadPool(5);
    static RefreshListener refreshListener= new LoggingRefreshListener();
    static AtomicLong refreshedCount = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                addProducts();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        scheduler.scheduleAtFixedRate(() -> {
            var allProducts = productService.getProducts();
            for (ProductInfo product: allProducts) {
                products.compute(product.getProductName(), (k, v) -> {
                    refreshedCount.getAndIncrement(); return product;});
            }
            products.values().forEach(System.out::println);
            refreshListener.onRefreshComplete(refreshedCount.get());
        }, 0, 10, TimeUnit.SECONDS);

        var nowPlus10 = LocalDateTime.now().plusSeconds(10);
        while (nowPlus10.isAfter(LocalDateTime.now())) {
            for (int i=0; i<=5; i++) {
                reader.submit(() -> {
                    var apple = products.get("Apple iPhone");
                    var samsung = products.get("Samsung Galaxy");
                    System.out.println(apple.toString());
                    System.out.println(samsung.toString());
                });
                Thread.sleep(2000);
            }
        }

        reader.shutdown();
        scheduler.shutdown();
        reader.awaitTermination(1, TimeUnit.MINUTES);
        scheduler.awaitTermination(1, TimeUnit.MINUTES);
    }

    private static void addProducts() throws InterruptedException {
        productService.addProduct(new ProductInfo(1, "Apple iPhone", new AtomicInteger(100), 1400));
        productService.addProduct(new ProductInfo(2, "Samsung Galaxy", new AtomicInteger(100), 900));
        Thread.sleep(3000);
        productService.updateProductPrice(1, new AtomicInteger(90));
        Thread.sleep(2000);
        productService.updateProductPrice(2, new AtomicInteger(75));
        productService.updateProductPrice(1, new AtomicInteger(50));
    }
}
