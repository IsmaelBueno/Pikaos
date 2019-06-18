package com.example.ismael.pikaos.UI.menu;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.competetions.CompetitionsFragment;
import com.example.ismael.pikaos.UI.friends.FriendsFragment;
import com.example.ismael.pikaos.UI.myCompetitions.MyCompetitionsFragment;
import com.example.ismael.pikaos.UI.settings.SettingsFragment;
import com.example.ismael.pikaos.UI.teams.TeamFragment;
import com.example.ismael.pikaos.UI.user.UserFragment;
import com.squareup.picasso.Picasso;

public class MenuActivity extends AppCompatActivity {

    Fragment fragment;
    Fragment settingsFragment;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navview);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setUserHeader();

        setNavigationViewListener();

        setDefaultFragment();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void closeSession(){
        this.finish();
    }

    private void setDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment = fragmentManager.findFragmentByTag(CompetitionsFragment.TAG);
        if (fragment == null)
            fragment = new CompetitionsFragment();
        fragmentTransaction.replace(R.id.frameLayoutMenu, fragment, CompetitionsFragment.TAG);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        navigationView.getMenu().getItem(0).setChecked(true);
    }


    //NAVEGATION DRAWER
    private void setNavigationViewListener(){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        //Si es ssettingse, el fragment es no hereda de Fragment
                        if(item.getItemId() == R.id.action_menu_settings){
                            settingsFragment = fragmentManager.findFragmentByTag(SettingsFragment.TAG);
                            if(settingsFragment==null)
                                settingsFragment = new SettingsFragment();
                            fragmentTransaction.replace(R.id.frameLayoutMenu, settingsFragment,SettingsFragment.TAG);


                        }else {
                            switch (item.getItemId()) {
                                case R.id.action_menu_competitions:
                                    fragment = fragmentManager.findFragmentByTag(CompetitionsFragment.TAG);
                                    if (fragment == null)
                                        fragment = new CompetitionsFragment();
                                    fragmentTransaction.replace(R.id.frameLayoutMenu, fragment, CompetitionsFragment.TAG);
                                    break;

                                case R.id.action_menu_mycompetitions:
                                    fragment = fragmentManager.findFragmentByTag(MyCompetitionsFragment.TAG);
                                    if (fragment == null)
                                        fragment = new MyCompetitionsFragment();
                                    fragmentTransaction.replace(R.id.frameLayoutMenu, fragment, MyCompetitionsFragment.TAG);
                                    break;

                                case R.id.action_menu_friends:
                                    //fragment = fragmentManager.findFragmentByTag(FriendsFragment.TAG);
                                    //  if(fragment==null)
                                    fragment = new FriendsFragment();
                                    fragmentTransaction.replace(R.id.frameLayoutMenu, fragment, FriendsFragment.TAG);
                                    break;

                                case R.id.action_menu_teams:
                                    //fragment = fragmentManager.findFragmentByTag(TeamManageFragment.TAG);
                                    //  if(fragment==null)
                                    fragment = new TeamFragment();
                                    fragmentTransaction.replace(R.id.frameLayoutMenu, fragment, TeamFragment.TAG);
                                    break;

                                case R.id.action_menu_user:
                                    fragment = fragmentManager.findFragmentByTag(UserFragment.TAG);
                                    if (fragment == null)
                                        fragment = new UserFragment();
                                    fragmentTransaction.replace(R.id.frameLayoutMenu, fragment, UserFragment.TAG);
                                    break;

                                case R.id.action_menu_logout:
                                    closeSession();
                                    break;
                            }
                        }

                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        drawerLayout.closeDrawers();

                        //Actualizar la cabecera
                        setUserHeader();

                        return true;
                    }
                });
    }

    private void setUserHeader(){
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.tvHeaderName);
        nav_user.setText(PikaosApplication.userName);

        ImageView nav_avatar = hView.findViewById(R.id.imgHeaderAvatar);
        if(PikaosApplication.userAvatar!=null)
            Picasso.get().load(PikaosApplication.userAvatar).into(nav_avatar);
    }

}



