package com.ewikse.mycommerce.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ewikse.mycommerce.adapters.Holders.ProductHolder;
import com.ewikse.mycommerce.model.Product;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ProductHolder> {
    private final List<Bitmap> picturesIcon;
    private final OnProductClickListener onProductClickListener;
    private List<Product> products;

    public ContentAdapter(List<Product> products, List<Bitmap> picturesIcon, OnProductClickListener onProductClickListener) {
        this.products = products;
        this.picturesIcon = picturesIcon;
        this.onProductClickListener = onProductClickListener;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(parent.getContext()), parent, products, onProductClickListener);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(picturesIcon.get(position), product.getName(), product.getPrice(), String.valueOf(product.getStock()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnProductClickListener {
         void onItemClick(String productCode);
    }
}