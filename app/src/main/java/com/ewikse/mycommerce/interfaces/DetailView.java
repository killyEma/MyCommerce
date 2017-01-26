package com.ewikse.mycommerce.interfaces;

import android.content.Context;

import com.ewikse.mycommerce.model.Item;
import com.ewikse.mycommerce.model.Product;

public interface DetailView {

    void showDialogToRemoveThisProduct();

    void closeDetailProduct(Product product);

    void fillPageWithProductData(Item item);

    Context getApplicationContext();

    void finish();
}
