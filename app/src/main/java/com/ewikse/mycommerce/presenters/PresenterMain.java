package com.ewikse.mycommerce.presenters;

import android.graphics.Bitmap;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.interfaces.IPresenterMain;
import com.ewikse.mycommerce.interfaces.MainView;
import com.ewikse.mycommerce.model.Item;
import com.ewikse.mycommerce.model.Product;

public class PresenterMain implements IPresenterMain {
    public static final int RESULT_ITEM_NEW = 2;
    private final MainView mainView;

    public PresenterMain(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onResult(int requestCode, int resultCode, Object oProduct, Object oImage) {
        if (resultCode == RESULT_ITEM_NEW) {
            notifyToTheListAProductWasAdded(requestCode, resultCode,
                    new Item((Bitmap) oImage, (Product) oProduct));
        }
    }

    @Override
    public void onOptionItemSelected(int itemId) {
        switch (itemId) {
            case R.id.action_new_product:
                mainView.startActivityToAddNewProduct();
        }
    }

    private void notifyToTheListAProductWasAdded(int requestCode, int resultCode, Item item) {
        if (item != null) {
            mainView.getProductFragment().getPresenter()
                    .onActivityResult(requestCode, resultCode, item);
        }
    }
}