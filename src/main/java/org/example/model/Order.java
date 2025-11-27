package org.example.model;

import java.util.List;
import java.util.Map;

public record Order(int orderId, Map<Product, Integer> products, double totalPrice) {
}
