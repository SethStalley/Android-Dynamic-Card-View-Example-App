package com.paradisegardens.requi.paradisegardens;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.karim.MaterialTabs;

import java.util.ArrayList;

public class Main extends AppCompatActivity {
    //butterknife library to easily bind layouts
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.material_tabs)
    MaterialTabs mMaterialTabs;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /*Array of data for the Cards */
    private ArrayList<String> mItems;
    private CardViewAdapter mAdapter;

    private ClientController client;
    private RecyclerView recyclerView;

    /**
     * Intent used to start {@link TabsActivity}.
     */
    public Intent startTabsActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //set title and action bar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_main));



        //startTabsActivityIntent = new Intent(this, TabsActivity.class);

        //tabs
        // Initialize the ViewPager and set an adapter



        System.out.println("Connecting to C# Server");

//        client = new ClientController();
//        client.sendMsg();

        //load up the cards
        mItems = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            mItems.add(String.format("Card number %02d", i));
        }
        mAdapter = new CardViewAdapter(mItems);


        //Menu Drawer stuff
//        mNavigationDrawerFragment = (NavigationDrawerFragment)
//                getFragmentManager().findFragmentById(R.id.navigation_drawer);
//
//        // Set up the drawer.
//        mNavigationDrawerFragment.setUp(
//                R.id.navigation_drawer,
//                (DrawerLayout) findViewById(R.id.drawer_layout));
//
        //fill the recyclerView with the cards
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(mAdapter);
    }

}
