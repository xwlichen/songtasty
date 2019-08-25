package com.song.tasty.common.core.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author lichen
 * @date ：2019-08-24 17:29
 * @email : 196003945@qq.com
 * @description :
 */
public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private CharSequence[] titles;

    public BaseViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.fragments = list;
    }

    public BaseViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> list, CharSequence[] titles) {
        super(fragmentManager);
        this.fragments = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && position < titles.length) {
            return titles[position];
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
