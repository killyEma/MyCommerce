package com.ewikse.mycommerce.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ewikse.mycommerce.backgroundservices.EmailService;
import com.ewikse.mycommerce.interfaces.NewProductView;
import com.ewikse.mycommerce.presenters.NewProductPresenter;
import com.ewikse.mycommerce.utils.PhotoUtils;

import static com.ewikse.mycommerce.R.drawable.document_add;
import static com.ewikse.mycommerce.R.id.cancel_new_product_btn;
import static com.ewikse.mycommerce.R.id.code_new_product_et;
import static com.ewikse.mycommerce.R.id.description_new_product_et;
import static com.ewikse.mycommerce.R.id.image_new_product_iv;
import static com.ewikse.mycommerce.R.id.name_new_product_et;
import static com.ewikse.mycommerce.R.id.new_product_btn;
import static com.ewikse.mycommerce.R.id.price_new_product_et;
import static com.ewikse.mycommerce.R.id.stock_new_product_et;
import static com.ewikse.mycommerce.R.layout.activity_new_product;
import static com.ewikse.mycommerce.backgroundservices.EmailService.CODE_PRODUCT;
import static com.ewikse.mycommerce.interfaces.ResultCodes.RESULT_ITEM_NEW;
import static com.ewikse.mycommerce.interfaces.ResultCodes.RESULT_LOAD_IMAGE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewProductActivity extends AppCompatActivity implements View.OnClickListener, NewProductView {
    private static final String TITLE_NEW_PRODUCT_CHOOSER = "Imagen de nuevo Producto";
    private static final String TYPE_IMAGE = "image/*";
    public static final String TO_ADD_ITEM = "TO_ADD_ITEM";

    public static final int SHOW_ACTION = 2;
    public static final int SAVE_ACTION = 1;

    private EditText name, code, description, stock, price;
    private ImageView image;

    private static Uri uri;
    private static Bitmap imageBitmap;
    private Intent toCallImageChooser;
    private Intent toCallService;

    private PhotoUtils photoUtils;
    private NewProductPresenter newProductPresenter;
    private Intent itemToAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_new_product);
        initConfig();
        newProductPresenter = new NewProductPresenter(this);
        photoUtils = new PhotoUtils(
                getContentResolver(), getApplicationContext(), getWindowManager());
    }

    @Override
    public void onClick(View v) {
        newProductPresenter.onClickById(v.getId());
    }

    @Override
    public String[] retrieveDataInputs() {
        return new String[]{code.getText().toString(),
                name.getText().toString(),
                description.getText().toString(),
                stock.getText().toString(),
                price.getText().toString()
        };
    }

    @Override
    public void retrieveImageFromGallery() {
        if (toCallImageChooser == null) {
            toCallImageChooser = new Intent();
            toCallImageChooser.setType(TYPE_IMAGE);
            toCallImageChooser.setAction(Intent.ACTION_GET_CONTENT);
        }
        startActivityForResult(Intent.createChooser(toCallImageChooser,
                TITLE_NEW_PRODUCT_CHOOSER),
                RESULT_LOAD_IMAGE, null);
    }

    @Override
    public void callEmailService() {
        if (toCallService == null) {
            toCallService = new Intent(this, EmailService.class);
        }
        toCallService.putExtra(CODE_PRODUCT, code.getText().toString());
        startService(toCallService);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getData() == null) {
            return;
        }
        uri = data.getData();
        newProductPresenter.onResult(requestCode, resultCode);
    }

    @Override
    public List<Bitmap> retrieveProductImages() {
        if (uri != null && inputsAreValid()) {
            List<Bitmap> images = new ArrayList<>(2);
            Bitmap icon, detail;
            try {
                icon = getImageIcon();
                detail = photoUtils.getBitmapFromUri(uri, SAVE_ACTION);
                images.add(icon);
                images.add(detail);
            } catch (IOException e) {
                Log.e(this.getPackageName(), e.getMessage());
            }
            return images;
        }
        return null;
    }

    @Override
    public void loadImage() {
        imageBitmap = photoUtils.reloadImageViewProduct(uri, SHOW_ACTION);
        image.setImageBitmap(imageBitmap);
    }

    @Override
    public void setResultAction(Serializable oProduct) {
        if (itemToAdd == null) {
            itemToAdd = new Intent();
        }
        itemToAdd.putExtra(TO_ADD_ITEM, oProduct);
        setResult(RESULT_ITEM_NEW, itemToAdd);
    }

    private Bitmap getImageIcon() throws IOException {
        return imageBitmap == null ?
                photoUtils.getBitmapFromUri(uri, SHOW_ACTION) :
                imageBitmap;
    }

    private boolean inputsAreValid() {
        return isNotEmpty(code.getText().toString()) &&
                isNotEmpty(name.getText().toString()) &&
                isNotEmpty(description.getText().toString()) &&
                isNotEmpty(stock.getText().toString()) &&
                isNotEmpty(price.getText().toString());
    }

    private void initConfig() {
        name = (EditText) findViewById(name_new_product_et);
        code = (EditText) findViewById(code_new_product_et);
        description = (EditText) findViewById(description_new_product_et);
        stock = (EditText) findViewById(stock_new_product_et);
        name = (EditText) findViewById(name_new_product_et);
        price = (EditText) findViewById(price_new_product_et);
        image = (ImageView) findViewById(image_new_product_iv);
        image.setImageResource(document_add);
        Button cancel = (Button) findViewById(cancel_new_product_btn);
        Button create = (Button) findViewById(new_product_btn);
        cancel.setOnClickListener(this);
        create.setOnClickListener(this);
        image.setOnClickListener(this);
    }

}