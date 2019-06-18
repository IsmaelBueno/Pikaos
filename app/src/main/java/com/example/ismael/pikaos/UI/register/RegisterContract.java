package com.example.ismael.pikaos.UI.register;

public interface RegisterContract {

    interface View{
        void setErrorMessage(int message);
        void setErrorNick(int message);
        void setErrorEmail(int message);
        void setErrorPassword(int message);
        void setErrorConfirmPassword(int message);
        void onSucess();

    }
    interface Presenter{
        void validateRegister(String nick,String email,String password,String confirmPassword);
    }
}
