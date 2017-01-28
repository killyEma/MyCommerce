package com.ewikse.mycommerce.presenters;

import android.graphics.Bitmap;

import com.ewikse.mycommerce.adapters.ContentAdapter;
import com.ewikse.mycommerce.interfaces.IProductPresenter;
import com.ewikse.mycommerce.interfaces.ProductView;
import com.ewikse.mycommerce.model.Item;
import com.ewikse.mycommerce.model.Product;
import com.ewikse.mycommerce.services.ProductServiceImpl;

import java.util.List;

import static com.ewikse.mycommerce.fragments.ProductFragment.RESULT_ITEM_DELETED;
import static com.ewikse.mycommerce.interfaces.ResultCodes.RESULT_ITEM_NEW;

public class ProductPresenter implements IProductPresenter {

    private ProductView productView;
    private List<Product> products;
    private List<Bitmap> picturesIcon;
    private ProductServiceImpl productService;

    @Override
    public void onCreate(ProductView productView) {
        this.productView = productView;
        productService = ProductServiceImpl.getInstance(productView.getContext());
    }

    @Override
    public void getProducts() {
        products = retrieveProducts();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Object data) {
        switch (resultCode) {
            case RESULT_ITEM_DELETED:
                deleteItem((Item) data); break;
            case RESULT_ITEM_NEW:
                addNewItem((Product) data); break;
        }
    }

    private void addNewItem(Product product) {
        products.add(product);
        //TODO: this should be in asyncTask
        picturesIcon.add(productService.retrievePictureProductByName(product.getPictureIcon()));
        productView.notifyItemAdded();
    }

    private void deleteItem(Item item) {
        int position = products.indexOf(item.getProduct());
        products.remove(position);
        picturesIcon.remove(position);
        productView.notifyDeletedItem(position);
    }

    private List<Product> retrieveProducts() {
        //TODO: this should be in asyncTask
        products = productService.getProducts();
        picturesIcon = productService.retrievePicturesForProducts(products);
        productView.setAdapterWithData(new ContentAdapter(products, picturesIcon, productView.getOnProductClickListener()));
        productView.setRecyclerView();
        return products;
    }

}
