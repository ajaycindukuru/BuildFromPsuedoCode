package org.example.helper;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class AmazonVendorAPI implements VendorAPI {
    AtomicReference<Double> count = new AtomicReference<>(0.0);

    @Override
    public String getVendorName() {
        return "Amazon";
    }

    @Override
    public double getPrice() {
         if (count.accumulateAndGet(1.0, Double::sum) % 5.0 != 0.0) {
             return count.get() * new Random().nextInt();
         }
         throw new RuntimeException("Error in Amazon Vendor API");
    }
}
