package com.ewikse.mycommerce.presenters;

import android.graphics.Bitmap;

import com.ewikse.mycommerce.interfaces.INewProductPresenter;
import com.ewikse.mycommerce.interfaces.NewProductView;
import com.ewikse.mycommerce.model.Item;
import com.ewikse.mycommerce.model.Product;
import com.ewikse.mycommerce.services.ProductServiceImpl;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.ewikse.mycommerce.R.id.cancel_new_product_btn;
import static com.ewikse.mycommerce.R.id.image_new_product_iv;
import static com.ewikse.mycommerce.R.id.new_product_btn;
import static com.ewikse.mycommerce.interfaces.ResultCodes.RESULT_LOAD_IMAGE;

public class NewProductPresenter implements INewProductPresenter {
    private NewProductView newProductView;
    private Product product;

    public NewProductPresenter(NewProductView newProductView) {
        this.newProductView = newProductView;
    }

    @Override
    public void onClickById(int id) {
        switch (id) {
            case cancel_new_product_btn:
                newProductView.finish();
                break;
            case new_product_btn:
                saveProduct();
                newProductView.callEmailService();
                newProductView.setResultAction(product);
                newProductView.finish();
                break;
            case image_new_product_iv:
                newProductView.retrieveImageFromGallery();
                break;
        }
    }

    @Override
    public void onResult(int requestCode, int resultCode) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            newProductView.loadImage();
        }
    }

    private void saveProduct() {
        product =  new Product(newProductView.retrieveDataInputs());
        List<Bitmap> images = newProductView.retrieveProductImages();
        Item item = new Item(images.get(0), images.get(1), product);

        ProductServiceImpl.getInstance(newProductView.getApplicationContext())
                .saveProduct(item);
    }
}
