package com.codepath.apps.restclienttemplate.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.restclienttemplate.Fragment.MentionFragment;
import com.codepath.apps.restclienttemplate.Fragment.PageFragment;
import com.codepath.apps.restclienttemplate.Fragment.TimelineFragment;

/**
 * Created by chandrav on 2/28/15.
 */
public class SampleFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Home", "Mentions" };

    public SampleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return TimelineFragment.newInstance();
                //return PageFragment.newInstance(position + 1);
            case 1:
                return MentionFragment.newInstance();
                //return PageFragment.newInstance(position + 1);
            default:
                return PageFragment.newInstance(position + 1);
        }
        
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
