package com.example.ismael.pikaos.UI.competetions;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.adapters.online.CompetitionsAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.UI.newCompetition.NewCompetitionView;
import com.example.ismael.pikaos.data.model.online.Competition;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CompetitionsFragment extends BaseFragment implements CompetitionsContract.View{

    public static final String TAG = "CompetitionsFragmentTAG";

    private RecyclerView rvCompetitions;

    private CompetitionsPresenter presenter;
    private CompetitionsAdapter adapter;

    @Override
    public void setTitleFragment() {
        getActivity().setTitle(R.string.title_competitions);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_competitions, container,false);
        rvCompetitions = rootView.findViewById(R.id.rvCompetitions);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitleFragment();

        setHasOptionsMenu(true);

        presenter = new CompetitionsPresenter(this);
        presenter.onLoadCompetitions(getActivity());

    }


    @Override
    public void setLoadedCompetitions(final List<Competition> competitions) {
        adapter = new CompetitionsAdapter(getActivity(),competitions);

        if(rvCompetitions.getAdapter()==null){
            rvCompetitions.setAdapter(adapter);
            rvCompetitions.setLayoutManager(new LinearLayoutManager(getActivity()));

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Competition competitionTmp = adapter.getItem(rvCompetitions.getChildAdapterPosition(v));

                    final EditText input = new EditText(getActivity());

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(getResources().getString(R.string.item_list_alertdialog_title) + " "+ competitionTmp.getName()+"?");

                    if(competitionTmp.isPrivate()==1){

                        input.setHint("Contraseña");
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        alertDialog.setView(input);
                    }

                    alertDialog.setPositiveButton(getResources().getString(R.string.item_list_alertdialog_ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            presenter.joinGame(competitionTmp.getId(),input.getText().toString());
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.item_list_alertdialog_cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).create().show();
                }
            });

        }
        else {
            rvCompetitions.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void setGenericError(int message) {
        Toast.makeText(getContext(), getResources().getString(message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setJoinedCompetition(int idCompetition) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),getResources().getString(R.string.succefully_joined),Snackbar.LENGTH_LONG).show();
        //Refrescar el adapter al unirte a una competición
        adapter.deleteItem(idCompetition);
    }


    //MENU
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_competition_online, menu);

        MenuItem searchItem = menu.findItem(R.id.action_competition_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_add_competiton)
            startActivity(new Intent(getActivity(), NewCompetitionView.class));
        return super.onOptionsItemSelected(item);
    }


}
