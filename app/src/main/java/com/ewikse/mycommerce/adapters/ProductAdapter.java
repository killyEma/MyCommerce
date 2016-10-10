package com.ewikse.mycommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter{

    private final Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_row, parent, false);
            holder = new ProductHolder(
                    (TextView) convertView.findViewById(R.id.description_product_tv),
                    (TextView) convertView.findViewById(R.id.price_tv),
                    (TextView) convertView.findViewById(R.id.stock_tv),
                    (ImageView) convertView.findViewById(R.id.product_iv)
            );
            holder.photo.setImageResource(R.drawable.computer_mouse);
            convertView.setTag(holder);
        } else {
            holder = (ProductHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        holder.setProduct(product);

        return convertView;
    }

    private class ProductHolder {
        TextView description, price, stock;
        ImageView photo;

        public ProductHolder(TextView description, TextView price, TextView stock, ImageView photo) {
            this.description = description;
            this.price = price;
            this.stock = stock;
            this.photo = photo;
        }

        public void setProduct(Product product) {
            this.description.setText(product.getDescription());
            this.price.setText(product.getPrice());
            this.stock.setText(String.valueOf(product.getStock()));
        }
    }

}

