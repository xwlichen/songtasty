package com.song.tasty.demo.adjustableheader;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by liyongan on 19/3/5.
 */
class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    TabFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
