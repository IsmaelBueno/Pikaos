package com.example.ismael.pikaos.UI.myCompetitionsManage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.adapters.online.MyCompetitionsManagePagerAdapter;
import com.example.ismael.pikaos.UI.myCompetitions.MyCompetitionsFragment;
import com.example.ismael.pikaos.data.model.online.MyCompetition;


public class MyCompetitionsManageActivity extends AppCompatActivity implements MyCompetitionsManageContract.view{

    public static final String TAG = "MyCompetitionsManageActivity";

    private MyCompetition thisCompetition;
    private boolean admin = false;

    private MyCompetitionManagePresenter presenter;


    private ProgressDialog progress;

    private TabLayout tbOptions;
    private ViewPager viewPager;
    private Toolbar toolbar;


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        setResult(Activity.RESULT_OK);
        super.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycompetitions_manage);

        tbOptions = findViewById(R.id.tbMyCompetitionsManage);
        viewPager = findViewById(R.id.vpMyCompetitionsManage);
        toolbar = findViewById(R.id.toolbar);

        //Recogemos los datos de esta competición y si el usuario es o no admin
        thisCompetition = (MyCompetition) this.getIntent().getExtras().getSerializable(TAG);
        if(thisCompetition.getAdmin().equals(PikaosApplication.userName))
            admin=true;

        //Presenter
        presenter = new MyCompetitionManagePresenter(this);

        //Refresca el menú
        this.invalidateOptionsMenu();

        //Toolbar y pestañas del tablayout
        setToolbar();
        setTabsOptions();


    }

    private void setToolbar(){
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(thisCompetition.getName());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setTabsOptions(){

        int pages = 2;
        boolean isLeague = thisCompetition.getType().equals("ind_league") || thisCompetition.getType().equals("team_league");

        tbOptions.addTab(tbOptions.newTab());
        tbOptions.addTab(tbOptions.newTab());
        if(isLeague)
            tbOptions.addTab(tbOptions.newTab());



        if(isLeague)
            pages = 3;

        viewPager.setAdapter(new MyCompetitionsManagePagerAdapter(this.getSupportFragmentManager(),
                pages,thisCompetition));
        tbOptions.setupWithViewPager(viewPager);


        tbOptions.getTabAt(0).setText("Chat");
        tbOptions.getTabAt(1).setText("Enfrentamientos");
        if(isLeague)
            tbOptions.getTabAt(2).setText("Tabla");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!admin)
            getMenuInflater().inflate(R.menu.menu_mycompetition, menu);
        else{
            getMenuInflater().inflate(R.menu.menu_mycompetition_admin,menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_mycompetitions_nextround:
                openDialogNextRound();
                break;
            case R.id.action_menu_mycompetitions_report:
                openDialogReport();
                break;
            case R.id.action_menu_mycompetitions_description:
                openDialogDescription();
                break;
            case R.id.action_menu_mycompetitions_cancell:
                openDialogCancell();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialogCancell() {
        String state = thisCompetition.getState();

        if(state.equals("preparing") || state.equals("competing")) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Cancelar competición").setMessage("¿Estás seguro que quieres cancelar la competición?," +
                    " una vez la canceles no podrás gestionarla más");

            alertDialog.setPositiveButton("De acuerdo", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    presenter.cancellCompetition(thisCompetition.getId());
                }
            })
                    .setNegativeButton(getResources().getString(R.string.item_list_alertdialog_cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).create().show();
        }else{
            if(state.equals("cancelled"))
                Toast.makeText(this, "Esta competición ya se encuentra cancelada", Toast.LENGTH_SHORT).show();
            if(state.equals("finished"))
                Toast.makeText(this, "No puedes cancelar una competición finalizada", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDialogNextRound() {
        String state = thisCompetition.getState();

        if(state.equals("preparing")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Comenzar competición").setMessage("Una vez comiences la competición " +
                    "nadie más podrá unirse. ¿Estás seguro?");

            alertDialog.setPositiveButton("Comenzar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    presenter.nextRoundCompetition(thisCompetition.getId());
                }
            })
            .setNegativeButton(getResources().getString(R.string.item_list_alertdialog_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            }).create().show();
        }

        if(state.equals("competing")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Siguiente ronda").setMessage("Una vez pases a la siguiente ronda no "+
                    "podrás modificar los enfrentamientos de rondas anteriores. ¿Estás seguro?");

            alertDialog.setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    presenter.nextRoundCompetition(thisCompetition.getId());
                }
            })
            .setNegativeButton(getResources().getString(R.string.item_list_alertdialog_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            }).create().show();
        }

        if(state.equals("cancelled") || state.equals("finished")){
            Toast.makeText(this, "Esta competición ya se encuentra finalizada", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDialogDescription() {
        if(!admin) {
            if (thisCompetition.getDescription() != null) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Descripción").setMessage(thisCompetition.getDescription()).create().show();
            } else
                Toast.makeText(this, "No hay descripción", Toast.LENGTH_SHORT).show();
        }else{
            final EditText input = new EditText(this);

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Descripción");

            input.setText(thisCompetition.getDescription());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);

            alertDialog.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    presenter.editDescription(thisCompetition.getId(),input.getText().toString());
                }
            })
            .setNegativeButton(getResources().getString(R.string.item_list_alertdialog_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            }).create().show();
        }
    }

    private void openDialogReport(){

        final EditText input = new EditText(this);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Describe brevemente el motivo del reporte");


        input.setHint("Descripción");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Enviar reporte", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                presenter.sendReport(thisCompetition.getId(),input.getText().toString());
            }
        })
        .setNegativeButton(getResources().getString(R.string.item_list_alertdialog_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).create().show();
    }

    public void showProgressDialog(){
        progress = ProgressDialog.show(this, "Conectando con el servidor...",null
                , true);
    }

    public void hideProgressDialog(){
        if(progress!=null)
            progress.dismiss();
    }


    //CONTRACT
    @Override
    public void onSuccessDescriptionEdited(String description) {
        thisCompetition.setDescription(description);
    }

    @Override
    public void onSuccessReported() {
        Snackbar.make(findViewById(android.R.id.content),"Reporte enviado con éxito, gracias por tu colaboración",Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessNextRound() {
        thisCompetition.setActualRound(thisCompetition.getActualRound()+1);
        thisCompetition.setState("competing");
        refreshConfrontationFragment();
    }

    @Override
    public void setMessageError(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessCancelled() {
        thisCompetition.setState("cancelled");
        refreshConfrontationFragment();
        Snackbar.make(findViewById(android.R.id.content),"Competición cancelada",Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessFinished() {
        thisCompetition.setState("finished");
        refreshConfrontationFragment();
        Snackbar.make(findViewById(android.R.id.content),"¡Competición finalizada!",Snackbar.LENGTH_LONG).show();
    }

    private void refreshConfrontationFragment(){
        //Tag por defecto del viewpager que le asgina android
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpMyCompetitionsManage + ":" + 1);
        page.getFragmentManager().beginTransaction().detach(page).commit();
        page.getFragmentManager().beginTransaction().attach(page).commit();

        //Esto solo para la categoria liga, refrescar la tabla
        if(thisCompetition.getType().equals("ind_league") || thisCompetition.getType().equals("team_league")) {
            Fragment table = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpMyCompetitionsManage + ":" + 2);
            page.getFragmentManager().beginTransaction().detach(table).commit();
            page.getFragmentManager().beginTransaction().attach(table).commit();
        }
    }
}
