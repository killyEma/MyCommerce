package com.ewikse.mycommerce.adapters.Holders;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.adapters.ContentAdapter;
import com.ewikse.mycommerce.model.Product;

import java.util.List;

public class ProductHolder extends RecyclerView.ViewHolder {
    private TextView name, price, stock;
    private ImageView photo;
    private static List<Product> products;

    public ProductHolder(LayoutInflater inflater, ViewGroup parent, List<Product> products, final ContentAdapter.OnProductClickListener onProductClickListener) {
        super(inflater.inflate(R.layout.product_row, parent, false));
        photo = (ImageView) itemView.findViewById(R.id.product_iv);
        name = (TextView) itemView.findViewById(R.id.description_product_tv);
        price = (TextView) itemView.findViewById(R.id.price_tv);
        stock = (TextView) itemView.findViewById(R.id.stock_tv);
        ProductHolder.products = products;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductClickListener.onItemClick(ProductHolder.products.get(getAdapterPosition()).getCode());
            }
        });
    }

    public void bind(Bitmap bitmap, String name, String price, String stock) {
        this.photo.setImageBitmap(bitmap);
        this.name.setText(name);
        this.price.setText(price);
        this.stock.setText(stock);
    }
}

