package com.ewikse.mycommerce.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ewikse.mycommerce.R;
import com.ewikse.mycommerce.services.ProductServiceImpl;

public class DeleteProductDialog extends Dialog implements View.OnClickListener{
    public static final int OK = 1;
    private static final int CANCEL = 0;
    private Button discard, cancel;
    private String code;
    Answer answer;

    public DeleteProductDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_product);

        discard = (Button) findViewById(R.id.dialog_product_discard_product);
        cancel = (Button) findViewById(R.id.dialog_product_cancel_delete);

        discard.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_product_discard_product:
                deleteProduct();
                answer.finish(OK);
                dismiss();
                break;
            case R.id.dialog_product_cancel_delete:
                answer.finish(CANCEL);
                dismiss();
                break;
        }
    }

    private void deleteProduct() {
        ProductServiceImpl.getInstance(getContext()).deleteProduct(this.code);
    }

    public void setDialogResult(Answer dialogResult){
        answer = dialogResult;
    }

    public interface Answer {
        void finish(int action);
    }

    public void setCode(String code) {
        this.code = code;
    }
}
