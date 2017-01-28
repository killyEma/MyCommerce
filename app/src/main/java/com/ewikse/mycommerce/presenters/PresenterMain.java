package com.ewikse.mycommerce.presenters;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.interfaces.IPresenterMain;
import com.ewikse.mycommerce.interfaces.MainView;
import com.ewikse.mycommerce.model.Product;

import static com.ewikse.mycommerce.interfaces.ResultCodes.RESULT_ITEM_NEW;

public class PresenterMain implements IPresenterMain {
    private final MainView mainView;

    public PresenterMain(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onResult(int requestCode, int resultCode, Object oProduct) {
        if (resultCode == RESULT_ITEM_NEW) {
            notifyToTheListAProductWasAdded(requestCode, resultCode, (Product) oProduct);
        }
    }

    @Override
    public void onOptionItemSelected(int itemId) {
        switch (itemId) {
            case R.id.action_new_product:
                mainView.startActivityToAddNewProduct();
        }
    }

    private void notifyToTheListAProductWasAdded(int requestCode, int resultCode, Product product) {
        if (product != null) {
            mainView.getProductFragment().getPresenter()
                    .onActivityResult(requestCode, resultCode, product);
        }
    }
}