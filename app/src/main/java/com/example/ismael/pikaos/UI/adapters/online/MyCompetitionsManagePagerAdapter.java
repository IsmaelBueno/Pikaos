package com.example.ismael.pikaos.UI.adapters.online;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ismael.pikaos.UI.myCompetitionsManage.MyCompetitionsManageChatView;
import com.example.ismael.pikaos.UI.myCompetitionsManage.MyCompetitionsManageConfrontationView;
import com.example.ismael.pikaos.UI.myCompetitionsManage.MyCompetitionsManageTableView;
import com.example.ismael.pikaos.data.model.online.MyCompetition;

public class MyCompetitionsManagePagerAdapter extends FragmentPagerAdapter {
    private int pageCount;
    private MyCompetition myCompetition;

    public MyCompetitionsManagePagerAdapter(FragmentManager fm, int pageCount, MyCompetition myCompetition) {
        super(fm);
        this.pageCount=pageCount;
        this.myCompetition = myCompetition;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = MyCompetitionsManageChatView.newInstance(myCompetition.getChat(),myCompetition.getName());
                break;
            case 1:
                fragment = MyCompetitionsManageConfrontationView.newInstance(myCompetition);
                break;
            case 2:
                fragment = MyCompetitionsManageTableView.newInstance(myCompetition);
                break;
        }
            return fragment;
        }

        @Override
        public int getCount() {
            return pageCount;
        }

    }
