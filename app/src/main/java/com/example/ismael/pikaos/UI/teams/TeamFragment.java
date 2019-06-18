package com.example.ismael.pikaos.UI.teams;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.adapters.online.TeamsViewPagerAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;

public class TeamFragment extends BaseFragment {

    public static final String TAG = "TeamFragment";

    public static TeamFragment newInstance() {
        return new TeamFragment();
    }

    @Override
    public void setTitleFragment() {
        getActivity().setTitle(R.string.title_teams);
    }


    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team, container,false);
        tabLayout = rootView.findViewById(R.id.tbTeams);
        viewPager = rootView.findViewById(R.id.vpTeams);

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitleFragment();
        setLoadedTabs();
    }



    //TABS Y VIEWPAGER
    private void setLoadedTabs() {

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        viewPager.setAdapter(new TeamsViewPagerAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setText("Mi equipo");
        tabLayout.getTabAt(1).setText("Solicitudes");


    }

}
