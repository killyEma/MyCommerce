package com.ewikse.mycommerce.database;


import com.ewikse.mycommerce.model.Product;

import java.util.List;

public interface ProductDAO {
    boolean addProduct(Product product);
    List<Product> fetchAllProducts();
    boolean deleteProduct(String code);
    boolean updateProduct(Product product);
}
