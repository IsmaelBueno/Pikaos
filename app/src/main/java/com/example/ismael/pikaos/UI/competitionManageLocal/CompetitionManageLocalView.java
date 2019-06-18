package com.example.ismael.pikaos.UI.competitionManageLocal;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.adapters.local.CompetitionsConfrontationsLocalAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;

import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;
import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;
import com.example.ismael.pikaos.data.model.local.CupRoundLocal;

import java.util.ArrayList;

public class CompetitionManageLocalView extends BaseFragment implements CompetitionManageLocalContract.View{

    public static final String TAG = "CompetitionManageLocalViewTAG";

    private TextView tvActualConfrontation;
    private Spinner spLastConfrontations;
    private RecyclerView rvConfrontations;

    CompetitionManageLocalPresenter presenter;
    CompetitionLocalView competition;
    int lastRound;

    CompetitionsConfrontationsLocalAdapter adapter;

    //new instance
    public static CompetitionManageLocalView newInstance(Bundle bundle) {
        CompetitionManageLocalView fragment = new CompetitionManageLocalView();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setTitleFragment() {
        getActivity().setTitle(competition.getName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mycompetitions_manage_confrontations,container,false);
        tvActualConfrontation = rootView.findViewById(R.id.tvActualConfrontationName);
        spLastConfrontations = rootView.findViewById(R.id.spLastConfrontations);
        rvConfrontations = rootView.findViewById(R.id.rvConfrontations);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        competition = (CompetitionLocalView)getArguments().getSerializable(TAG);

        setTitleFragment();
        setNavigationHomeMenu();
        setHasOptionsMenu(true);

        presenter = new CompetitionManageLocalPresenter(this);
        presenter.getAllRounds(competition.getId());
    }


    //DIALOGS
    /**
     * Se abrirá cuando se vaya a establecer un ganador en un enfrentamiento
     * @param confrontation
     */
    private void showPutWinnerDialog(final CupConfrontationsLocal confrontation) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.radiobutton_dialog);
        dialog.setCancelable(true);


        final RadioButton rd1 = dialog.findViewById(R.id.rd_1);
        rd1.setText(confrontation.getPlayerOne());
        final RadioButton rd2 = dialog.findViewById(R.id.rd_2);
        rd2.setText(confrontation.getPlayerTwo());

        Button ok = dialog.findViewById(R.id.btPutWinner);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rd1.isChecked())
                    presenter.setWinner(confrontation.getIdCup(), confrontation.getnRound(), confrontation.getPlayerOne(), confrontation.getPlayerTwo(), rd1.getText().toString());
                if(rd2.isChecked())
                    presenter.setWinner(confrontation.getIdCup(), confrontation.getnRound(), confrontation.getPlayerOne(), confrontation.getPlayerTwo(), rd2.getText().toString());

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

    /**
     * Muestra la descripción de una competición en un cuadro de dialogo
     */
    private void showDescriptionDialog(){
        String description = competition.getNote();
        if(description.isEmpty())
            description = getString(R.string.dialog_competition_description_competition_no_description);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(description)
                .setTitle(R.string.dialog_competition_description_title)
                .setCancelable(true)
                .setPositiveButton(R.string.dialog_competition_description_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showFinishRoundDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_finish_round_title)
                .setMessage(R.string.dialog_finsih_round_message)
                .setCancelable(true)
                .setPositiveButton(R.string.dialog_competition_description_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.setNewRound(competition.getId(),lastRound);
                    }
                })
                .setNegativeButton(R.string.dialog_finish_round_message_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    //MENU
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_competition_local,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_show_description:
                    showDescriptionDialog();
                break;
            case R.id.action_finish_round:
                if(!competition.isFinished())
                    showFinishRoundDialog();
                else
                    Toast.makeText(getContext(), R.string.finished_competition_message, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationHomeMenu(){
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }



    //CONTRAT

    @Override
    public void setAllRounds(final ArrayList<CupRoundLocal> rounds) {

        //Guardamos cual es la ultima ronda de la competición y ademaás la que sale por defecto
        lastRound = rounds.size();

        //Visualmente solo mostraremos en el spinner el nombre de la ronda
        final ArrayList<String> namesRounds = new ArrayList<>();
        for (int i = 0; i < rounds.size(); i++) {
            namesRounds.add(rounds.get(i).getRoundName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.spinner_item,namesRounds);
        spLastConfrontations.setAdapter(adapter);
        spLastConfrontations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvActualConfrontation.setText(namesRounds.get(position));
                presenter.getAllConfrontations(competition.getId(),rounds.get(position).getnRound());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Selecciona automáticamente el último enfrentamiento
        spLastConfrontations.setSelection(lastRound-1);

    }

    @Override
    public void setActualsConfrontations(final ArrayList<CupConfrontationsLocal> confrontations) {
        if(adapter == null){
            adapter = new CompetitionsConfrontationsLocalAdapter(getContext(),confrontations);
            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Si el segundo jugador está vacio, es decir, es un emparejamiento que sobró no dejará editar el ganador
                    if(!adapter.getitem(rvConfrontations.getChildAdapterPosition(v)).getPlayerTwo().isEmpty() &&
                            adapter.getitem(rvConfrontations.getChildAdapterPosition(v)).getnRound()==lastRound && !competition.isFinished())
                        showPutWinnerDialog(adapter.getitem(rvConfrontations.getChildAdapterPosition(v)));

                }
            });
            rvConfrontations.setAdapter(adapter);
            rvConfrontations.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            adapter.updateAdapter(confrontations);
        }

    }

    @Override
    public void setFinishedCompetition(String winner) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.competition_winner_message) + winner, Snackbar.LENGTH_LONG).show();
        //Esto solo es para la vez que esta aquí no tener que hacer otra consulta, las proximas veces que el usuario
        //entre a la competición ya estará actualizada de la bbdd
        competition.setFinished(true);
    }

    @Override
    public void setErrorAllRounds() {
        Toast.makeText(getContext(), R.string.error_database, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setErrorActualsConfrontations() {
        Toast.makeText(getContext(), R.string.error_database, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setErrorSetWinner() {
        Toast.makeText(getContext(), R.string.error_database, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setErrorWinnersEmpty() {
        Toast.makeText(getContext(), R.string.all_winners_uncompleted_next_round, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setErrorCreatingNewRound() {
        Toast.makeText(getContext(), R.string.error_database, Toast.LENGTH_SHORT).show();
    }
}