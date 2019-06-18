package com.example.ismael.pikaos.UI.friends;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.adapters.online.RvFriendRequestAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.data.model.online.FriendRequest;

import java.util.List;

public class FriendRequestsFragment extends BaseFragment implements FriendRequestContract.view{
    public static final String TAG = "FriendsRequestFragmentTAG";

    @Override
    public void setTitleFragment() {
        getActivity().setTitle("Amigos");
    }

    public static FriendRequestsFragment newInstance() {
        return new FriendRequestsFragment();
    }

    private RecyclerView rvFriendRequest;
    private RvFriendRequestAdapter adapter;

    private TextView tvNoFriendRequests;
    private FriendRequestPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friendsrequest,container,false);
        rvFriendRequest = rootView.findViewById(R.id.rvFriendRequests);
        tvNoFriendRequests = rootView.findViewById(R.id.tvNoFriendRequest);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new FriendRequestPresenter(this);
        presenter.getFriendRequests();
    }


    //REFRESCAR EL FRAGMENT CUANDO SE HACE SWIPE ENTRE LOS TABS
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && presenter!=null){
            presenter.getFriendRequests();
        }
    }


    //CONTRACT
    @Override
    public void setErrorMessage(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setFriendRequests(final List<FriendRequest> requests) {
        tvNoFriendRequests.setVisibility(View.GONE);

        adapter = new RvFriendRequestAdapter(getActivity(), requests);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FriendRequest request = adapter.getitem(rvFriendRequest.getChildAdapterPosition(v));

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle(request.getName());

                alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.acceptRequest(request.getId());
                    }
                });

                alert.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.declineRequest(request.getId());
                    }
                });

                alert.show();
            }
        });

        rvFriendRequest.setAdapter(adapter);
        rvFriendRequest.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onSuccess(int id) {
        adapter.updateAdapter(id);

        if(adapter.getItemCount()==0){
            tvNoFriendRequests.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setAnyRequest() {
        tvNoFriendRequests.setVisibility(View.VISIBLE);

        //Esto sirve para controlar que el usuario se quede sin peticiones cuando ya tenía
        rvFriendRequest.setAdapter(null);
    }
}
