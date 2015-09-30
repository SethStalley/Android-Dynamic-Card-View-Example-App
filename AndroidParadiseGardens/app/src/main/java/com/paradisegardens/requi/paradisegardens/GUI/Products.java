package com.paradisegardens.requi.paradisegardens.GUI;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;

import com.paradisegardens.requi.paradisegardens.R;


/**
 * Activity to handle showing all the products for a store
 */
public class Products extends AppCompatActivity {
    Toolbar toolbar;
    ViewPagerAdapter adapter;
    ViewPager pager;

    String storeName;
    SlidingTabLayout tabs;

    //Set tab names and number of them
    CharSequence Titles[];
    int Numboftabs =1; //only going to have one tab for products

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get our store name back
        Intent myIntent = getIntent();
        storeName = myIntent.getStringExtra("name");
        int position = myIntent.getIntExtra("position",0);
        //set tab name
        Titles = new CharSequence[]{storeName};

        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //if search change title
        if(position == 6)
            setTitle("Resultados Busqueda");
        /**
         * Tab init stuff on main screen
         */
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs, storeName, position);
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                Intent myIntent=new Intent(this,Main.class);
                startActivity(myIntent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




}


