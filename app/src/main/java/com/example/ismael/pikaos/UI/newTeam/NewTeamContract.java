package com.example.ismael.pikaos.UI.newTeam;

import android.graphics.Bitmap;

public interface NewTeamContract {
    interface presenter {
        void createTeam(String name, Bitmap mBitmap);
    }

    interface view{
        void onSuccess();
        void setError(int error);
        void setErrorName(int error);
    }
}
