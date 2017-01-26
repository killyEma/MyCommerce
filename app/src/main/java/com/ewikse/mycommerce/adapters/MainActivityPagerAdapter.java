package com.ewikse.mycommerce.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ewikse.mycommerce.fragments.ProductFragment;

public class MainActivityPagerAdapter extends FragmentPagerAdapter{
    private ProductFragment productFragment;

    public MainActivityPagerAdapter(FragmentManager fm, ProductFragment productFragment) {
        super(fm);
        this.productFragment = productFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return productFragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

}
