package com.example.ismael.pikaos.UI.recoverAccount;

public interface RecoverAccountContract {

    interface view{
        void onSuccess();
        void setMessageError(int error);
        void setWrongUser(int message);
    }

    interface presenter{
        void sendRecoverData(String user);
    }
}
