package com.example.ismael.pikaos.UI.login;

public interface LoginContract {

    interface View{
        void setErrorMessage(int message);
        void setInvalidEmail();
        void setEmptyEmail(int message);
        void setEmptyPassword(int message);
        void onSucess();
        void updateProgressBar(int progress);
        void showProgressBar();
        void hideProgressBar();
        void onSuccessEmailResend();
    }

    interface Presenter{
        void validateCredentials(String email,String password);
        void resendEmailVerified(String toString);
    }
}
