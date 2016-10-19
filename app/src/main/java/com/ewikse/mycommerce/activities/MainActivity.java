package com.ewikse.mycommerce.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.adapters.MainActivityPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.commerce_vp);
        viewPager.setAdapter(new MainActivityPagerAdapter(this.getSupportFragmentManager()));
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
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
