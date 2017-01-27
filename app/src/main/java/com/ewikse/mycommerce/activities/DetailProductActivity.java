package com.ewikse.mycommerce.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ewikse.mycommerce.dialogs.DeleteProductDialog;
import com.ewikse.mycommerce.interfaces.DetailView;
import com.ewikse.mycommerce.model.DetailProductPresenter;
import com.ewikse.mycommerce.model.Item;

import java.util.List;

import static com.ewikse.mycommerce.R.id.detail_collapsing_toolbar;
import static com.ewikse.mycommerce.R.id.detail_product_code;
import static com.ewikse.mycommerce.R.id.detail_product_description;
import static com.ewikse.mycommerce.R.id.detail_product_image;
import static com.ewikse.mycommerce.R.id.detail_product_price;
import static com.ewikse.mycommerce.R.id.detail_product_stock;
import static com.ewikse.mycommerce.R.id.toolbar;
import static com.ewikse.mycommerce.R.layout.activity_detail_product;
import static com.ewikse.mycommerce.R.menu.menu_detail_product;
import static com.ewikse.mycommerce.fragments.ProductFragment.RESULT_ITEM_DELETED;

public class DetailProductActivity extends AppCompatActivity implements DetailView {

    public static final String CODE_KEY = "CODE_KEY";
    public static final String TO_DELETE = "TO_DELETE";

    private DeleteProductDialog deleteProductDialog;
    private Intent toDeleteProduct;
    private DetailProductPresenter detailProductPresenter;
    private TextView description, stock, price, code;
    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbar;
    private DeleteProductDialog.Answer answer;
    private String codeProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(activity_detail_product);
        setSupportActionBar((Toolbar) findViewById(toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initComponents();

        detailProductPresenter = new DetailProductPresenter();
        codeProduct = getIntent().getStringExtra(CODE_KEY);
        detailProductPresenter.onCreate(this, codeProduct);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return detailProductPresenter.onOptionsItemSelected(item.getItemId());
    }

    @Override
    public void showDialogToRemoveThisProduct() {
        if (deleteProductDialog == null) {
            deleteProductDialog = new DeleteProductDialog(this);
        }
        deleteProductDialog.setCode(codeProduct);
        deleteProductDialog.show();
        deleteProductDialog.setDialogResult(getDialogResult());
    }

    @Override
    public void closeDetailProduct(Item item) {
        if (toDeleteProduct == null) {
            toDeleteProduct = new Intent();
        }
        toDeleteProduct.putExtra(TO_DELETE, item);
        setResult(RESULT_ITEM_DELETED, toDeleteProduct);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_detail_product, menu);
        return true;
    }

    @Override
    public void fillPageWithProductData(List<Object> data) {
        collapsingToolbar.setTitle((String) data.get(0));
        description.setText((String) data.get(1));
        stock.setText((String) data.get(2));
        price.setText((String) data.get(3));
        code.setText((String) data.get(4));
        imageView.setImageBitmap((Bitmap) data.get(5));
    }

    @NonNull
    private DeleteProductDialog.Answer getDialogResult() {
        if (answer == null) {
            answer = new DeleteProductDialog.Answer() {
                @Override
                public void finish(int action) {
                    detailProductPresenter.onActionSelected(action);
                }
            };
        }
        return answer;
    }

    private void initComponents() {
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(detail_collapsing_toolbar);
        description = (TextView) findViewById(detail_product_description);
        stock = (TextView) findViewById(detail_product_stock);
        price = (TextView) findViewById(detail_product_price);
        code = (TextView) findViewById(detail_product_code);
        imageView = (ImageView) findViewById(detail_product_image);
    }
}