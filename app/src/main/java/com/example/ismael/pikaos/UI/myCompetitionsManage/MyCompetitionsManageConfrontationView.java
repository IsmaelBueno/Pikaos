package com.example.ismael.pikaos.UI.myCompetitionsManage;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.adapters.online.CompetitionsConfrontationsOnlineAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;
import com.example.ismael.pikaos.data.model.online.Confrontation;
import com.example.ismael.pikaos.data.model.online.Friend;
import com.example.ismael.pikaos.data.model.online.MyCompetition;
import com.example.ismael.pikaos.data.model.online.Player;
import com.example.ismael.pikaos.data.model.online.Round;

import java.util.ArrayList;
import java.util.List;

public class MyCompetitionsManageConfrontationView extends BaseFragment implements MyCompetitionManageConfrontationContract.view{

    public static final String TAG = "MyCompetitionsManageConfrontationView";

    @Override
    public void setTitleFragment() {
        getActivity().setTitle(myCompetition.getName());
    }

    private MyCompetition myCompetition;
    private MyCompetitionManageConfrontationPresenter presenter;

    private TextView tvMoreOptions;
    private ListView lvPlayers;
    private TextView tvActualConfrontationName;
    private Spinner spAllConfrontations;
    private RecyclerView rvConfrontations;

    private CompetitionsConfrontationsOnlineAdapter adapter;


    public static MyCompetitionsManageConfrontationView newInstance(MyCompetition competition) {

        MyCompetitionsManageConfrontationView fragment = new MyCompetitionsManageConfrontationView();

        Bundle args = new Bundle();
        args.putSerializable(TAG,competition);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mycompetitions_manage_confrontations, container,false);

        tvActualConfrontationName = rootView.findViewById(R.id.tvActualConfrontationName);
        spAllConfrontations = rootView.findViewById(R.id.spLastConfrontations);
        rvConfrontations = rootView.findViewById(R.id.rvConfrontations);
        lvPlayers = rootView.findViewById(R.id.lvPlayersConfrontation);
        tvMoreOptions = rootView.findViewById(R.id.tvMoreOptions);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myCompetition = (MyCompetition) getArguments().getSerializable(TAG);

        presenter = new MyCompetitionManageConfrontationPresenter(this);

