package com.chatlocaly.adapter.CategoryViewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chatlocaly.fragment.MarketFragment;
import com.chatlocaly.fragment.TagFragments;
import com.chatlocaly.fragment.ThreadesFragment;

/**
 * Created by anjani on 16/12/17.
 */

public class CategoryViewpagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Market ", "Threads", "Tags"};
    private Context context;

    public CategoryViewpagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        switch (position){

            case 0:
                fragment=new MarketFragment();
                break;
            case 1:
                fragment=new ThreadesFragment();
                break;
            case 2:
                fragment=new TagFragments();
                break;
           default:
             fragment=new MarketFragment();


        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}