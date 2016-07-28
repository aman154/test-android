package com.example.aman.myapp1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ContainerFragmentTest extends Fragment implements View.OnClickListener {

    private Button mBtn;
    int mCurrentlyShowingFragment = 1;

    public ContainerFragmentTest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ContainerFragmentTest.
     */
    // TODO: Rename and change types and number of parameters
    public static ContainerFragmentTest newInstance() {
        ContainerFragmentTest fragment = new ContainerFragmentTest();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_container_fragment_test, container, false);
        mBtn = (Button) fragmentView.findViewById(R.id.btn_nested_frag);
        mBtn.setOnClickListener(this);
        return fragmentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            if (savedInstanceState == null) {
                getChildFragmentManager().beginTransaction().replace(R.id.container, ViewPagerFragmentTest.newInstance("some string")).commit();
                mCurrentlyShowingFragment = 0;
            } else {
                mCurrentlyShowingFragment = savedInstanceState.getInt("currently_showing_fragment");
            }
        adjustButtonText();
    }

    @Override
    public void onClick(View v) {
        if (mCurrentlyShowingFragment == 0) {
            showNextScreen();
        } else {
            showPreviousScreen();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currently_showing_fragment", mCurrentlyShowingFragment);
    }

    private void adjustButtonText() {
        if(mCurrentlyShowingFragment == 0){
            mBtn.setText("Next");
        } else {
            mBtn.setText("Previous");
        }
    }

    private void showNextScreen() {
        mCurrentlyShowingFragment = 1;
        getChildFragmentManager().beginTransaction().replace(R.id.container, ViewPagerFragmentTest.newInstance("string")).addToBackStack(null).commit();
        adjustButtonText();
    }

    private void showPreviousScreen() {
        mCurrentlyShowingFragment = 0;
        getChildFragmentManager().popBackStackImmediate();
        adjustButtonText();

    }
}
