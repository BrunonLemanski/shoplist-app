package com.pjatk.brunolemanski.shoplist;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


/**
 * Class responsible for Dialog form which add new item to shop list.
 */
public class DialogForm extends AppCompatDialogFragment {

    private TextInputLayout eTNameProduct;
    private TextInputLayout eTPrice;
    private TextInputLayout eTQuanity;
    private IDialogFormListener listener;


    /**
     * Putting values from form to activity
     */
    public interface IDialogFormListener {
        void applyTexts(String nameOfProduct, String price, String quantity);
    }


    /**
     * Creating dialog form.
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_form, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Add product")
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .show();

        eTNameProduct = view.findViewById(R.id.tINameProduct);
        eTPrice = view.findViewById(R.id.tIPrice);
        eTQuanity = view.findViewById(R.id.tIQuantity);

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (confirmInput(v)) {
                    dialog.dismiss();
                }
            }
        });

        return dialog;
    }


    /**
     * Validate data from form.
     * @return Return true/false if correct/incorrect.
     */
    private boolean validateTitle() {
        String title = eTNameProduct.getEditText().getText().toString().trim();

        if (title.isEmpty()) {
            eTNameProduct.setError("Field can't be empty");
            return false;
        } else if (title.length() > 25) {
            eTNameProduct.setError("Title too long");
            return false;
        } else {
            eTNameProduct.setError(null);
            return true;
        }
    }


    /**
     * Validate price name from form.
     * @return Return true/false if correct/incorrect.
     */
    private boolean validatePrice() {
        String price = eTPrice.getEditText().getText().toString().trim();

        if(price.isEmpty()) {
            eTPrice.setError("Field can't be empty");
            return false;
        } else {
            eTPrice.setError(null);
            eTPrice.setErrorEnabled(false);
            return true;
        }
    }


    /**
     * Validate quantity name from form.
     * @return Return true/false if correct/incorrect.
     */
    private boolean validateQuantity() {
        String quantity = eTQuanity.getEditText().getText().toString().trim();

        if(quantity.isEmpty()) {
            eTQuanity.setError("Field can't be empty");
            return false;
        } else {
            eTQuanity.setError(null);
            eTQuanity.setErrorEnabled(false);
            return true;
        }

    }


    /**
     * Confirm input from form.
     * @param v
     * @return
     */
    public boolean confirmInput(View v) {
        String title = eTNameProduct.getEditText().getText().toString().trim();
        String price = eTPrice.getEditText().getText().toString().trim();
        String quantity = eTQuanity.getEditText().getText().toString().trim();

        if(!validateTitle() | !validatePrice() | !validateQuantity()) {
            return false;
        } else {
            listener.applyTexts(title, price, quantity);
            return true;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            listener = (IDialogFormListener) context;
    }
}
