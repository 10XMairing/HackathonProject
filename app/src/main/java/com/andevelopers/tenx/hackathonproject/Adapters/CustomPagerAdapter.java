package com.andevelopers.tenx.hackathonproject.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andevelopers.tenx.hackathonproject.FragmentFeeds;
import com.andevelopers.tenx.hackathonproject.FragmentForum;
import com.andevelopers.tenx.hackathonproject.FragmentMenu;
import com.andevelopers.tenx.hackathonproject.FragmentSubs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;
    private String[] titles = {"News Feed", "Subs", "Forum"};
    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
        mList = new ArrayList<>();
        mList.add(new FragmentFeeds());
        mList.add(new FragmentSubs());
        mList.add(new FragmentForum());
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
