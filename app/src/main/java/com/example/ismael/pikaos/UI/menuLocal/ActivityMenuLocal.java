package com.example.ismael.pikaos.UI.menuLocal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.competitionManageLocal.CompetitionManageLocalPresenter;
import com.example.ismael.pikaos.UI.competitionManageLocal.CompetitionManageLocalView;
import com.example.ismael.pikaos.UI.competitionsLocal.CompetitionLocalListView;
import com.example.ismael.pikaos.UI.newCompetitionLocal.NewCompetitionLocalView;
import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;

public class ActivityMenuLocal extends AppCompatActivity implements CompetitionLocalListView.ICompetitionLocalListView,
        NewCompetitionLocalView.loadCompetitionFragment {

    Toolbar toolbar;
    Fragment fragment;

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if(backStackEntryCount<=1)
            this.finish();
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_local);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setDefaultFragment();
    }


    private void setDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment = fragmentManager.findFragmentByTag(CompetitionLocalListView.TAG);
        if (fragment == null)
            fragment = new CompetitionLocalListView();
        fragmentTransaction.replace(R.id.fl_local, fragment, CompetitionLocalListView.TAG);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void setNewCompetitionFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment = fragmentManager.findFragmentByTag(NewCompetitionLocalView.TAG);
        if (fragment == null)
            fragment = new NewCompetitionLocalView();
        fragmentTransaction.replace(R.id.fl_local, fragment, NewCompetitionLocalView.TAG);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public void setCompetitionManage(CompetitionLocalView competition) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CompetitionManageLocalView.TAG,competition);

        fragment = CompetitionManageLocalView.newInstance(bundle);

        fragmentTransaction.replace(R.id.fl_local,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void setCompetitionFragment() {
        setDefaultFragment();
    }

}
