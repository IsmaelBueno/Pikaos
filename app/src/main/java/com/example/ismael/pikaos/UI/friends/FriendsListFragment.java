package com.example.ismael.pikaos.UI.friends;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.adapters.online.RvFriendListAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.UI.chat.ChatActivity;
import com.example.ismael.pikaos.data.model.online.Friend;
import com.example.ismael.pikaos.data.model.online.Team;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FriendsListFragment extends BaseFragment implements FriendsListContract.View {
    public static final String TAG = "FriendsListFragmentTAG";

    @Override
    public void setTitleFragment() {
        getActivity().setTitle("Amigos");
    }

    public static FriendsListFragment newInstance() {
        return new FriendsListFragment();
    }


    private RecyclerView rvFriends;
    private RvFriendListAdapter adapter;

    private TextView tvNoFriends;
    private FriendsListPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friendslist,container,false);
        rvFriends = rootView.findViewById(R.id.rvFriends);
        tvNoFriends = rootView.findViewById(R.id.tvNoFriends);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new FriendsListPresenter(this);
        presenter.loadFriends();
    }

    //REFRESCAR EL FRAGMENT CUANDO SE HACE SWIPE ENTRE LOS TABS
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && presenter!=null){
            presenter.loadFriends();
        }
    }


    //CONTRACT
    @Override
    public void setFriends(List<Friend> friends) {
        tvNoFriends.setVisibility(View.GONE);

        adapter = new RvFriendListAdapter(getActivity(), friends);

        adapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Friend friend = adapter.getitem(rvFriends.getChildAdapterPosition(v));

                Bundle bundle = new Bundle();
                bundle.putSerializable(ChatActivity.TAG,friend);

                Intent i = new Intent(getActivity(),ChatActivity.class);
                i.putExtras(bundle);

                startActivity(i);
            }
        });

        adapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Friend friend = adapter.getitem(rvFriends.getChildAdapterPosition(v));

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle(friend.getName());

                alert.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.deleteFriend(friend.getId());
                    }
                });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();

                return false;
            }
        });

        rvFriends.setAdapter(adapter);
        rvFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void setErrorMessage(int message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAnyFriends() {
        tvNoFriends.setVisibility(View.VISIBLE);

        //Esto sirve para controlar que el usuario se quede sin amigos cuando ya tenía, limpiamos el adapter y yasta
        rvFriends.setAdapter(null);
    }

    @Override
    public void onSucessDeleted(int id) {
        adapter.updateAdapter(id);

        if(adapter.getItemCount()==0){
            tvNoFriends.setVisibility(View.VISIBLE);
        }
    }
}
