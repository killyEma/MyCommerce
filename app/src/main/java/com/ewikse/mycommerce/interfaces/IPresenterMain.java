package com.ewikse.mycommerce.interfaces;

public interface IPresenterMain {
    void onResult(int requestCode, int resultCode, Object oProduct, Object oImage);
    void onOptionItemSelected(int itemId);
}
