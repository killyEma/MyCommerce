package com.ewikse.mycommerce.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.activities.DetailProductActivity;
import com.ewikse.mycommerce.model.Product;
import com.ewikse.mycommerce.services.ProductServiceImpl;

import java.util.List;

public class ProductFragment extends Fragment {

    private static List<Product> products;
    private static Intent intent;
    private static ProductFragment fragment;
    private ContentAdapter adapter;
    private RecyclerView recyclerView;
    public static Product PRODUCT_DELETED = null;

    public ProductFragment() {}

    public static ProductFragment newInstance() {
        if (ProductFragment.fragment == null) {
            ProductFragment.fragment = new ProductFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = retrieveProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    private List<Product> retrieveProducts() {
        //TODO: this should be in asynctask
        return ProductServiceImpl.getInstance(getContext()).getProducts();
    }

    public void removeRow(int position) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != PRODUCT_DELETED) {
            adapter.notifyItemRemoved(products.indexOf(PRODUCT_DELETED));
            products.remove(PRODUCT_DELETED);
        }
        PRODUCT_DELETED = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ProductHolder> {

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProductHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {
            Product product = products.get(position);
            holder.photo.setImageBitmap(product.getPicture());
            holder.name.setText(product.getName());
            holder.price.setText(product.getPrice());
            holder.stock.setText(String.valueOf(product.getStock()));
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

    }

    public static class ProductHolder extends RecyclerView.ViewHolder {
        TextView name, price, stock;
        ImageView photo;

        public ProductHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.product_row, parent, false));
            photo = (ImageView) itemView.findViewById(R.id.product_iv);
            name = (TextView) itemView.findViewById(R.id.description_product_tv);
            price = (TextView) itemView.findViewById(R.id.price_tv);
            stock = (TextView) itemView.findViewById(R.id.stock_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (intent == null) {
                        intent = new Intent(v.getContext(), DetailProductActivity.class);
                    }
                    intent.putExtra(DetailProductActivity.CODE_KEY, products.get(getAdapterPosition()).getCode());
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

}
