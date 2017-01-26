package com.ewikse.mycommerce.model;

import android.graphics.Bitmap;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.interfaces.DetailView;
import com.ewikse.mycommerce.interfaces.IDetailProductPresenter;
import com.ewikse.mycommerce.services.ProductServiceImpl;

import static com.ewikse.mycommerce.dialogs.DeleteProductDialog.OK;

public class DetailProductPresenter implements IDetailProductPresenter {
    private DetailView detailView;
    private Product product;
    private Bitmap picture;

    @Override
    public void onCreate(DetailView detailView, String code) {
        this.detailView = detailView;
        findProductByCode(code);
        detailView.fillPageWithProductData(new Item(picture, product));
    }

    @Override
    public boolean onOptionsItemSelected(int itemId) {
        switch (itemId) {
            case R.id.action_remove_product :
                detailView.showDialogToRemoveThisProduct();
                return true;
            case R.id.action_edit_product :
                return true;
            default :
                return false;
        }
    }

    @Override
    public void onActionSelected(int action) {
        switch (action) {
            case OK:
                detailView.closeDetailProduct(product);
                break;
            default: detailView.finish();
        }
    }

    private void findProductByCode(String code) {
        ProductServiceImpl productService = ProductServiceImpl.getInstance(detailView.getApplicationContext());
        product = productService.getProductByCode(code);
        picture = productService.retrievePictureProduct(product.getPictureDetail());
    }
}
