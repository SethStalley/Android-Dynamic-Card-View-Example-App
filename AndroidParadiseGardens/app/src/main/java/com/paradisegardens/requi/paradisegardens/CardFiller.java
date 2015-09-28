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


public class CardFiller extends Fragment {

    //Stuff for the cards
    private ArrayList<String> mItems;

    private CardViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private Data data = new Data();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_activity,container,false);

        //get the tab position from our values
        int position = getArguments().getInt("pos");

        //if attractions tab
        if (position == 0){
            //load up the cards
            mItems = data.getCardData();
        }

        mAdapter = new CardViewAdapter(mItems);
        //fill the recyclerView with the cards
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(mAdapter);

        return v;
    }
}