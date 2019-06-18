package com.example.ismael.pikaos.UI.register;


import android.os.Build;

import android.support.design.widget.Snackbar;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.base.BaseActivity;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    Button btRegisterRegister;
    Button btBackLogin;
    TextInputEditText etNick;
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    TextInputEditText etPasswordConfirmed;
    Toolbar toolbar;

    RegisterPresenter presenter;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btRegisterRegister:
                    presenter.validateRegister(etNick.getText().toString(), etEmail.getText().toString(),
                            etPassword.getText().toString(),etPasswordConfirmed.getText().toString() );
                    break;
                case R.id.btBackLogin:
                    RegisterActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);
        btRegisterRegister = findViewById(R.id.btRegisterRegister);
        btBackLogin = findViewById(R.id.btBackLogin);
        etNick = findViewById(R.id.etNick);
        etEmail = findViewById(R.id.etEmailRegister);
        etPassword = findViewById(R.id.etPasswordRegister);
        etPasswordConfirmed = findViewById(R.id.etConfirmPassword);

        toolbar.setTitle(R.string.title_register);

        btRegisterRegister.setOnClickListener(listener);
        btBackLogin.setOnClickListener(listener);

        presenter = new RegisterPresenter(this);
    }

    @Override
    public void setErrorMessage(int message) {
        Toast.makeText(this,getResources().getString(message),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setErrorNick(int message) {
        etNick.setError(getResources().getString(message));
    }

    @Override
    public void setErrorEmail(int message) {
        etEmail.setError(getResources().getString(message));
    }

    @Override
    public void setErrorPassword(int message) {
        etPassword.setError(getResources().getString(message));
    }

    @Override
    public void setErrorConfirmPassword(int message) {
        etPasswordConfirmed.setError(getResources().getString(message));
    }

    @Override
    public void onSucess() {
        etNick.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etPasswordConfirmed.setText("");

        Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.sucesfull_register),Snackbar.LENGTH_LONG).show();
    }
}

