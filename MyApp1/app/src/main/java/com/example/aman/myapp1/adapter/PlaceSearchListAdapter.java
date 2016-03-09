package com.example.aman.myapp1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.model.PlaceResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by aman on 8/3/16.
 */
public class PlaceSearchListAdapter extends BaseAdapter {

    private final String TAG = "SearchListAdapter";
    Context context;
    LayoutInflater layoutInflater;
    private ArrayList<PlaceResult> placeResults;
    private int feed_state = FEED_STATE_IDLE;
    public static final int FEED_STATE_IDLE = 1,FEED_STATE_PAGINATION = 2;

    public PlaceSearchListAdapter(Context context, ArrayList<PlaceResult> placeResults) {
        this.context = context;
        this.placeResults = placeResults;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setPlaceResults(ArrayList<PlaceResult> placeResults) {
        this.placeResults = placeResults;
        this.notifyDataSetChanged();
    }

    public int getFeed_state() {
        return feed_state;
    }

    public void setFeed_state(int feed_state) {
        this.feed_state = feed_state;
    }


    @Override
    public int getCount() {
        if(placeResults != null) {
            return placeResults.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return placeResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PlaceResult result;
        Log.i(TAG, "placeResultArraysize" + placeResults.size());
        result = placeResults.get(position);

        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.search_list_item, null);}

        TextView search_name = (TextView) convertView.findViewById(R.id.search_name_tv);
        TextView search_add = (TextView) convertView.findViewById(R.id.search_add_tv);
        ImageView search_image = (ImageView) convertView.findViewById(R.id.search_image);

        if(result != null){
            search_name.setText(result.getName());

            search_add.setText("Address-" + result.getAddress());

            // String urlS = result.getIcon();
            String photoRef = result.getPhotoRef();

            String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=120&photoreference="+photoRef+"&sensor=true&key=AIzaSyDgpkdlHyCjPFUw-SV1BUCIJOgdAvnVUfI";


            Log.i(TAG,"name"+result.getName()+","+"add"+result.getAddress()+","+"url"+url);

            if (url != null && url.length() > 0) {
                Picasso.with(context)
                        .load(url)
                        .placeholder(R.drawable.title_about_default)
                        .error(R.drawable.title_about_alt)
                        .into(search_image);

            }
        }

        return convertView;
    }

}
