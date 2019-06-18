package com.example.ismael.pikaos.UI.base;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;

public abstract class BaseFragment extends Fragment {

    ProgressDialog progress;

    public abstract void setTitleFragment();

    public void titleLoading() {
        getActivity().setTitle(R.string.title_loading);
    }

    public void notification(){
        Notification.Builder builder = new Notification.Builder(PikaosApplication.getAppContext());
        builder.setContentTitle("Pikaos");
        builder.setSmallIcon(R.mipmap.ic_app);
//        builder.setContentIntent(pendingIntent);
        builder.setContentText("Se ha añadido una competición");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(PikaosApplication.CHANNEL_ID);
        }


        NotificationManager notificationManager = (NotificationManager) PikaosApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }


    public void hideKeyBoard(){
        //OCULTAR TECLADO
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void showProgressBar(){
        progress = ProgressDialog.show(getContext(), "Enviando petición...",
                "Espera por favor", true);
    }

    public void hideProgressBar(){
        progress.dismiss();
    }
}
