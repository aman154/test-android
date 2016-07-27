package com.ibusl.android.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibusl.android.register.R;

import butterknife.ButterKnife;

/**
 * Created by aman on 12/4/16.
 */
public class TransactionsFragment extends Fragment {
    private static final String LOG_TAG = TransactionsFragment.class.getSimpleName();

    public TransactionsFragment() {
        // Required empty public constructor
    }

    public static TransactionsFragment newInstance() {
        return new TransactionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);
        ButterKnife.bind(TransactionsFragment.this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(TransactionsFragment.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
