package com.example.administrator.aap_interphase.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdpter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    //按照歌手的名字将歌曲分类
    private String tabTitles[] = new String[]{"本地音乐", "歌手"};

    public FragmentAdpter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
