package com.ewikse.mycommerce.presenters;

import android.graphics.Bitmap;

import com.ewikse.mycommerce.adapters.ContentAdapter;
import com.ewikse.mycommerce.interfaces.IProductPresenter;
import com.ewikse.mycommerce.interfaces.ProductView;
import com.ewikse.mycommerce.model.Item;
import com.ewikse.mycommerce.model.Product;
import com.ewikse.mycommerce.services.ProductServiceImpl;

import java.util.List;

import static com.ewikse.mycommerce.fragments.ProductFragment.RESULT_LIST_CHANGED ;
import static com.ewikse.mycommerce.presenters.PresenterMain.RESULT_ITEM_NEW;

public class ProductPresenter implements IProductPresenter {

    private ProductView productView;
    private List<Product> products;
    private List<Bitmap> picturesIcon;

    @Override
    public void onCreate(ProductView productView) {
        this.productView = productView;
    }

    @Override
    public void getProducts() {
        products = retrieveProducts();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Object data) {
        switch (resultCode) {
            case RESULT_LIST_CHANGED:
                deleteItem((Product) data); break;
            case RESULT_ITEM_NEW:
                addNewItem((Item) data); break;
        }
    }

    private void addNewItem(Item item) {
        products.add(item.getProduct());
        picturesIcon.add(item.getIcon());
        productView.notifyItemAdded();
    }

    private void deleteItem(Product product) {
        int position = products.indexOf(product);
        products.remove(position);
        picturesIcon.remove(position);
        productView.notifyDeletedItem(position);
    }

    private List<Product> retrieveProducts() {
        ProductServiceImpl productService = ProductServiceImpl.getInstance(productView.getContext());
        //TODO: this should be in asyncTask
        products = productService.getProducts();
        picturesIcon = productService.retrievePicturesForProducts(products);
        productView.setAdapterWithData(new ContentAdapter(products, picturesIcon, productView.getOnProductClickListener()));
        productView.setRecyclerView();
        return products;
    }

}
