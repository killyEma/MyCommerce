package com.ewikse.mycommerce.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

import com.ewikse.mycommerce.model.Product;
import com.ewikse.mycommerce.presenters.ProductPresenter;

import java.util.List;

public interface ProductView {
    void createAdapter(List<Bitmap> picturesIcon, List<Product> products);

    Context getContext();

    void setRecyclerView();

    void notifyDeletedItem(int position);

    void notifyItemAdded();

    ProductPresenter getPresenter();
}
