package com.example.ismael.pikaos.UI.register;


import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.Utils;
import com.example.ismael.pikaos.data.ApiRestClient;
import com.example.ismael.pikaos.data.model.online.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RegisterInteractorImpl {

    private final int MAXLENGTHNICK = 25;
    private final int MINLENGTHNICK = 3;
    private final int MINLENGTHPASSWORD = 4;
    private final int MAXLENGTHPASSWORD = 20;
    private final String PATTERNNICK ="^[ A-Za-zñÑ0-9_./#+-]*$";
    private final String PATTERNPASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])(?!.*\\s).*$";

    interface RegisterInteractor{
        void onErrorMessage(int message);
        void onErrorNick(int errorMessage);
        void onErrorEmail(int errorMessage);
        void onErrorPassword(int errorMessage);
        void onErrorConfirmPassword(int errorMessage);
        void onSucess();
    }

    private RegisterInteractor listener;

    public RegisterInteractorImpl(RegisterInteractor listener){
        this.listener = listener;
    }

    public void validateRegister(String nick, String email, String password, String confirmPassword){

        if(validateNick(nick) && validateEmail(email) && validatePassword(password) && validateConfirmPassword(password,confirmPassword)){

            Call<String> callPost = ApiRestClient.getInstance().register(nick, Utils.SHA256(password),email);

            callPost.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    switch (response.code()){

                        case 200:
                            listener.onSucess();
                            break;

                        case 409:
                            listener.onErrorMessage(R.string.error_email_user_existing);
                            break;

                            default:
                                listener.onErrorMessage(R.string.error_strange);
                                break;
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    listener.onErrorMessage(R.string.error_cant_connect);
                }
            });
        }
    }

    private boolean validateNick(String nick){
        if(nick.trim().isEmpty()) {
            listener.onErrorNick(R.string.error_register_empty_nick);
            return false;
        }

        //TODO mirar el pattern de no mas de un espacio
        if(nick.length() < MINLENGTHNICK || nick.length() > MAXLENGTHNICK  || !nick.matches(PATTERNNICK)){
            listener.onErrorNick(R.string.error_register_wrong_nick);
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email){
        if(email.trim().isEmpty()){
            listener.onErrorEmail(R.string.error_register_empty_email);//email vacio
            return false;
        }

        return true;
    }

    private boolean validatePassword(String password){

        if(password.isEmpty()){
            listener.onErrorPassword(R.string.error_register_empty_password);
            return false;
        }

        if(!password.matches(PATTERNPASSWORD) || password.length() > MAXLENGTHPASSWORD || password.length() < MINLENGTHPASSWORD){
            listener.onErrorPassword(R.string.error_register_wrong_password);
            return false;
        }

        return true;
    }

    private boolean validateConfirmPassword(String password,String confirmPassword){
        if(!password.equals(confirmPassword)){
            listener.onErrorPassword(R.string.error_confirm_password);
            listener.onErrorConfirmPassword(R.string.error_confirm_password);
            return false;
        }

        return true;
    }
}
