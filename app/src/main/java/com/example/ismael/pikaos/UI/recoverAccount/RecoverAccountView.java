package com.example.ismael.pikaos.UI.recoverAccount;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.Utils;

public class RecoverAccountView extends AppCompatActivity implements RecoverAccountContract.view{

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private Toolbar toolbar;
    private TextInputEditText etUser;
    private Button btSendToRecover;
    private Button backLogin;

    private ProgressDialog progressDialog;

    private RecoverAccountPresenter presenter;

    private View.OnClickListener onClickBackLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener onClickRecoverAccount = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(RecoverAccountView.this);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            presenter.sendRecoverData(etUser.getText().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_account_view);

        toolbar = findViewById(R.id.toolbar);
        etUser = findViewById(R.id.etUserRecover);
        btSendToRecover = findViewById(R.id.btRecoverAccount);
        backLogin = findViewById(R.id.btBackLoginRecover);

        toolbar.setTitle("Recuperar cuenta");

        presenter = new RecoverAccountPresenter(this);

        backLogin.setOnClickListener(onClickBackLogin);
        btSendToRecover.setOnClickListener(onClickRecoverAccount);

    }

    @Override
    public void onSuccess() {
        etUser.setError(null);
        Snackbar.make(findViewById(android.R.id.content),"Te hemos enviado un correo con los pasos para recuperar tu contraseña",Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void setMessageError(int error) {
        etUser.setError(null);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setWrongUser(int message) {
        etUser.setError(getResources().getString(message));
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
}
