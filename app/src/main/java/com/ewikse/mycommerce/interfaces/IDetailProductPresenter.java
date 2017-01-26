package com.ewikse.mycommerce.interfaces;

public interface IDetailProductPresenter {
    void onCreate(DetailView detailView, String code);
    boolean onOptionsItemSelected(int itemId);
    void onActionSelected(int action);
}
