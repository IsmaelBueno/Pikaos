package com.example.ismael.pikaos.UI.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

//    public void hideProgressBar() {
//        if(progressDialog !=null)
//            progressDialog.dismiss();
//    }
//
//    public void showProgressBar(Context context) {
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setCancelable(false);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setTitle("Iniciando sesión");
//        progressDialog.show();
//    }

    public void hideKeyBoard(){
        View view = getCurrentFocus();
        InputMethodManager keyBoardManagger = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        keyBoardManagger.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
