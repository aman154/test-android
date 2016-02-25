package com.example.aman.myapp1.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aman.myapp1.app.AppUtil;
import com.example.aman.myapp1.model.HomeElement;
import com.example.aman.myapp1.R;
import com.example.aman.myapp1.util.AugmentView;
import com.example.aman.myapp1.util.TextViewTimer;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by aman on m1/m7/m15.
 */
public class HomeListAdapter extends ArrayAdapter<HomeElement> {

    LayoutInflater inflater;
    List<HomeElement> elements;
    Context context;
    int layoutResourceId;
    AugmentView cusView;
    private static final String FORMAT = "%02d:%02d:%02d";

    int seconds , minutes;

    public HomeListAdapter(Context context, int layoutResourceId, List<HomeElement> elements){
       super(context,layoutResourceId,elements);

        this.elements = elements;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
        return elements.size();
    }

    public View getView(int position, View view, ViewGroup viewGroup){

        View rootView = view;
      //  cusView = new AugmentView(context);


        if(view == null){

            rootView = inflater.inflate(R.layout.homelistview,viewGroup,false);}

        assert view != null;
        cusView = (AugmentView) rootView.findViewById(R.id.augment_view);

        cusView.getTittle().setText(elements.get(position).getTitle());
        cusView.getDetail().setText("detail");
       // cusView.getIcon().setImageResource(elements.get(position).getImage());

        if(com.example.aman.myapp1.util.AppUtil.checkIsTimeToStartTimer(elements.get(position).getEpoch()).equals(com.example.aman.myapp1.util.AppUtil.TIMER2)) {
            long diff = (elements.get(position).getEpoch()-System.currentTimeMillis()/1000)*1000;
            TextViewTimer tt = new TextViewTimer(diff, 1000, cusView.getTimer());
            tt.start();
        }else if(com.example.aman.myapp1.util.AppUtil.checkIsTimeToStartTimer(elements.get(position).getEpoch()).equals(com.example.aman.myapp1.util.AppUtil.TIMER3)) {
            cusView.getTimer().setText("expire");
        }else if(com.example.aman.myapp1.util.AppUtil.checkIsTimeToStartTimer(elements.get(position).getEpoch()).equals(com.example.aman.myapp1.util.AppUtil.TIMER1)){
            cusView.getTimer().setText(com.example.aman.myapp1.util.AppUtil.calculate_date_diff_from_now_epoch(elements.get(position).getEpoch())+" days to expire");
        }

           /* ImageView imageView = (ImageView) rootView.findViewById(R.id.homelist_image);
            TextView textView = (TextView) rootView.findViewById(R.id.homelist_text);

            imageView.setImageResource(elements.get(position).getImage());
            textView.setText(elements.get(position).getTitle());*/

        return rootView;
    }


}
