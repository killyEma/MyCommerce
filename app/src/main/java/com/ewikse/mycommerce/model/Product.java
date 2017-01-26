package com.ewikse.mycommerce.model;

import java.io.Serializable;

public class Product implements Serializable{
    public static final String ICON = "ICON";
    public static final String DETAIL = "DETAIL";

    private String code;
    private String name;
    private String description;
    private int stock;
    private String price;
    private String pictureIcon;
    private String pictureDetail;

    public Product(String[] values) {
        this.code = values[0];
        this.name = values[1];
        this.description = values[2];
        this.stock = Integer.parseInt(values[3]);
        this.price = values[4];
        this.pictureIcon = code + ICON;
        this.pictureDetail = code + DETAIL;
    }

    public Product(String code, String name, String description, int stock, String price, String pictureIcon, String pictureDetail) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.pictureIcon = pictureIcon;
        this.pictureDetail = pictureDetail;
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

    public int getStock() {
        return stock;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureIcon() {
        return pictureIcon;
    }

    public String getPictureDetail() {
        return pictureDetail;
    }

}
