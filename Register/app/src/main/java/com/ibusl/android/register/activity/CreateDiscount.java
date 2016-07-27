package com.ibusl.android.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibusl.android.register.R;
import com.ibusl.android.register.model.Discount;
import com.ibusl.android.register.utilities.AndroidUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateDiscount extends AppCompatActivity {
    private static final String LOG_TAG = "CreateDiscountActivity";
    private Discount discount = null;
    /**
     * default discount type is 0(percentage)
     * and user can set it as 1(currency ex-rupee) also.
     */
    private int discountType = 0;
    private String discountPerValue = null;
    private String discountCurrValue = null;

    @Bind(R.id.discount_activity_toolbar)
    Toolbar discountToolbar;
    @Bind(R.id.discount_save_bt)
    Button discountSaveButton;
    @Bind(R.id.discount_photo_viewer)
    View discountPhotoViewer;
    @Bind(R.id.discount_name_et)
    EditText discountNameEt;
    @Bind(R.id.discount_amount_et)
    EditText discountAmountEt;
    @Bind(R.id.discount_type_percentage_tv)
    TextView discountTypePerTv;
    @Bind(R.id.discount_type_currency_tv)
    TextView discountTypeCurrencyTv;
    @Bind(R.id.discount_currency_tv)
    TextView discountCurrencyTv;
    @Bind(R.id.discount_amount_tv)
    TextView discountAmountTv;
    @Bind(R.id.discount_percentage_tv)
    TextView discountPerTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_discount);
        ButterKnife.bind(CreateDiscount.this);

        AndroidUtil.setDrawableColor(this, "#ffffff", R.drawable.ic_local_offer_black_24dp);

        ImageView imageView = (ImageView) discountPhotoViewer.findViewById(R.id.photo_viewer_image_view);
        imageView.setBackgroundColor(0xff757575);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_local_offer_black_24dp));
        final TextView textView = (TextView) discountPhotoViewer.findViewById(R.id.photo_viewer_image_text_view);
        final TextView textView1 = (TextView) discountPhotoViewer.findViewById(R.id.photo_viewer_text_view);

        Intent intent = getIntent();
        if(intent != null){


        }

        discountNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textView1 != null) {
                    textView1.setText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        discountTypePerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discountType = 0;
                discountAmountEt.setPadding(0, 0, 0, 0);
                if (discountPerValue != null && discountPerValue.length() > 0) {
                    discountAmountEt.setText(discountPerValue);
                    discountAmountEt.setSelection(discountPerValue.length());
                    discountAmountTv.setVisibility(View.VISIBLE);
                    discountAmountTv.setText(discountPerValue);
                    discountPerTv.setVisibility(View.VISIBLE);
                    textView.setText(discountPerValue + "$");

                } else {
                    discountAmountEt.setText("");
                    textView.setText("");
                    discountAmountTv.setVisibility(View.GONE);
                    discountPerTv.setVisibility(View.GONE);
                    discountCurrencyTv.setVisibility(View.GONE);
                }
                discountAmountEt.setHint("0%");
                discountTypePerTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_thin_border_pressed_drawable));
                discountTypeCurrencyTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_thin_border_drawable));
            }
        });

        discountTypeCurrencyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discountType = 1;
                Log.i(LOG_TAG, "discountTypeCurrencyTv.setOnClickListener discountCurrValue - " + discountCurrValue);
                if (discountCurrValue != null && discountCurrValue.length() > 0) {
                    Log.i(LOG_TAG, "if discountTypeCurrencyTv.setOnClickListener");
                    discountAmountEt.setPadding(AndroidUtil.dp(10), 0, 0, 0);
                    discountAmountEt.setText(discountCurrValue);
                    discountAmountEt.setSelection(discountCurrValue.length());
                    discountAmountTv.setVisibility(View.VISIBLE);
                    discountAmountTv.setText(discountCurrValue);
                    discountCurrencyTv.setVisibility(View.VISIBLE);
                    textView.setText("$" + discountCurrValue);
                } else {
                    Log.i(LOG_TAG, "else discountTypeCurrencyTv.setOnClickListener");
                    discountAmountEt.setText("");
                    textView.setText("");
                    discountAmountTv.setVisibility(View.GONE);
                    discountCurrencyTv.setVisibility(View.GONE);
                    discountPerTv.setVisibility(View.GONE);
                }
                discountAmountEt.setHint("$0.0");
                discountTypePerTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_thin_border_drawable));
                discountTypeCurrencyTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_thin_border_pressed_drawable));
            }
        });

        discountAmountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(LOG_TAG, "discountAmountEt onTextChanged CharSequence - " + s + ", " + "count - " + count);

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(LOG_TAG, "discountAmountEt afterTextChanged - " + s.toString());
                discountAmountTv.setVisibility(View.VISIBLE);
                discountAmountTv.setText(s.toString());
                if (discountType == 0) {
                    discountPerValue = s.toString();
                    discountCurrencyTv.setVisibility(View.GONE);
                    discountPerTv.setVisibility(View.VISIBLE);
                    textView.setText(s.toString() + "%");

                } else if (discountType == 1) {
                    discountAmountEt.setPadding(AndroidUtil.dp(10), 0, 0, 0);
                    discountCurrValue = s.toString();
                    discountCurrencyTv.setVisibility(View.VISIBLE);
                    discountPerTv.setVisibility(View.GONE);
                    textView.setText("$" + s.toString());

                }
                if (s.length() <= 0) {
                    discountAmountEt.setPadding(0, 0, 0, 0);
                    textView.setText("");
                    discountAmountTv.setVisibility(View.GONE);
                    discountCurrencyTv.setVisibility(View.GONE);
                    discountPerTv.setVisibility(View.GONE);
                }
            }

        });

        discountSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    discount = new Discount();
                    discount.setDiscountName(discountNameEt.getText().toString());
                    discount.setDiscountQuantity(discountAmountEt.getText().toString());
                    discount.setDiscountType(discountType);

                    Log.i(LOG_TAG, "Discount ," + "discountNameEt - " + discount.getDiscountName() + ", discountAmountEt - " + discount.getDiscountQuantity() + ", discountType - " + discount.getDiscountType());
                }
            }
        });
    }

    private boolean validateData() {
        boolean validation = false;
        if (discountNameEt != null && discountNameEt.getText().length() > 0 && discountAmountEt != null) {
            validation = true;
        }
        return validation;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(CreateDiscount.this);
    }
}
