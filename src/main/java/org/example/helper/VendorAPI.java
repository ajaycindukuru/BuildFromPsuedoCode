package org.example.helper;

public interface VendorAPI {
    String getVendorName();
    double getPrice();

    default void logCallback() {
        System.out.println("Vendor " +getVendorName() + " responded with price " +getPrice());
    }
}
