package com.tabsswipe.adapter;

import com.example.sliding.Fragmentgallery;
import com.example.sliding.Fragmentvideo;
import com.example.sliding.Fragmentnews;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new Fragmentgallery();
        case 1:
            // Games fragment activity
            return new Fragmentvideo();
        case 2:
            // Movies fragment activity
            return new Fragmentnews();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}
