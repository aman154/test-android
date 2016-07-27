package com.ibusl.android.register.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ibusl.android.register.R;
import com.ibusl.android.register.activity.AddNoteActivity;
import com.ibusl.android.register.utilities.RegisterConstants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterKeypadFragment extends Fragment {
    private static final String LOG_TAG = RegisterKeypadFragment.class.getSimpleName();
    private static final int REQUEST_CODE = 11;
    private OnRegisterKeypadFragmentInteractionListener mListener;
    private Context context;
    private String addNote;

    @Bind(R.id.reg_amount_edit_tv)
    TextView regEditAmount;
    @Bind(R.id.reg_add_note_tv)
    TextView regAddNote;
    @Bind(R.id.reg_keypad_1)
    TextView regKeypad1;
    @Bind(R.id.reg_keypad_2)
    TextView regKeypad2;
    @Bind(R.id.reg_keypad_3)
    TextView regKeypad3;
    @Bind(R.id.reg_keypad_4)
    TextView regKeypad4;
    @Bind(R.id.reg_keypad_5)
    TextView regKeypad5;
    @Bind(R.id.reg_keypad_6)
    TextView regKeypad6;
    @Bind(R.id.reg_keypad_7)
    TextView regKeypad7;
    @Bind(R.id.reg_keypad_8)
    TextView regKeypad8;
    @Bind(R.id.reg_keypad_9)
    TextView regKeypad9;
    @Bind(R.id.reg_keypad_cancel)
    TextView regKeypadCancel;
    @Bind(R.id.reg_keypad_0)
    TextView regKeypad0;
    @Bind(R.id.reg_keypad_plus)
    TextView regKeypadPlus;

    public RegisterKeypadFragment() {
        // Required empty public constructor
    }

    public static RegisterKeypadFragment newInstance() {
        return new RegisterKeypadFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_keypad, container, false);
        context = RegisterKeypadFragment.this.getActivity();
        ButterKnife.bind(this, rootView);
        onKeypadClick(rootView);
        return rootView;
    }

    private StringBuilder amount = new StringBuilder();
    private int salesItemCount = 0;
    private double realAmount = 0.0,oldAmount;

    public void onKeypadPressed(int id) {
        if (id == RegisterConstants.KEYPAD_CANCEL) {
            if( mListener != null) {
                if (realAmount != 0 && salesItemCount > 0) {
                    --salesItemCount;
                    regEditAmount.setText("");
                    amount.setLength(0);
                    mListener.OnRegisterKeypadCancelClick(realAmount, salesItemCount);
                    realAmount = 0.0;
                } else {
                    salesItemCount = 0;
                    realAmount = 0.0;
                    mListener.OnRegisterKeypadCancelClick(0, 0);
                    regEditAmount.setText("");
                }
            }
        } else if (id == RegisterConstants.KEYPAD_PLUS) {
            if (realAmount != 0 && mListener != null) {
                realAmount = 0.0;
                amount.setLength(0);
                regEditAmount.setText("");
                mListener.OnRegisterKeypadAddClick();
            } else {
                Toast.makeText(context, "Add some amount", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (realAmount == 0 && id == RegisterConstants.KEYPAD_0) {
                return;
            } else {
                String text = String.valueOf(id);
                amount.append(text);
                if (Double.parseDouble(amount.toString()) < 1000000) {
                    if (realAmount == 0) {
                        ++salesItemCount;
                    }
                    oldAmount = realAmount;
                    realAmount = Double.parseDouble(amount.toString());
                    regEditAmount.setText(String.valueOf(realAmount));
                    regEditAmount.setTextColor(0xff000000);
                    if (mListener != null) {
                        mListener.OnRegisterKeypadNumberClick(realAmount, oldAmount, salesItemCount);
                    }
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterKeypadFragmentInteractionListener) {
            mListener = (OnRegisterKeypadFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisterKeypadFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(context);
    }

    @OnClick({R.id.reg_add_note_tv, R.id.reg_keypad_1, R.id.reg_keypad_2, R.id.reg_keypad_3, R.id.reg_keypad_4, R.id.reg_keypad_5, R.id.reg_keypad_6,
            R.id.reg_keypad_7, R.id.reg_keypad_8, R.id.reg_keypad_9, R.id.reg_keypad_0, R.id.reg_keypad_cancel, R.id.reg_keypad_plus})
    public void onKeypadClick(View v) {
        int id = v.getId();
        Log.i(LOG_TAG, "Register keypad onClick view id - " + id);
        if (id == R.id.reg_add_note_tv) {
            Intent intent = new Intent(context,AddNoteActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else if (id == R.id.reg_keypad_1) {
            Log.i(LOG_TAG, "Register onClick keypad press id - " + id);
            onKeypadPressed(RegisterConstants.KEYPAD_1);
        } else if (id == R.id.reg_keypad_2) {
            Log.i(LOG_TAG, "Register onClick keypad press id - " + id);
            onKeypadPressed(RegisterConstants.KEYPAD_2);
        } else if (id == R.id.reg_keypad_3) {
            onKeypadPressed(RegisterConstants.KEYPAD_3);
        } else if (id == R.id.reg_keypad_4) {
            onKeypadPressed(RegisterConstants.KEYPAD_4);
        } else if (id == R.id.reg_keypad_5) {
            onKeypadPressed(RegisterConstants.KEYPAD_5);
        } else if (id == R.id.reg_keypad_6) {
            onKeypadPressed(RegisterConstants.KEYPAD_6);
        } else if (id == R.id.reg_keypad_7) {
            onKeypadPressed(RegisterConstants.KEYPAD_7);
        } else if (id == R.id.reg_keypad_8) {
            onKeypadPressed(RegisterConstants.KEYPAD_8);
        } else if (id == R.id.reg_keypad_9) {
            onKeypadPressed(RegisterConstants.KEYPAD_9);
        } else if (id == R.id.reg_keypad_0) {
            onKeypadPressed(RegisterConstants.KEYPAD_0);
        } else if (id == R.id.reg_keypad_cancel) {
            onKeypadPressed(RegisterConstants.KEYPAD_CANCEL);
        } else if (id == R.id.reg_keypad_plus) {
            onKeypadPressed(RegisterConstants.KEYPAD_PLUS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(LOG_TAG,"requestCode - "+requestCode+", "+"resultCode - "+resultCode);
        if(requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    addNote = data.getStringExtra("add_note");
                    Log.i(LOG_TAG,"addNote - "+addNote);
                    regAddNote.setText(addNote);
                    regAddNote.setTextColor(0xff000000);
                }
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRegisterKeypadFragmentInteractionListener {
        void OnRegisterKeypadNumberClick(double realAmount, double oldAmount,  int salesCount);

        void OnRegisterKeypadCancelClick(double amount, int salesCount);

        void OnRegisterKeypadAddClick();
    }
}
