package com.produtos.dao;

import com.produtos.model.Product;
import java.util.List;

public interface ProductDAO {
    void addProduct(Product product);
    Product getProductById(int id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    void updateProduct(Product product);
    void deleteProduct(int id);
}
