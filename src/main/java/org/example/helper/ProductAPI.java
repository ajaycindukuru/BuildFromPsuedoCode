package org.example.helper;

import org.example.model.ProductInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface ProductAPI {

    List<ProductInfo> getProducts();

    ProductInfo getProductById(int id);

    ProductInfo getProductByName(String name);

    int addProduct(ProductInfo productInfo);

    void updateProductPrice(int productId, AtomicInteger quantity);
}
