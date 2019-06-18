package com.example.ismael.pikaos.UI.videogame;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.Utils;

public class ProposeVideogameView extends AppCompatActivity implements ProposeVideogameContract.view {

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private ProposeVideogamePresenter presenter;

    private Toolbar toolbar;
    private ProgressDialog progressDialog;

    private EditText etName;
    private CheckBox cbxIndividual;
    private CheckBox cbxTeam;
    private CheckBox cbxCustomGame;
    private EditText etInformation;
    private Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose_videogame_view);

        //UI
        etName = findViewById(R.id.etNameProVideogame);
        cbxIndividual = findViewById(R.id.cbxIndividualProVideogame);
        cbxTeam = findViewById(R.id.cbxTeamProVideogame);
        cbxCustomGame = findViewById(R.id.cbxCustomsGamesProVideogame);
        etInformation = findViewById(R.id.etDescriptionProVideogame);
        btSend = findViewById(R.id.btSendProVideogame);


        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Propuesta de nuevo videojuego");

        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Presenter
        presenter = new ProposeVideogamePresenter(this);

        //Botón envíar
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(ProposeVideogameView.this);
                presenter.sendVideogame(etName.getText().toString(),cbxIndividual.isChecked(),
                        cbxTeam.isChecked(),cbxCustomGame.isChecked(),etInformation.getText().toString());
            }
        });

    }

    //CONTRACT
    @Override
    public void onSuccess() {
        Snackbar.make(findViewById(android.R.id.content),"Propuesta enviada con éxito. ¡Gracias por tu colaboración!",Snackbar.LENGTH_LONG).show();
        etName.setText(null);
        etInformation.setText(null);
        cbxCustomGame.setChecked(false);
        cbxIndividual.setChecked(false);
        cbxTeam.setChecked(false);
    }

    @Override
    public void setMessageError(int error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void errorName(int error) {
        etName.setError(getResources().getString(error));
    }


    public void hideProgressBar() {
        if(progressDialog !=null)
            progressDialog.dismiss();
    }

    public void showProgressBar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Enviando correo de recuperación");
        progressDialog.show();
    }

    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
