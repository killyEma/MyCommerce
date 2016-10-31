package com.ewikse.mycommerce.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.services.ProductServiceImpl;

public class DeleteProductDialog extends Dialog implements View.OnClickListener{
    private Button discard, cancel;
    private String code;

    public DeleteProductDialog(Context context, String code) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_product);

        discard = (Button) findViewById(R.id.dialog_product_discard_product);
        cancel = (Button) findViewById(R.id.dialog_product_cancel_delete);

        discard.setOnClickListener(this);
        cancel.setOnClickListener(this);

        this.code = code;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_product_discard_product:
                deleteProduct();
                dismiss();
                break;
            case R.id.dialog_product_cancel_delete:
                dismiss();
                break;
        }
    }

    private void deleteProduct() {
        ProductServiceImpl.getInstance(getContext()).deleteProduct(this.code);
    }
}
