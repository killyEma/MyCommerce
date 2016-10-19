package com.ewikse.mycommerce.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
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

import java.io.FileDescriptor;
import java.io.IOException;

public class NewProductActivity extends AppCompatActivity implements View.OnClickListener{
    private Button create, cancel;
    private EditText name, code, description, stock, price;
    private ImageView image;
    private static int RESULT_LOAD_IMAGE = 1;
    private static Bitmap imageBitmap;
    private Intent intent;

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
        cancel = (Button) findViewById(R.id.cancel_new_product_btn);
        create = (Button) findViewById(R.id.new_product_btn);
        cancel.setOnClickListener(this);
        create.setOnClickListener(this);
        image.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_new_product_btn : finish(); break;
            case R.id.new_product_btn : saveProduct(); break;
            case R.id.image_new_product_iv : retrieveImage(); break;
        }

    }

    public void retrieveImage() {
        retrieveImageFromGallery();
    }

    public void saveProduct() {
        if (image.getId() != 0) {
            Product product = retrieveProduct();
            ProductServiceImpl.getInstance(getApplicationContext()).saveProduct(product);
            callServiceEmail(product.getCode());
        }
        finish();
    }

    private Product retrieveProduct() {
        return new Product(code.getText().toString(),
                name.getText().toString(),
                description.getText().toString(),
                Integer.parseInt(stock.getText().toString()),
                price.getText().toString(),
                imageBitmap);
    }

    private void retrieveImageFromGallery() {
        if (intent == null) {
            intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        startActivityForResult(Intent.createChooser(intent,"Imagen de nuevo Producto"), RESULT_LOAD_IMAGE, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            reloadImageViewProduct(data.getData());
        }
    }

    private void reloadImageViewProduct(Uri selectedImage) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        cursor.close();

        Bitmap bmp = null;
        try {
            bmp = getBitmapFromUri(selectedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageBitmap = bmp;
        image.setImageBitmap(imageBitmap);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void callServiceEmail(String code) {
        Intent i = new Intent(this, EmailService.class);
        i.putExtra(EmailService.CODE_PRODUCT, code);
        startService(i);
    }

}
