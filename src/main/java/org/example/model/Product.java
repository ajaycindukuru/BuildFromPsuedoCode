package org.example.model;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public record Product(int productId,String productName, AtomicInteger quantity, double price) {
}
