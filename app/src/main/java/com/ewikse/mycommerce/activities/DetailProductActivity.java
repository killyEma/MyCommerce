package com.ewikse.mycommerce.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.dialogs.DeleteProductDialog;
import com.ewikse.mycommerce.fragments.ProductFragment;
import com.ewikse.mycommerce.model.Product;
import com.ewikse.mycommerce.services.ProductServiceImpl;

public class DetailProductActivity extends AppCompatActivity {

    public static final String CODE_KEY = "CODE_KEY";
    public static Product product;
    private DeleteProductDialog deleteProductDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String product_code = getIntent().getStringExtra(CODE_KEY);
        product = findProductByCode(product_code);

        setContentView(R.layout.activity_detail_product);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.detail_collapsing_toolbar);

        TextView description = (TextView) findViewById(R.id.detail_product_description);
        TextView stock = (TextView) findViewById(R.id.detail_product_stock);
        TextView price = (TextView) findViewById(R.id.detail_product_price);
        TextView code = (TextView) findViewById(R.id.detail_product_code);
        ImageView imageView = (ImageView) findViewById(R.id.detail_product_image);

        collapsingToolbar.setTitle(product.getName());
        description.setText(product.getDescription());
        stock.setText(String.valueOf(product.getStock()));
        price.setText(product.getPrice());
        code.setText(product.getCode());
        imageView.setImageBitmap(product.getPicture());
    }

    private Product findProductByCode(String code) {
        return ProductServiceImpl.getInstance(getApplicationContext()).getProductByCode(code);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_remove_product:
                deleteProductDialog = new DeleteProductDialog(this, product.getCode());
                deleteProductDialog.show();
                ProductFragment.PRODUCT_DELETED = product;
                return true;
            case R.id.action_edit_product:
//                TODO:edit product
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_product, menu);
        return true;
    }
}
