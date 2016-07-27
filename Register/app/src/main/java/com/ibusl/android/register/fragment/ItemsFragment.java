package com.ibusl.android.register.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ibusl.android.register.R;
import com.ibusl.android.register.activity.CommonItemsViewActivity;
import com.ibusl.android.register.adapter.ItemsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemsFragment extends Fragment {

    @Bind(R.id.items_list_view)
    ListView listView;

    public ItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_items, container, false);
        ButterKnife.bind(this, rootView);

        listView.setBackgroundColor(0xffffffff);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setVerticalScrollBarEnabled(false);
        listView.setAdapter(new ItemsAdapter(ItemsFragment.this.getActivity()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ItemsFragment.this.getActivity(),CommonItemsViewActivity.class);
                intent.putExtra("item_type",position);
                startActivity(intent);
            }
        });

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(ItemsFragment.this.getActivity());
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
