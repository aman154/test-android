package com.example.aman.myapp1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class ViewPagerFragmentTest extends Fragment {
    private static final String TAG  = "ViewPagerFragmentTest";
    private String string, string1;
    private TextView textView;
    EditText editText;


    public ViewPagerFragmentTest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ViewPagerFragmentTest.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPagerFragmentTest newInstance(String param1) {
        Log.v(TAG, "newInstance");
        ViewPagerFragmentTest fragment = new ViewPagerFragmentTest();
        Bundle args = new Bundle();
        args.putString("string_", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            string = getArguments().getString("string_");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_view_pager_fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onViewCreated");
        textView = (TextView) view.findViewById(R.id.page_num);

        editText = (EditText) view.findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                string1 = s.toString();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated");
        textView.setText("Page num -"+string);
        if (savedInstanceState != null) {
            editText.setText(savedInstanceState.getString("string_1"));
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onViewStateRestored");
        super.onViewStateRestored(savedInstanceState);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v(TAG, "onSaveInstanceState");
        outState.putString("string_1", string1);
    }
}
