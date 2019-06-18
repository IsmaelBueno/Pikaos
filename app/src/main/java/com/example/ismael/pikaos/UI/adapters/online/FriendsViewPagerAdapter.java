package com.example.ismael.pikaos.UI.adapters.online;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ismael.pikaos.UI.friends.FriendsListFragment;
import com.example.ismael.pikaos.UI.friends.FriendRequestsFragment;

public class FriendsViewPagerAdapter extends FragmentStatePagerAdapter {

    public FriendsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) {
            FriendsListFragment friendsListFragment = FriendsListFragment.newInstance();
            return friendsListFragment;
        }
        else {
            FriendRequestsFragment friendsRequestFragment = FriendRequestsFragment.newInstance();
            return friendsRequestFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
