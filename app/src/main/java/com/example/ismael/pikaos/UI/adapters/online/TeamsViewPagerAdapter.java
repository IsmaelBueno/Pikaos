package com.example.ismael.pikaos.UI.adapters.online;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ismael.pikaos.UI.teams.TeamManageFragment;
import com.example.ismael.pikaos.UI.teams.TeamRequestFragment;

public class TeamsViewPagerAdapter extends FragmentStatePagerAdapter {


    public TeamsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new Fragment();

        switch (position) {
            case 0:
                fragment = TeamManageFragment.newInstance();
                break;
            case 1:
                fragment = TeamRequestFragment.newInstance();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
