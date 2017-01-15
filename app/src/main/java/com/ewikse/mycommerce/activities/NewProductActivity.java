package com.ewikse.mycommerce.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.backgroundservices.EmailService;
import com.ewikse.mycommerce.model.Product;
import com.ewikse.mycommerce.services.ProductServiceImpl;
import com.ewikse.mycommerce.utils.PhotoUtils;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.IOException;

public class NewProductActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TITLE_NEW_PRODUCT_CHOOSER = "Imagen de nuevo Producto";
    public static final int SAVE_ACTION = 1;
    public static final int SHOW_ACTION = 2;
    public static final String TO_ADD = "TO_ADD";
    public static final String TYPE_IMAGE = "image/*";
    public static final String TO_ADD_IMAGE = "TO_ADD_IMAGE";
    private static int RESULT_LOAD_IMAGE = 1;

    private EditText name, code, description, stock, price;
    private ImageView image;

    private static Uri uri;
    private static Bitmap imageBitmap;
    private Intent intent;

    private PhotoUtils photoUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        name = (EditText) findViewById(R.id.name_new_product_et);
        code = (EditText) findViewById(R.id.code_new_product_et);
        description = (EditText) findViewById(R.id.description_new_product_et);
        stock = (EditText) findViewById(R.id.stock_new_product_et);
        name = (EditText) findViewById(R.id.name_new_product_et);
        price = (EditText) findViewById(R.id.price_new_product_et);
        image = (ImageView) findViewById(R.id.image_new_product_iv);
        image.setImageResource(R.drawable.document_add);
        Button cancel = (Button) findViewById(R.id.cancel_new_product_btn);
        Button create = (Button) findViewById(R.id.new_product_btn);
        cancel.setOnClickListener(this);
        create.setOnClickListener(this);
        image.setOnClickListener(this);
        photoUtils = new PhotoUtils(getContentResolver(), getApplicationContext(), getWindowManager());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_new_product_btn:
                finish();
                break;
            case R.id.new_product_btn:
                saveProduct();
                break;
            case R.id.image_new_product_iv:
                retrieveImageFromGallery();
                break;
        }
    }

    public void saveProduct() {
        if (uri != null && inputsAreValid()) {
            Product product = retrieveProduct();
            Bitmap icon = null, detail = null;
            try {
                icon = imageBitmap == null ? photoUtils.getBitmapFromUri(uri, SHOW_ACTION) : imageBitmap;
                detail = photoUtils.getBitmapFromUri(uri, SAVE_ACTION);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ProductServiceImpl.getInstance(getApplicationContext()).saveProduct(product, icon, detail);
            callServiceEmail(product.getCode());
            Intent intent = new Intent();
            intent.putExtra(TO_ADD, product);
            intent.putExtra(TO_ADD_IMAGE, icon);
            setResult(MainActivity.RESULT_LIST_CHANGED, intent);
        }
        finish();
    }

    private boolean inputsAreValid() {
        return isNotEmpty(code.getText().toString()) &&
                isNotEmpty(name.getText().toString()) &&
                isNotEmpty(description.getText().toString()) &&
                isNotEmpty(stock.getText().toString()) &&
                isNotEmpty(price.getText().toString());
    }

    private Product retrieveProduct() {
        return new Product(code.getText().toString(),
                name.getText().toString(),
                description.getText().toString(),
                Integer.parseInt(stock.getText().toString()),
                price.getText().toString(),
                EMPTY,
                EMPTY);
    }

    private void retrieveImageFromGallery() {
        if (intent == null) {
            intent = new Intent();
            intent.setType(TYPE_IMAGE);
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        startActivityForResult(Intent.createChooser(intent,
                NewProductActivity.TITLE_NEW_PRODUCT_CHOOSER),
                RESULT_LOAD_IMAGE, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            uri = data.getData();
            imageBitmap = photoUtils.reloadImageViewProduct(uri, SHOW_ACTION);
            image.setImageBitmap(imageBitmap);
        }
    }

    private void callServiceEmail(String code) {
        Intent i = new Intent(this, EmailService.class);
        i.putExtra(EmailService.CODE_PRODUCT, code);
        startService(i);
    }
}
