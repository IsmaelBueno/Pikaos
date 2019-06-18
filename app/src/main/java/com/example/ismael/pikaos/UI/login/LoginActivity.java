package com.example.ismael.pikaos.UI.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.base.BaseActivity;
import com.example.ismael.pikaos.UI.menu.MenuActivity;
import com.example.ismael.pikaos.UI.menuLocal.ActivityMenuLocal;
import com.example.ismael.pikaos.UI.recoverAccount.RecoverAccountView;
import com.example.ismael.pikaos.UI.register.RegisterActivity;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    Button btLogin;
    Button btRegister;
    Button btLocal;
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    CheckBox cbRememberme;
    TextView emailNotVerified;
    TextView passwordForget;
    ProgressBar progressBar;

    LoginPresenter presenter;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btLogin:
                    presenter.validateCredentials(etEmail.getText().toString(),etPassword.getText().toString());
                    break;
                case R.id.btRegister:
                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                    break;
                case R.id.btLocal:
                    startActivity(new Intent(LoginActivity.this, ActivityMenuLocal.class));
                    break;
                case R.id.tvVerifyEmail:
                    presenter.resendEmailVerified(etEmail.getText().toString());
                    break;
                case R.id.tvForgotPassword:
                    startActivity(new Intent(LoginActivity.this, RecoverAccountView.class));
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etiPassword);
        cbRememberme = findViewById(R.id.cbxRememberme);
        progressBar = findViewById(R.id.progressBar);
        emailNotVerified = findViewById(R.id.tvVerifyEmail);
        passwordForget = findViewById(R.id.tvForgotPassword);
        btLocal = findViewById(R.id.btLocal);

        presenter = new LoginPresenter(this);

        loadCheckBox();

        if(cbRememberme.isChecked())
            loadRemeberCredentials();

        btLogin.setOnClickListener(listener);
        btRegister.setOnClickListener(listener);
        btLocal.setOnClickListener(listener);
        emailNotVerified.setOnClickListener(listener);
        passwordForget.setOnClickListener(listener);

    }


    @Override
    public void setErrorMessage(int message) {
        Toast.makeText(this, getResources().getString(message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setInvalidEmail() {
        etEmail.setError("Aún no has verificado tu correo");
        emailNotVerified.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmptyEmail(int message) {
        etEmail.setError(getResources().getString(message));
    }

    @Override
    public void setEmptyPassword(int message) {
        etPassword.setError(getResources().getString(message));
    }

    @Override
    public void onSucess() {

        //Cuando se loguee en función a como estaba el checkbox se guardarán las nuevas credneciales o se borraran si es que habia
        if(cbRememberme.isChecked())
            saveRememberCredentials();
        else
            deleteRememberCredentials();

        emailNotVerified.setVisibility(View.GONE);
        startActivity(new Intent(LoginActivity.this,MenuActivity.class));
    }


    //TODO probablemente haya que refactorizar esto al MVP
    private void loadCheckBox(){
        SharedPreferences rememberData = getSharedPreferences("REMEMBER", Context.MODE_PRIVATE);
        cbRememberme.setChecked(rememberData.getBoolean("CHECK",false));

        cbRememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences rememberData = getSharedPreferences("REMEMBER", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = rememberData.edit();
                editor.putBoolean("CHECK",isChecked);
                editor.apply();
            }
        });
    }

    private void deleteRememberCredentials(){
        SharedPreferences loginData = getSharedPreferences("LOGINDATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.clear();
        editor.apply();
    }

    private void saveRememberCredentials(){
        SharedPreferences loginData = getSharedPreferences("LOGINDATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("EMAIL", etEmail.getText().toString());
        editor.putString("PASSWORD", etPassword.getText().toString());
        editor.apply();
    }

    private void loadRemeberCredentials(){
        SharedPreferences loginData = getSharedPreferences("LOGINDATA", Context.MODE_PRIVATE);

        String email = loginData.getString("EMAIL", "");
        String password = loginData.getString("PASSWORD", "");

        etEmail.setText(email);
        etPassword.setText(password);

    }

    @Override
    public void showProgressBar(){
        btLogin.setEnabled(false);
        progressBar.setMax(2);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
        btLogin.setEnabled(true);
    }

    @Override
    public void onSuccessEmailResend() {
        emailNotVerified.setVisibility(View.GONE);
        Snackbar.make(findViewById(android.R.id.content),"Verificación reenvíada de nuevo con éxito.",Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void updateProgressBar(int progress) {
        progressBar.setProgress(progress);
    }
}
