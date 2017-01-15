package com.ewikse.mycommerce.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.adapters.MainActivityPagerAdapter;
import com.ewikse.mycommerce.model.Product;

public class MainActivity extends AppCompatActivity {
    public static final int RESULT_LIST_CHANGED = 1;
    private MainActivityPagerAdapter mainActivityPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.commerce_vp);
        viewPager.setAdapter(getAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_product:
                Intent intent = new Intent();
                intent.setClass(this, NewProductActivity.class);
                startActivityForResult(intent, RESULT_LIST_CHANGED);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode && data != null){
            Product product = (Product) data.getExtras().get(NewProductActivity.TO_ADD);
            if (product != null) {
                Bitmap icon = (Bitmap) data.getExtras().get(NewProductActivity.TO_ADD_IMAGE);
                mainActivityPagerAdapter.notifyItemAdded(product, icon);
            }
        }
    }

    @NonNull
    private MainActivityPagerAdapter getAdapter() {
        if (this.mainActivityPagerAdapter != null) {
            return mainActivityPagerAdapter;
        }
        mainActivityPagerAdapter = new MainActivityPagerAdapter(this.getSupportFragmentManager());
        return mainActivityPagerAdapter;
    }
}
