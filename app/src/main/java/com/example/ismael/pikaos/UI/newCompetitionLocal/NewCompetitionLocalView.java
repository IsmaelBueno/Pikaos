package com.example.ismael.pikaos.UI.newCompetitionLocal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewCompetitionLocalView extends BaseFragment implements NewCompetitionLocalContract.View {

    public static final String TAG = "NewCompetitionLocalTAG";

    private EditText etCompetitionName;
    private Spinner spTypeCompetition;
    private EditText etDescriptionCompetition;
    private ImageButton btAddPlayer;
    private ListView lvPlayers;
    private TextView tvNplayers;



    ArrayAdapter<String> adapterTypesCompetition;
    NewCompetitionLocalPresenter presenter;

    ArrayAdapter<String> adapter;

    loadCompetitionFragment callback;

    public interface loadCompetitionFragment{
        void setCompetitionFragment();
    }

    @Override
    public void setTitleFragment() {
        getActivity().setTitle(R.string.title_new_competition);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback=(loadCompetitionFragment) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementar NewCompetitionInterface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newcompetition_local,container,false);
        etCompetitionName = rootView.findViewById(R.id.etCompetitionNameLocal);
        spTypeCompetition = rootView.findViewById(R.id.spTypeCompetitionLocal);
        etDescriptionCompetition = rootView.findViewById(R.id.etCompetitionDescriptionLocal);
        btAddPlayer = rootView.findViewById(R.id.btAddPlayerLocal);
        lvPlayers = rootView.findViewById(R.id.lvPlayersLocal);
        tvNplayers = rootView.findViewById(R.id.tvNplayers);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitleFragment();
        setNavigationHomeMenu();
        setHasOptionsMenu(true);

        loadTypesCompetitionUI();

        presenter = new NewCompetitionLocalPresenter(this);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        lvPlayers.setAdapter(adapter);
        lvPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogDelete(lvPlayers.getItemAtPosition(position).toString());
            }
        });

        btAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final EditText input = new EditText(getActivity());

                    //Mostrar el teclado
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Introduce el nombre del nuevo participante");
                    input.setHint("nombre");
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);

                    alertDialog.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            presenter.addNewPlayer(input.getText().toString(),adapter);
                            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        }
                    })
                            .setNegativeButton(getResources().getString(R.string.item_list_alertdialog_cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                }
                            }).create().show();
                }
        });
    }


    private void showDialogDelete(final String player){
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        adapter.remove(player);
                        adapter.notifyDataSetChanged();
                        tvNplayers.setText(adapter.getCount()+"/32");
                        dialog.dismiss();

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Quieres eliminar a " + player)
                .setIcon(R.drawable.ic_delete_alertdialog)
                .setNegativeButton("Cancelar",dialogListener)
                .setPositiveButton("Sí",dialogListener)
                .show();
    }

    private void loadTypesCompetitionUI(){
        ArrayList<String> resources = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.values_typesCompetitionLocal)));
        adapterTypesCompetition = new ArrayAdapter<>(getActivity(),R.layout.spinner_item,resources);
        spTypeCompetition.setAdapter(adapterTypesCompetition);
    }







    //MENU
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_local,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(spTypeCompetition.getSelectedItem().equals("Copa")) {
            if (item.getItemId() == R.id.action_add_competiton) {
                ArrayList<String> players = new ArrayList<>();

                for (int i = 0; i < lvPlayers.getAdapter().getCount(); i++)
                    players.add(lvPlayers.getAdapter().getItem(i).toString());

                presenter.createCompetition(etCompetitionName.getText().toString(),
                        etDescriptionCompetition.getText().toString(), spTypeCompetition.getSelectedItem().toString(), players);
                return true;
            }
        }else{
            Toast.makeText(getActivity(), "La liga en modo local aún no ha sido implementada, lo sentimos", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationHomeMenu(){
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_cancel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }


    //CONTRATO
    @Override
    public void onSuccess() {
        notification();
        callback.setCompetitionFragment();
    }

    @Override
    public void errorName() {
        etCompetitionName.setError("El nombre no puede ser vacío y debe contener al menos 50 caracteres");
    }

    @Override
    public void errorDescription() {
        etDescriptionCompetition.setError("La descripción no puede tener más de 250 caracteres");
    }

    @Override
    public void errorPlayers() {
        Toast.makeText(getContext(), "Debes al menos introducir 3 jugadores en una competición", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorSqlite() {
        Toast.makeText(getContext(), "Error con la base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSucessNewPlayer() {
        adapter.notifyDataSetChanged();
        tvNplayers.setText(adapter.getCount()+"/32");
    }

    @Override
    public void onErrorPlayer() {
        Toast.makeText(getContext(), "El nombre de un participante debe contener entre 3 y 20 caracteres", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorMaxPlayers() {
        Toast.makeText(getContext(), "No puede haber más de 32 participantes por competición", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorPlayerExists() {
        Toast.makeText(getContext(), "Ya existe un participante con ese nombre", Toast.LENGTH_SHORT).show();
    }

}
