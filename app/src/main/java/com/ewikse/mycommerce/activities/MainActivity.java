package com.ewikse.mycommerce.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import static com.ewikse.mycommerce.R.id.appbar;
import static com.ewikse.mycommerce.R.id.commerce_vp;
import static com.ewikse.mycommerce.R.layout.activity_main;
import static com.ewikse.mycommerce.R.menu.menu_main;
import static com.ewikse.mycommerce.activities.NewProductActivity.TO_ADD_ITEM;

import com.ewikse.mycommerce.adapters.MainActivityPagerAdapter;
import com.ewikse.mycommerce.fragments.ProductFragment;
import com.ewikse.mycommerce.interfaces.MainView;
import com.ewikse.mycommerce.presenters.PresenterMain;

public class MainActivity extends AppCompatActivity implements MainView {
    public static final int RESULT_ITEM_NEW = 2;

    private MainActivityPagerAdapter mainActivityPagerAdapter;
    private ProductFragment productFragment;
    private PresenterMain presenterMain;
    private Intent toAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        productFragment = ProductFragment.newInstance();

        Toolbar toolbar = (Toolbar) findViewById(appbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(commerce_vp);
        viewPager.setAdapter(getAdapter());
        presenterMain = new PresenterMain(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenterMain.onOptionItemSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getExtras() == null) {
            return;
        }
        presenterMain.onResult(requestCode, resultCode,
                data.getExtras().get(TO_ADD_ITEM));
    }

    @NonNull
    private MainActivityPagerAdapter getAdapter() {
        if (this.mainActivityPagerAdapter == null) {
            mainActivityPagerAdapter = new MainActivityPagerAdapter(
                    getSupportFragmentManager(), productFragment);
        }
        return mainActivityPagerAdapter;
    }

    @Override
    public ProductFragment getProductFragment() {
        return productFragment;
    }

    @Override
    public void startActivityToAddNewProduct() {
        if (toAddProduct == null) {
            toAddProduct = new Intent();
            toAddProduct.setClass(this, NewProductActivity.class);
        }
        startActivityForResult(toAddProduct, RESULT_ITEM_NEW);
    }
}