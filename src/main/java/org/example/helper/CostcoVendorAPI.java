package org.example.helper;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class CostcoVendorAPI implements VendorAPI {
    AtomicReference<Double> count = new AtomicReference<>(0.0);

    @Override
    public String getVendorName() {
        return "Costco";
    }

    @Override
    public double getPrice() {
        if (count.accumulateAndGet(1.0, Double::sum) % 6.0 != 0.0) {
            return count.get() * new Random().nextInt();
        }
        throw new RuntimeException("Error in Costco Vendor API");
    }
}
