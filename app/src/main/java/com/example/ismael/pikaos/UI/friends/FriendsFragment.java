package com.example.ismael.pikaos.UI.friends;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.adapters.online.FriendsViewPagerAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;


public class FriendsFragment extends BaseFragment implements FriendsContract.view{

    public static final String TAG = "FriendsFragmentTAG";

    @Override
    public void setTitleFragment() {
        getActivity().setTitle(R.string.title_friends);
    }

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FriendsContract.presenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends,container,false);
        tabLayout = rootView.findViewById(R.id.tbFriends);
        viewPager = rootView.findViewById(R.id.vpFriends);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitleFragment();

        presenter = new FriendsPresenter(this);
        setLoadedTabs();

    }

    public void setLoadedTabs() {

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        viewPager.setAdapter(new FriendsViewPagerAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setText("Lista");
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.tab_title_friendsRequest));

    }

    //MENU
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_friends_add,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showKeyBoard();

        final EditText edittext = new EditText(getActivity());
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Introduce el nombre del usuario");

        alert.setView(edittext);

        alert.setPositiveButton("Enviar petición", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                presenter.sendRequest(edittext.getText().toString());
                hideKeyBoard();
            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                hideKeyBoard();
            }
        });

        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                hideKeyBoard();
            }
        });

        alert.show();
        return super.onOptionsItemSelected(item);
    }


    //CONTRACT
    @Override
    public void showMessage(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

