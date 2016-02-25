package com.example.aman.myapp1.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.adapter.RecyclerAdapter;
import com.example.aman.myapp1.model.MoviesDetail;

import java.util.ArrayList;
import java.util.List;


public class MoviesFragment extends Fragment {

    List<com.example.aman.myapp1.model.MoviesDetail> details;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerAdapter adapter;
    FragmentActivity fragmentActivity;
 //   RatingBar ratingBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        init();

       /* ratingBar = (RatingBar) rootView.findViewById(R.id.card_rating_movie);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                float rating1 = rating;
            }
        });*/

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new RecyclerAdapter(details);

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            public void onItemClick(View view, int position) {

                MoviesDetailFragment mFragment = new MoviesDetailFragment();

                ImageView image = (ImageView) view.findViewById(R.id.augment_icon_image);
                String transitionName = getString(R.string.movie_image_tran);
               /* TransitionSet transitionSet = new TransitionSet();
                transitionSet.addTransition(new ChangeImageTransform());
                transitionSet.addTransition(new ChangeBounds());
                transitionSet.setDuration(30);
                mFragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.tran_move));
                mFragment.setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));
                mFragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));
                mFragment.setSharedElementEnterTransition(transitionSet);
                mFragment.setSharedElementReturnTransition(transitionSet);
                Fade fade = new Fade();
                fade.setStartDelay(30);
                mFragment.setEnterTransition(fade);*/
               // setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.trans_exit_move));
                //mFragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.trans_exit_move));

                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.tab_container, mFragment, mFragment.toString());
               // trans.addSharedElement(image, transitionName);
                trans.addToBackStack(mFragment.toString());
                trans.commit();
            }
        });
        recyclerView.setAdapter(adapter);


        return rootView;

    }

    public void init() {
        details = new ArrayList<>();
        details.add(new MoviesDetail(R.drawable.india,"India","india"));
        details.add(new MoviesDetail(R.drawable.m1,"Dawn of the Planet of the Apes","india"));
        details.add(new MoviesDetail(R.drawable.m2,"District 9","india"));
        details.add(new MoviesDetail(R.drawable.m3,"Transformers: Age of Extinction", "india"));
        details.add(new MoviesDetail(R.drawable.m4, "X-Men: Days of Future Past","india"));
       /* details.add(new MoviesDetail(R.drawable.m5,"The Machinist","india"));
        details.add(new MoviesDetail(R.drawable.m6,"The Last Samurai","india"));
        details.add(new MoviesDetail(R.drawable.m7,"The Amazing Spider-Man 2","india"));
        details.add(new MoviesDetail(R.drawable.m8,"Tangled","india"));
        details.add(new MoviesDetail(R.drawable.m9,"Rush","india"));
        details.add(new MoviesDetail(R.drawable.m10,"Drag Me to Hell","india"));
        details.add(new MoviesDetail(R.drawable.m11,"Despicable Me 2","india"));
        details.add(new MoviesDetail(R.drawable.m12,"Kill Bill: Vol. 1","india"));
        details.add(new MoviesDetail(R.drawable.m13,"A Bug's Life","india"));
        details.add(new MoviesDetail(R.drawable.m14,"Life of Brian","india"));
        details.add(new MoviesDetail(R.drawable.m15,"How to Train Your Dragon","india"));
        details.add(new MoviesDetail(R.drawable.index2,"Index2","india"));
        details.add(new MoviesDetail(R.drawable.index1,"Index1","india"));*/
    }


}
