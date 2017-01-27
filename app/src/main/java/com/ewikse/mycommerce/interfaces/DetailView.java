package com.ewikse.mycommerce.interfaces;

import android.content.Context;

import com.ewikse.mycommerce.model.Item;
import com.ewikse.mycommerce.model.Product;

import java.io.Serializable;
import java.util.List;

public interface DetailView {

    void showDialogToRemoveThisProduct();

    void closeDetailProduct(Item product);

    void fillPageWithProductData(List<Object> data);

    Context getApplicationContext();

    void finish();
}
