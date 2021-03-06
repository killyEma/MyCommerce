package com.ewikse.mycommerce.interfaces;

import com.ewikse.mycommerce.model.Item;

public interface IProductPresenter {
    void onCreate(ProductView productView);
    void getProducts();
    void onResult(int requestCode, int resultCode, Object item);
}
