package com.ewikse.mycommerce.model;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private Product product;
    private Bitmap icon, detail;

    public Item(@NonNull Bitmap icon,@NonNull Product product) {
        this.icon = icon;
        this.product = product;
    }

    public Item(@NonNull Bitmap icon, @NonNull Bitmap detail, @NonNull Product product) {
        this.icon = icon;
        this.product = product;
        this.detail = detail;
    }

    public Item(@NonNull Product product) {
        this.product = product;
    }

    public List<Object> toObjectList() {
        List<Object> data = new ArrayList<>(6);
        data.add(getName());
        data.add(getDescription());
        data.add(getStock());
        data.add(getPrice());
        data.add(getCode());
        data.add(getIcon());
        return data;
    }

    public String getName() {
        return product.getName();
    }

    public Product getProduct() {
        return product;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public String getDescription() {
        return product.getDescription();
    }

    public String getStock() {
        return String.valueOf(product.getStock());
    }

    public String getPrice() {
        return String.valueOf(product.getPrice());
    }

    public String getCode() {
        return product.getCode();
    }

    public Bitmap getDetail() {
        return detail;
    }
}