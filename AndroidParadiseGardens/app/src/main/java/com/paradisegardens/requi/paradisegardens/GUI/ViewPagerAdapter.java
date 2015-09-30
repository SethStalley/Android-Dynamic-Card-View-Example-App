package com.paradisegardens.requi.paradisegardens.GUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.paradisegardens.requi.paradisegardens.GUI.CardFiller;
import com.paradisegardens.requi.paradisegardens.GUI.Main;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    Main main;

    String storeName;
    Boolean products;
    int position; //which product

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(Main main, FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.main = main;
        products = false;
    }

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[],
                            int mNumbOfTabsumb, String storeName, int position) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        products = true;
        this.storeName = storeName;
        this.position = position;
    }


    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        CardFiller tab = new CardFiller();
        tab.setMain(main);

        //set our position value so our CardFiller knows which tab we are on
        Bundle args = new Bundle();
        //if we are on a products tab
        if(products)
            position = this.position;
        //pass these to Cardfiller through bundle
        args.putInt("pos", position);
        args.putBoolean("products", products);
        Log.e("ProductsPage", products.toString());
        args.putString("storeName", storeName);
        tab.setArguments(args);

        return tab;
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}