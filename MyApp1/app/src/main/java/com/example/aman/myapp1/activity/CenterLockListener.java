package com.example.aman.myapp1.activity;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CenterLockListener extends RecyclerView.OnScrollListener {

    Context context;
    private boolean mAutoSet = true;
    private int mCenterPivot;

    private View view;

    public CenterLockListener(int center,Context context){

        this.mCenterPivot = center;
        this.context = context;

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();

        if( mCenterPivot == 0 ) {

            mCenterPivot = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? ( recyclerView.getLeft() + recyclerView.getRight() ) : ( recyclerView.getTop() + recyclerView.getBottom() );
        }
        if( !mAutoSet ) {

            if( newState == RecyclerView.SCROLL_STATE_IDLE ) {

                View view = findCenterView(lm);

                int position = recyclerView.getChildPosition(view);

                Log.i("CenterLockListener", "list-position" + position);

                Toast.makeText(context,"list-position"+position,Toast.LENGTH_SHORT).show();

                int viewCenter = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? ( view.getLeft() + view.getRight() )/2 :( view.getTop() + view.getBottom() )/2;

                int scrollNeeded = viewCenter - mCenterPivot;

                if( lm.getOrientation() == LinearLayoutManager.HORIZONTAL ) {

                    recyclerView.smoothScrollBy(scrollNeeded, 0);
                }
                else
                {
                    recyclerView.smoothScrollBy(0, (int) (scrollNeeded));

                }
                mAutoSet =true;
            }
        }
        if( newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING ){

            mAutoSet =false;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

    }
    private View findCenterView(LinearLayoutManager lm) {

        int minDistance = 0;
        View view = null;
        View returnView = null;
        boolean notFound = true;

        for(int i = lm.findFirstVisibleItemPosition(); i <= lm.findLastVisibleItemPosition() && notFound ; i++ ) {

            view=lm.findViewByPosition(i);

            int center = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? ( view.getLeft() + view.getRight() )/ 2 : ( view.getTop() + view.getBottom() )/ 2;
            int leastDifference = Math.abs(mCenterPivot - center);

            if( leastDifference <= minDistance || i == lm.findFirstVisibleItemPosition())
            {
                minDistance = leastDifference;
                returnView=view;
                setCenterView(returnView);
            }
            else
            {
                notFound=false;

            }
        }
        return returnView;
    }

    public View  getCenterView(){
      return view;
    }

    public void setCenterView(View view){
        this.view = view;
    }


}