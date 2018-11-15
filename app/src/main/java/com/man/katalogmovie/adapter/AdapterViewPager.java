package com.man.katalogmovie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.man.katalogmovie.fragment.FavoriteFragment;
import com.man.katalogmovie.fragment.NowplayingFragment;
import com.man.katalogmovie.fragment.UpcomingFragment;

public class AdapterViewPager extends FragmentPagerAdapter {
    private int PAGE_COUNT;

    public AdapterViewPager(FragmentManager fm, int PAGE_COUNT) {
        super(fm);
        this.PAGE_COUNT = PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new UpcomingFragment();
            case 1:
                return new NowplayingFragment();
            case 2:
                return new FavoriteFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
