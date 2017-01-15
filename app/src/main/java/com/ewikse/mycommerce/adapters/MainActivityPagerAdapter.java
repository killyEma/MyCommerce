package com.ewikse.mycommerce.adapters;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ewikse.mycommerce.fragments.ProductFragment;
import com.ewikse.mycommerce.model.Product;

public class MainActivityPagerAdapter extends FragmentPagerAdapter{
    private ProductFragment productFragment;

    public MainActivityPagerAdapter(FragmentManager fm) {
        super(fm);
        productFragment = ProductFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        return productFragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    public void notifyItemAdded(Product product, Bitmap icon) {
        productFragment.notifyItemAdded(product, icon);
    }
}
