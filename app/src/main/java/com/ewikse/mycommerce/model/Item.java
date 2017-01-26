package com.ewikse.mycommerce.model;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public class Item {
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

    public Item(Product product) {
        this.product = product;
        this.icon = null;
        this.detail = null;
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