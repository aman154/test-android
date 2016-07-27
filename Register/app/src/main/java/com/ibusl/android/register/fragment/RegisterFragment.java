package com.ibusl.android.register.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibusl.android.register.R;
import com.ibusl.android.register.adapter.RegisterFragmentPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aman on 12/4/16.
 */
public class RegisterFragment extends Fragment implements RegisterKeypadFragment.OnRegisterKeypadFragmentInteractionListener {
    View rootView;

    @Bind(R.id.reg_fragments_tab_layout)
    TabLayout regTabLayout;
    @Bind(R.id.reg_fragment_view_pager)
    ViewPager fragmentViewPager;
    @Bind(R.id.reg_sale_ll)
    LinearLayout regSaleLL;
    @Bind(R.id.reg_charge_ll)
    LinearLayout regChargeLL;
    @Bind(R.id.reg_sale_tv)
    TextView regSaleTextView;
    @Bind(R.id.reg_sale_count_tv)
    TextView regSaleCountTextView;
    @Bind(R.id.reg_charge_tv)
    TextView regChargeTextView;
    @Bind(R.id.reg_money_symbol_tv)
    TextView regMoneySymbolTextView;
    @Bind(R.id.reg_charge_amount_tv)
    TextView regChargeAmountTextView;

    private RegisterFragmentPagerAdapter fragmentPagerAdapter;
    private int count = 0;
    private double totalAmount = 0;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(RegisterFragment.this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.content_register,container,false);
            ButterKnife.bind(RegisterFragment.this, rootView);
        }

        fragmentPagerAdapter = new RegisterFragmentPagerAdapter(getFragmentManager());
        fragmentViewPager.setAdapter(fragmentPagerAdapter);
        regTabLayout.setupWithViewPager(fragmentViewPager);

        return rootView;//inflater.inflate(R.layout.content_register,container,false);//super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void OnRegisterKeypadNumberClick(double realAmount, double oldAmount, int salesCount) {
        if(totalAmount == 0){
            regSaleCountTextView.setVisibility(View.VISIBLE);
            regChargeTextView.setVisibility(View.VISIBLE);
            regSaleTextView.setText("Current Sale");
        }
        if(salesCount == 1){
            regChargeAmountTextView.setText(String.valueOf(realAmount));
            totalAmount = realAmount;
        }else {
            totalAmount = totalAmount - oldAmount;
            totalAmount = totalAmount + realAmount;
            regChargeAmountTextView.setText(String.valueOf(totalAmount));
        }
        count = salesCount;
        regSaleCountTextView.setText(String.valueOf(count));
    }

    @Override
    public void OnRegisterKeypadCancelClick(double amount, int salesCount) {
        if (salesCount == 0) {
            totalAmount = 0;
            count = 0;
            regSaleCountTextView.setVisibility(View.GONE);
            regChargeTextView.setVisibility(View.GONE);
            regSaleTextView.setText("No Sale");
            regChargeAmountTextView.setText("");
        }else {
            count = salesCount;
            totalAmount = totalAmount - amount;
            regChargeAmountTextView.setText(String.valueOf(totalAmount));
            regSaleCountTextView.setText(String.valueOf(count));
        }
    }

    @Override
    public void OnRegisterKeypadAddClick() {
    }
}