        //Si la competición está prepandose no buscaremos sus rondas ni partidas, porque no existen
        //Sólo buscaremos los jugadores apuntados hasta el momento
        if(!myCompetition.getState().equals("preparing")) {
            lvPlayers.setVisibility(View.GONE);
            spAllConfrontations.setVisibility(View.VISIBLE);
            rvConfrontations.setVisibility(View.VISIBLE);
            tvMoreOptions.setVisibility(View.VISIBLE);
            presenter.getConfrontations(myCompetition.getId());
        }
        else{
            tvMoreOptions.setVisibility(View.GONE);
            lvPlayers.setVisibility(View.VISIBLE);
            spAllConfrontations.setVisibility(View.GONE);
            rvConfrontations.setVisibility(View.GONE);
            presenter.getPlyers(myCompetition.getId());
        }
    }




    //CONTRACT
    /**
     * ALERTA CÓDIGO ESPAGUETI!!!
     * @param rounds
     */
    @Override
    public void onSuccessGetConfrontations(final List<Round> rounds) {

        String nameRound;

        //El nombre de la ronda
        if (myCompetition.getType().contains("cup"))
            nameRound = "Ronda";
        else
            nameRound = "Jornada";

        //Spinner
        ArrayList<String> nameRounds = new ArrayList<>();
        for (int i = 0; i < rounds.size(); i++) {
            nameRounds.add(nameRound+": " + rounds.get(i).getnRound());
        }

        final ArrayAdapter<String> spAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, nameRounds);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAllConfrontations.setAdapter(spAdapter);

        //Dependiendo del elemento del spinner variará el contenido del recycler view por eso,
        //cada vez que actualizamos este listener (onItemSelected) debemos cambiar el adapter del recycler
        spAllConfrontations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Confrontation[] selectedConfrontations = rounds.get(position).getConfrontations();
                adapter = new CompetitionsConfrontationsOnlineAdapter(getActivity(),selectedConfrontations);

                //Título de la ronda
                if (myCompetition.getType().contains("cup"))
                    tvActualConfrontationName.setText("Ronda " + (position+1));
                else
                    tvActualConfrontationName.setText("Jornada " + (position+1));

                //Solo añadiremos el listener para editar el ganador si es la última ronda, es decir,
                // la ronda actual, si se es administrador y si la competición está en estado compitiendo
                boolean competing = myCompetition.getState().equals("competing");
                boolean admin = myCompetition.getAdmin().equals(PikaosApplication.userName);
                boolean lastRound = spAllConfrontations.getSelectedItemPosition()==spAdapter.getCount()-1;

                if(competing && admin && lastRound) {
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Confrontation tmpConfrontation = adapter.getitem(rvConfrontations.getChildAdapterPosition(v));

                            //Filtramos que no sea un emparejamiento sin contrincante, en este caso no
                            //se abrirá nada
                            boolean emptyMatchUp = tmpConfrontation.getPlayer_two().equals("0") || tmpConfrontation.getPlayer_one().equals("0");

                            if(!emptyMatchUp) {

                                final Dialog dialog = new Dialog(getContext());
                                dialog.setContentView(R.layout.radiobutton_dialog);
                                dialog.setCancelable(true);


                                final RadioButton rd1 = dialog.findViewById(R.id.rd_1);
                                rd1.setText(tmpConfrontation.getPlayer_one());
                                final RadioButton rd2 = dialog.findViewById(R.id.rd_2);
                                rd2.setText(tmpConfrontation.getPlayer_two());
                                final RadioButton rd3 = dialog.findViewById(R.id.rd_3);
                                rd3.setText("Empate");

                                if(myCompetition.getType().contains("league")){
                                    rd3.setVisibility(View.VISIBLE);
                                }else
                                    rd3.setVisibility(View.GONE);

                                Button ok = dialog.findViewById(R.id.btPutWinner);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (rd1.isChecked())
                                            presenter.setWinner(myCompetition.getId(), tmpConfrontation.getPlayer_one(),
                                                    spAdapter.getCount(), myCompetition.getType(), tmpConfrontation.getPlayer_one(), tmpConfrontation.getPlayer_two());
                                        if (rd2.isChecked())
                                            presenter.setWinner(myCompetition.getId(), tmpConfrontation.getPlayer_two(),
                                                    spAdapter.getCount(), myCompetition.getType(),tmpConfrontation.getPlayer_one(),tmpConfrontation.getPlayer_two());

                                        //El 0 indica empate en lado del servidor
                                        if(rd3.isChecked())
                                            presenter.setWinner(myCompetition.getId(),"0",spAdapter.getCount(),myCompetition.getType(),
                                                    tmpConfrontation.getPlayer_one(),tmpConfrontation.getPlayer_two());

                                        dialog.dismiss();
                                    }
                                });

                                Button cancel = dialog.findViewById(R.id.btCancelWinner);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        }
                    });
                }

                rvConfrontations.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvConfrontations.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spAllConfrontations.setSelection(spAdapter.getCount()-1);
    }

    @Override
    public void setMessageError(int messageError) {
        Toast.makeText(getActivity(), messageError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessGetPlayers(List<Player> players) {

        ArrayList<String> playersString = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            playersString.add(players.get(i).getName());
        }

        ArrayAdapter<String> lvAdapter;
        lvAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,playersString);
        lvPlayers.setAdapter(lvAdapter);
    }

    @Override
    public void onSuccessSetWinner(String winner,String playerone,String playertwo) {
        adapter.updateAdapter(winner,playerone,playertwo);
    }

}
