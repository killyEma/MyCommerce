package com.ewikse.mycommerce.interfaces;

import com.ewikse.mycommerce.fragments.ProductFragment;

public interface MainView {
    ProductFragment getProductFragment();

    void startActivityToAddNewProduct();
}
