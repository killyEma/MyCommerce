package com.ewikse.mycommerce.activities;

import android.content.Intent;
import android.graphics.Bitmap;
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
    public static final String TO_DELETE = "to_delete";

    private static Bitmap picture;
    private DeleteProductDialog deleteProductDialog;
    private static Product product;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_product);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.detail_collapsing_toolbar);

        TextView description = (TextView) findViewById(R.id.detail_product_description);
        TextView stock = (TextView) findViewById(R.id.detail_product_stock);
        TextView price = (TextView) findViewById(R.id.detail_product_price);
        TextView code = (TextView) findViewById(R.id.detail_product_code);
        ImageView imageView = (ImageView) findViewById(R.id.detail_product_image);

        product = findProductByCode(getIntent().getStringExtra(CODE_KEY));

        collapsingToolbar.setTitle(product.getName());
        description.setText(product.getDescription());
        stock.setText(String.valueOf(product.getStock()));
        price.setText(product.getPrice());
        code.setText(product.getCode());
        imageView.setImageBitmap(picture);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove_product :
                toRemoveThisProduct();
                return true;
            case R.id.action_edit_product :
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void toRemoveThisProduct() {
        if (deleteProductDialog == null) {
            deleteProductDialog = new DeleteProductDialog(this);
        }
        deleteProductDialog.setCode(product.getCode());
        deleteProductDialog.show();
        deleteProductDialog.setDialogResult(new DeleteProductDialog.Answer() {
            @Override
            public void finish(int action) {
                if (action == DeleteProductDialog.OK){
                    closeDetailProduct(product);
                }
            }
        });
    }

    private Product findProductByCode(String code) {
        ProductServiceImpl productService = ProductServiceImpl.getInstance(getApplicationContext());
        Product product = productService.getProductByCode(code);
        picture = productService.retrievePictureProduct(product.getPictureDetail());
        return product;
    }

    private void closeDetailProduct(Product product) {
        if (intent == null) {
            intent = new Intent();
        }
        intent.putExtra(TO_DELETE, product);
        setResult(ProductFragment.RESULT_LIST_CHANGED, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_product, menu);
        return true;
    }
}
