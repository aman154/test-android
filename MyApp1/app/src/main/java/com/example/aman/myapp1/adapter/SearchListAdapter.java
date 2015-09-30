package com.example.aman.myapp1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
 * Created by aman on 14/9/15.
 */
public class SearchListAdapter extends BaseAdapter {

    private final String TAG = "SearchListAdapter";
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<PlaceResult> placeResults;

    public SearchListAdapter(Context context, ArrayList<PlaceResult> placeResults) {
        this.context = context;
        this.placeResults = placeResults;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return placeResults.size();
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
        Log.i(TAG,"placeResultArraysize"+placeResults.size());
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