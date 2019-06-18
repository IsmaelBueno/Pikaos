package com.example.ismael.pikaos.UI.teams;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.adapters.online.RvTeamMembersAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.UI.chatTeam.ChatTeamActivity;
import com.example.ismael.pikaos.UI.newTeam.NewTeamView;
import com.example.ismael.pikaos.data.model.online.Team;
import com.squareup.picasso.Picasso;


public class TeamManageFragment extends BaseFragment implements TeamManageContract.View{

    public static final String TAG = "TeamsFragmentTAG";

    public static TeamManageFragment newInstance() {
        return new TeamManageFragment();
    }

    @Override
    public void setTitleFragment() {
        getActivity().setTitle(R.string.title_teams);
    }


    private View.OnClickListener onclickNewTeam = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityForResult(new Intent(getActivity(),NewTeamView.class), 10001);
        }
    };

    private View.OnClickListener onClickLeave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            //alert.setTitle(request.getName());

            if(PikaosApplication.userName.equals(team.getCaptain()))
                alert.setMessage("Eres el capitán de este equipo, si lo dejas, este equipo será disuelto\n¿Estás seguro?");
            else
                alert.setMessage("¿Seguro que quieres dejar este equipo?");


            alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    presenter.leaveTeam();
                }
            });

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });

            alert.show();
        }
    };


    private View.OnClickListener onClickInviteTeam = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
        }
    };

    private View.OnClickListener onClickOpenChat = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ChatTeamActivity.TAG,team);

            Intent i = new Intent(getActivity(),ChatTeamActivity.class);
            i.putExtras(bundle);

            startActivity(i);
        }
    };


    Team team;

    private TeamManagePresenter presenter;

    private RvTeamMembersAdapter adapter;

    private ConstraintLayout layoutNoTeam;
    private ConstraintLayout layoutTeam;
    private ImageView logoTeam;
    private RecyclerView membersTeam;
    private TextView nameTeam;
    private TextView maxPlayers;

    private Button btNewTeam;
    private Button btInvite;
    private Button btLeave;
    private FloatingActionButton btOpenChat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team_manage, container,false);
        layoutNoTeam = rootView.findViewById(R.id.layoutNoTeam);
        layoutTeam = rootView.findViewById(R.id.layoutTeam);
        logoTeam = rootView.findViewById(R.id.imgTeam);
        membersTeam = rootView.findViewById(R.id.rvMembersTeam);
        nameTeam = rootView.findViewById(R.id.tvTitleTeam);

        btNewTeam = rootView.findViewById(R.id.btNewTeam);
        btInvite = rootView.findViewById(R.id.btInvite);
        btLeave = rootView.findViewById(R.id.btLeave);

        btOpenChat = rootView.findViewById(R.id.btOpenChatTeam);

        maxPlayers = rootView.findViewById(R.id.tvNumberPlayers);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new TeamManagePresenter(this);
        presenter.loadTeam();

        btNewTeam.setOnClickListener(onclickNewTeam);
        btLeave.setOnClickListener(onClickLeave);
        btInvite.setOnClickListener(onClickInviteTeam);
        btOpenChat.setOnClickListener(onClickOpenChat);

    }

    //CONTRACT
    @Override
    public void setTeam(Team team) {
        layoutNoTeam.setVisibility(View.GONE);

        this.team = team;

        maxPlayers.setText(team.getPlayers().length+"/20");

        nameTeam.setText(team.getName());
        String logoUrl = "http://157.230.114.223/logos/"+team.getLogo();
        Picasso.get().load(logoUrl).into(logoTeam);

        adapter = new RvTeamMembersAdapter(getActivity(),team.getPlayers(),team.getCaptain());
        membersTeam.setAdapter(adapter);
        membersTeam.setLayoutManager(new LinearLayoutManager(getActivity()));

        layoutTeam.setVisibility(View.VISIBLE);
        btOpenChat.setVisibility(View.VISIBLE);

    }

    @Override
    public void setAnyTeams() {
        layoutNoTeam.setVisibility(View.VISIBLE);
        layoutTeam.setVisibility(View.GONE);
        btOpenChat.setVisibility(View.GONE);
    }

    @Override
    public void setMessage(int message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessLeaved() {
        //Refresca el fragment
        setUserVisibleHint(true);
    }

    @Override
    public void onSuccessInvited() {
        Toast.makeText(getActivity(), "Invitación enviada con éxito", Toast.LENGTH_SHORT).show();
    }

    //REFRESCAR EL FRAGMENT CUANDO SE HACE SWIPE ENTRE LOS TABS
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && presenter!=null){
            presenter.loadTeam();
        }
    }

    //Refresca el framgnet cuando se cierra la activity de crear equipo, por si se hubiera creado
    //que cargue de forma instantanea
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == NewTeamView.RESULT_OK))
            setUserVisibleHint(true);
    }
}
