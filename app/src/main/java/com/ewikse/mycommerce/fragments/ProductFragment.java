package com.ewikse.mycommerce.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.adapters.ProductAdapter;
import com.ewikse.mycommerce.model.Product;
import com.ewikse.mycommerce.services.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends ListFragment {

    public ProductFragment() {}

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (getUserVisibleHint()) {
            List<Product> products = retrieveProducts();
            setListAdapter(new ProductAdapter(getActivity(), products));
        }

    }

    private List<Product> retrieveProducts() {
        return ProductServiceImpl.getInstance(getContext()).getProducts();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(),"se pulso un producto" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
