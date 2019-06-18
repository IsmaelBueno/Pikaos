package com.example.ismael.pikaos.UI.newCompetition;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.data.model.online.Videogame;

import java.util.ArrayList;
import java.util.List;

public class NewCompetitionView extends AppCompatActivity implements NewCompetitionContract.View{

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private NewCompetitionPresenter presenter;

    private Toolbar toolbar;
    private ProgressDialog dialog;

    private EditText etName;
    private Spinner spTypeCompetition;
    private Spinner spVideogame;
    private RadioButton rbPublic;
    private EditText etPassword;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_competition);

        //UI
        etName = findViewById(R.id.etNameNewComp);
        spTypeCompetition = findViewById(R.id.spTypeCompetitionNewComp);
        spVideogame = findViewById(R.id.spVideogameNewComp);
        rbPublic = findViewById(R.id.rbPublic);
        etPassword = findViewById(R.id.etPasswordNewComp);
        etDescription = findViewById(R.id.etDescriptionNewComp);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crear competición");
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Presenter
        presenter = new NewCompetitionPresenter(this);

        //Spinners
        loadSpinnerTypeCompetition();
    }


    //INTERFAZ
    private void loadSpinnerTypeCompetition() {
        ArrayList<String> typeCompetitions = new ArrayList<>();
        typeCompetitions.add("copa individual");
        typeCompetitions.add("liga individual");
        typeCompetitions.add("copa en equipo");
        typeCompetitions.add("liga en equipo");

        ArrayAdapter<String> adapterTypeCompetitions= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, typeCompetitions);
        adapterTypeCompetitions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTypeCompetition.setAdapter(adapterTypeCompetitions);

        spTypeCompetition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getAdapter().getItem(position).equals("copa individual") || parent.getAdapter().getItem(position).equals("liga individual"))
                    presenter.getAllVideogamesIndividual();
                else
                   presenter.getAllVideogamesTeam();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTypeCompetition.setSelection(0);
    }


    public void showProgressBar(){
        dialog = ProgressDialog.show(this, "Obteniendo la lista de videojuegos",
                "Espera por favor...", true,false);
    }

    public void hideProgressBar(){
        if(dialog!=null)
            dialog.dismiss();
    }


    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_team, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        presenter.createCompetition(etName.getText().toString(),spTypeCompetition.getSelectedItem().toString()
                ,spVideogame.getSelectedItem().toString(),etPassword.getText().toString(),
                etDescription.getText().toString(),rbPublic.isChecked());

        return super.onOptionsItemSelected(item);
    }


    //CONTRACT
    @Override
    public void setErrorName(int message) {
        etName.setError(getResources().getString(message));
    }

    @Override
    public void setErrorPasswordCompetition(int message) {
        etPassword.setError(getResources().getString(message));
    }

    @Override
    public void setErrorDescriptionCompetition(int message) {
        etDescription.setError(getResources().getString(message));
    }

    @Override
    public void onSuccessVideogames(List<Videogame> videogames) {

        ArrayList<String> videogamesList = new ArrayList<>();

        for (int i = 0; i < videogames.size(); i++) {
            videogamesList.add(videogames.get(i).getTitle());
        }

        ArrayAdapter<String> adapterVideogames= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, videogamesList);
        adapterVideogames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVideogame.setAdapter(adapterVideogames);
    }

    @Override
    public void setMessageError(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSucess() {
        this.finish();
    }
}
