package com.ewikse.mycommerce.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ewikse.mycommerce.activities.DetailProductActivity;
import com.ewikse.mycommerce.adapters.ContentAdapter;
import com.ewikse.mycommerce.interfaces.ProductView;
import com.ewikse.mycommerce.presenters.ProductPresenter;

import static com.ewikse.mycommerce.R.layout.recycler_view;
import static com.ewikse.mycommerce.activities.DetailProductActivity.CODE_KEY;
import static com.ewikse.mycommerce.activities.DetailProductActivity.TO_DELETE;

public class ProductFragment extends Fragment implements ProductView {

    public static final int RESULT_ITEM_DELETED = 1;

    private static ProductFragment fragment;
    private static ContentAdapter adapter;
    private Intent toSeeDetailProduct;
    private ProductPresenter presenter;
    private RecyclerView recyclerView;

    public ProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(recycler_view, container, false);
        presenter = new ProductPresenter();
        presenter.onCreate(this);
        presenter.getProducts();
        return recyclerView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) { return; }
        Object oProduct = data.getExtras().get(TO_DELETE);
        presenter.onResult(requestCode, resultCode, oProduct);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void notifyItemAdded() {
        adapter.notifyItemInserted(adapter.getItemCount() + 1);
    }

    @Override
    public void notifyDeletedItem(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void setAdapterWithData(ContentAdapter contentAdapter) {
        adapter = contentAdapter;
    }

    @Override
    public void setRecyclerView() {
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @NonNull
    @Override
    public  ContentAdapter.OnProductClickListener getOnProductClickListener() {
        return new ContentAdapter.OnProductClickListener() {
            @Override
            public void onItemClick(String productCode) {
                if (toSeeDetailProduct == null) {
                    toSeeDetailProduct = new Intent(getContext(), DetailProductActivity.class);
                }
                toSeeDetailProduct.putExtra(CODE_KEY, productCode);
                startActivityForResult(toSeeDetailProduct, RESULT_ITEM_DELETED);
            }
        };
    }

    public static ProductFragment newInstance() {
        if (ProductFragment.fragment == null) {
            ProductFragment.fragment = new ProductFragment();
        }
        return fragment;
    }

    @Override
    public ProductPresenter getPresenter() {
        return presenter;
    }

}