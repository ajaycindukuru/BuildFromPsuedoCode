package org.example.helper;

import org.example.model.ProductInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductService implements ProductAPI {

    List<ProductInfo> products = new ArrayList<>();

    @Override
    public List<ProductInfo> getProducts() {
        return products.stream().toList();
    }

    @Override
    public ProductInfo getProductById(int id) {
        return products.stream().filter(f -> f.getProductId()==id).findFirst().orElseThrow(() -> new UnknownError("No product available with id " +id));
    }

    @Override
    public ProductInfo getProductByName(String name) {
        return products.stream().filter(f -> f.getProductName().equalsIgnoreCase(name)).findFirst().orElseThrow(() -> new UnknownError("No product available with name " +name));
    }

    @Override
    public int addProduct(ProductInfo productInfo) {
         products.add(productInfo);
         return productInfo.getProductId();
    }

    @Override
    public void updateProductPrice(int productId, AtomicInteger quantity) {
        products.stream().filter(f -> f.getProductId()==productId).findFirst().ifPresent(productInfo -> productInfo.setQuantity(quantity));
    }
}
