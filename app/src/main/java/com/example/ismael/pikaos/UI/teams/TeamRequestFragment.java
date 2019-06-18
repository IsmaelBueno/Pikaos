package com.example.ismael.pikaos.UI.teams;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.ismael.pikaos.UI.adapters.online.RvTeamRequestAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.data.model.online.TeamRequest;

import java.util.List;

public class TeamRequestFragment extends BaseFragment implements TeamRequestContract.view {
    @Override
    public void setTitleFragment() { getActivity().setTitle("Equipo"); }

    public static TeamRequestFragment newInstance() {
        return new TeamRequestFragment();
    }

    private RecyclerView rvTeamRequest;
    private RvTeamRequestAdapter adapter;

    private TextView tvNoRequestsTeam;
    private TeamRequestsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teamrequests, container, false);

        rvTeamRequest = rootView.findViewById(R.id.rvTeamRequests);
        tvNoRequestsTeam = rootView.findViewById(R.id.tvNoTeamRequest);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new TeamRequestsPresenter(this);
        presenter.getTeamRequests();
    }

    //REFRESCAR EL FRAGMENT CUANDO SE HACE SWIPE ENTRE LOS TABS
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && presenter!=null){
            presenter.getTeamRequests();
        }
    }

    //CONTRACT
    @Override
    public void onErrorMessage(int Message) {
        Toast.makeText(getContext(), Message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTeamRequests(List<TeamRequest> requests) {

        tvNoRequestsTeam.setVisibility(View.GONE);

        adapter = new RvTeamRequestAdapter(getActivity(), requests);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TeamRequest request = adapter.getitem(rvTeamRequest.getChildAdapterPosition(v));

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle(request.getName());

                alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.acceptTeamRequest(request.getId());
                    }
                });

                alert.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.declineTeamRequest(request.getId());
                    }
                });

                alert.show();
            }
        });

        rvTeamRequest.setAdapter(adapter);
        rvTeamRequest.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onSuccessResponseRequest(int id) {
        adapter.deleteRequest(id);

        if(adapter.getItemCount()==0){
            tvNoRequestsTeam.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setAnyRequest() {
        tvNoRequestsTeam.setVisibility(View.VISIBLE);

        //Esto sirve para controlar que el usuario se quede sin peticiones cuando ya tenía
        rvTeamRequest.setAdapter(null);

    }
}
