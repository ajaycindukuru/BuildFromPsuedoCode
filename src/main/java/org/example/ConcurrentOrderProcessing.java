package org.example;


import org.example.model.Order;
import org.example.model.Product;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentOrderProcessing {

    public static final Order POISON_PILL = new Order(-1, Map.of(), 0.0);
    static BlockingQueue<Order> queue = new LinkedBlockingQueue<>();
    static ExecutorService consumers = Executors.newFixedThreadPool(5);
    static Map<Integer, AtomicInteger> topOrders = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Product[] products = getProducts();

        //Start Consumer Threads
        for (int i=0; i<5; i++) {
            consumers.submit(() -> {
                try {
                    while (true) {
                        Order order = queue.take();
                        if (order==POISON_PILL) break;
                        processOrder(order);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        //Start Producer Threads
        generateOrders(products, 200);

        //Poison Pill
        for (int i=0; i<5; i++) {
            queue.put(POISON_PILL);
        }

        consumers.shutdown();
        var consumerTerminated = consumers.awaitTermination(5, TimeUnit.SECONDS);
        Arrays.stream(products).forEach(System.out::println);

        System.out.println("Top 3 Orders: ");
        if (consumerTerminated)
            topOrders.entrySet().stream()
                    .sorted(Comparator.comparing((Map.Entry<Integer, AtomicInteger> map) -> map.getValue().get()).reversed())
                    .limit(3)
                    .forEach(System.out::println);
    }

    private static Product[] getProducts() {
        Product product1 = new Product(1,"t-shirt", new AtomicInteger(1000), 12.00);
        Product product2 = new Product(2,"full sleeves shirt", new AtomicInteger(200), 25.00);
        Product product3 = new Product(3,"polo", new AtomicInteger(600), 19.98);
        Product product4 = new Product(4,"jeans", new AtomicInteger(900), 29.98);
        Product product5 = new Product(5,"shorts", new AtomicInteger(1200), 24.90);

        return new Product[]{product1, product2, product3, product4, product5};
    }

    private static void generateOrders(Product[] products, int count) throws InterruptedException {
       Random random = new Random();
       for (int orderId = 1; orderId <= count; orderId++) {
               var noOfProductsOnSale = random.nextInt(products.length) + 1;
               Map<Product, Integer> sales = new HashMap<>();
               for (int sale = 1; sale <= noOfProductsOnSale; sale++) {
                   int randomProduct = random.nextInt(products.length);
                   var product = products[randomProduct];
                   sales.put(product, random.nextInt(3) + 1);
               }
               var totalPrice = sales.entrySet().stream().mapToDouble(map -> map.getKey().price() * map.getValue()).sum();
               var order = new Order(orderId, sales, totalPrice);
           System.out.println(order);
               queue.put(order);
           }
       }

    private static void processOrder(Order thisOrder) {
        for(Map.Entry<Product, Integer> sales: thisOrder.products().entrySet()) {
            var product = sales.getKey();
            var saleQuantity = sales.getValue();
            if (product.quantity().get() < saleQuantity) {
                System.out.println("Order " +thisOrder.orderId() + ": " + product.productName() +" is Out of stock");
                return;
            } else {
                topOrders.computeIfAbsent(product.productId(), k -> new AtomicInteger(0)).addAndGet(saleQuantity);
                product.quantity().addAndGet(-saleQuantity);
            }
        }
    }
}
