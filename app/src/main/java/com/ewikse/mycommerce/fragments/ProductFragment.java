package com.ewikse.mycommerce.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.activities.DetailProductActivity;
import com.ewikse.mycommerce.adapters.ContentAdapter;
import com.ewikse.mycommerce.model.Product;
import com.ewikse.mycommerce.services.ProductServiceImpl;

import java.util.List;

public class ProductFragment extends Fragment {

    public static final int RESULT_LIST_CHANGED = 2;

    private static List<Product> products;
    private static ProductFragment fragment;
    private static ContentAdapter adapter;
    private static List<Bitmap> picturesIcon = null;
    private Intent intent;

    public ProductFragment() {
    }

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
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        adapter = new ContentAdapter(products, picturesIcon, getOnProductClickListener());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    private List<Product> retrieveProducts() {
        ProductServiceImpl productService = ProductServiceImpl.getInstance(getContext());
        //TODO: this should be in asyncTask
        List<Product> products = productService.getProducts();
        picturesIcon = productService.retrievePicturesForProducts(products);
        return products;
    }

    @NonNull
    private ContentAdapter.OnProductClickListener getOnProductClickListener() {
        return new ContentAdapter.OnProductClickListener() {
            @Override
            public void onItemClick(String productCode) {
                if (intent == null) {
                    intent = new Intent(getContext(), DetailProductActivity.class);
                }
                intent.putExtra(DetailProductActivity.CODE_KEY, productCode);
                startActivityForResult(intent, RESULT_LIST_CHANGED);
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Product product = (Product) data.getExtras().get(DetailProductActivity.TO_DELETE);
        if (requestCode == RESULT_LIST_CHANGED && product != null) {
            products.remove(product);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void notifyItemAdded(Product product, Bitmap icon) {
        products.add(product);
        picturesIcon.add(icon);
        adapter.notifyItemInserted(adapter.getItemCount() + 1);
    }
}
