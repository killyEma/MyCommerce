package com.ewikse.mycommerce.model;

import java.io.Serializable;

public class Product implements Serializable{
    private String code;
    private String name;
    private String description;
    private int stock;
    private String price;
    private String picture;

    public Product(String code, String name, String description, int stock, String price, String picture) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.picture = picture;
    }

    @Override
    public int hashCode() {
        return code.hashCode() * 2 + stock * 2;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getCode().equals(((Product) obj).code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
