package com.example.ismael.pikaos.UI.competitionsLocal;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.adapters.local.CompetitionsListLocalAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;

import java.util.ArrayList;

public class CompetitionLocalListView extends BaseFragment implements CompetitionLocalContract.View {

    public static final String TAG = "CompetitionLocalListTAG";

    RecyclerView rvListCompetitions;
    FloatingActionButton btAddCompetition;

    CompetitionLocalPresenter presenter;
    CompetitionsListLocalAdapter adapter;
    ICompetitionLocalListView callback;

    public interface ICompetitionLocalListView{
        void setNewCompetitionFragment();
        void setCompetitionManage(CompetitionLocalView competition);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback = (ICompetitionLocalListView)context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " debe implmeentar el interfaz loadNewCompetitionFragment");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_competition_local_list, container,false);
        rvListCompetitions = rootView.findViewById(R.id.rvCompetitionsLocal);
        btAddCompetition = rootView.findViewById(R.id.btAddCompetitIonLocal);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitleFragment();
        setHasOptionsMenu(true);
        //Desactivar el NavigationUp
        ((Toolbar)getActivity().findViewById(R.id.toolbar)).setNavigationIcon(null);

        btAddCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.setNewCompetitionFragment();
            }
        });

        presenter = new CompetitionLocalPresenter(this);
        presenter.loadCompetitions();

    }


    public static CompetitionLocalListView newInstance() {
        CompetitionLocalListView fragment = new CompetitionLocalListView();
        return fragment;
    }

    @Override
    public void setCompetitionsLocal(ArrayList<CompetitionLocalView> competitions) {
        adapter = new CompetitionsListLocalAdapter(getActivity(), competitions);
        rvListCompetitions.setAdapter(adapter);
        rvListCompetitions.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialogDelete(adapter.getItem(rvListCompetitions.getChildAdapterPosition(v)));
                return true;
            }
        });
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.setCompetitionManage(adapter.getItem(rvListCompetitions.getChildAdapterPosition(v)));
            }
        });
    }


    private void showDialogDelete(final CompetitionLocalView competition){
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        presenter.deleteCompetition(competition.getId());
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//TODO string value
        builder.setMessage("¿Seguro que quieres eliminar definitivamente " + competition.getName())
                .setIcon(R.drawable.ic_delete_alertdialog)
                .setNegativeButton("Cancelar",dialogListener)
                .setPositiveButton("Sí",dialogListener)
                .show();
    }


    @Override
    public void deleteCompetition() {
        presenter.loadCompetitions();//TODO esto habrá que hacerlo mejor
    }

    @Override
    public void setError(int messageError) {
        Toast.makeText(getActivity(), "Fallo de testeo: "+messageError, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setTitleFragment() {
        getActivity().setTitle("Competiciones");
    }

    //Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_local,menu);

        MenuItem searchItem = menu.findItem(R.id.action_menulocal_search);

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
        switch (item.getItemId()){
            case R.id.action_order_name:
                adapter.orderByName();
                break;
            case R.id.action_order_date:
                adapter.orderByDate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
