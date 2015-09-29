package com.paradisegardens.requi.paradisegardens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class CardFiller extends Fragment {

    //Stuff for the cards
    private ArrayList<String> mItems;
    private ArrayList<String> imgUrls;
    List cardsInfo;

    private CardViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private Data data = new Data();

    //our webService URLS
    private String IP = "192.168.0.5";
    private String ATRACTION_URL = "http://"+IP+"/~seth/rest/atractions.php";
    private String SHOWS_URL = "http://192.168.0.5/~seth/rest/shows.php";
    private String RESTAURANT_URL = "http://192.168.0.5/~seth/rest/restaurants.php";
    private String STORES_URL = "http://192.168.0.5/~seth/rest/stores.php";
    //names of data we want from webService
    ArrayList <String> params = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_activity,container,false);

        //clear our array of params, we might have used it on another tab
        params.clear();

        //get the tab position from our values
        int position = getArguments().getInt("pos");

        //which attraction tab are we on?
        switch (position){
            case 0:
                params.add("name");
                params.add("description");
                params.add("schedule");
                params.add("state");
                params.add("capacity");
                //load up the cards
                cardsInfo = data.getCardData(ATRACTION_URL, params);
                imgUrls = (ArrayList<String>) cardsInfo.get(0);
                mItems = (ArrayList<String>) cardsInfo.get(1);
                break;
            case 1:
                params.add("name");
                params.add("schedule");
                params.add("location");
                //load up the cards
                cardsInfo = data.getCardData(SHOWS_URL, params);
                imgUrls = (ArrayList<String>) cardsInfo.get(0);
                mItems = (ArrayList<String>) cardsInfo.get(1);
                break;
            case 2:
                params.add("name");
                params.add("schedule");
                //load up the cards
                cardsInfo = data.getCardData(RESTAURANT_URL, params);
                imgUrls = (ArrayList<String>) cardsInfo.get(0);
                mItems = (ArrayList<String>) cardsInfo.get(1);
                break;
            case 3:
                params.add("name");
                params.add("schedule");
                //load up the cards
                cardsInfo = data.getCardData(STORES_URL, params);
                imgUrls = (ArrayList<String>) cardsInfo.get(0);
                mItems = (ArrayList<String>) cardsInfo.get(1);
                break;
        }




        mAdapter = new CardViewAdapter(mItems, imgUrls);
        //fill the recyclerView with the cards
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(mAdapter);

        return v;
    }
}