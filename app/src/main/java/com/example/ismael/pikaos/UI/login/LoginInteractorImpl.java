package com.example.ismael.pikaos.UI.login;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.Utils;
import com.example.ismael.pikaos.data.ApiRestClient;
import com.example.ismael.pikaos.data.model.online.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginInteractorImpl {

    LoginInteractor listener;

    interface LoginInteractor{
        void onCantConnect(int message);
        void onWrongCredentials(int message);
        void onUnvalidatedEmail();
        void onEmptyEmail(int message);
        void onEmptyPassword(int message);
        void onSetProgress(int progress);
        void onSucess();
        void onSuccessEmailResend();
        void onError(int error);
    }

    public LoginInteractorImpl(LoginInteractor listener){
        this.listener = listener;
    }

    @SuppressLint("StaticFieldLeak")
    public void validateCredentials(final String email, final String password){
        if(email.trim().isEmpty())
            listener.onEmptyEmail(R.string.error_login_empty_email);
        else if(password.isEmpty())
            listener.onEmptyPassword(R.string.error_login_empty_password);
        else{

            listener.onSetProgress(1);

            Call<LoginResponse> callPost = ApiRestClient.getInstance().login(email, Utils.SHA256(password));

            callPost.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    switch (response.code()){
                        case 200:
                            PikaosApplication.token = response.body().getToken();
                            PikaosApplication.userName = response.body().getUser();
                            PikaosApplication.userAvatar = response.body().getAvatar();
                            PikaosApplication.userStatus = response.body().getStatus();

                            listener.onSetProgress(2);
                            listener.onSucess();
                            break;

                        case 400:
                            listener.onSetProgress(0);
                            listener.onUnvalidatedEmail();
                            break;

                        case 401:
                            listener.onSetProgress(0);
                            listener.onWrongCredentials(R.string.error_login_wrong_credentials);
                            break;
                        default:
                            listener.onCantConnect(R.string.error_unexpeted);
                            break;
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    listener.onSetProgress(0);
                    listener.onCantConnect(R.string.error_cant_connect);
                }
            });
        }
    }

    public void resendEmailVirify(final LoginInteractor listener, String email) {
        Call<Void> callPost= ApiRestClient.getInstance().sendVerifyEmail(email);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 200:
                        listener.onSuccessEmailResend();
                        break;
                    case 400:
                        listener.onError(R.string.error_user_not_exists);
                        break;
                    default:
                        listener.onError(R.string.error_unexpeted);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }
}
