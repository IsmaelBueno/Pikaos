package com.example.ismael.pikaos.UI.user;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.Utils;
import com.example.ismael.pikaos.data.ApiRestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInteractorImpl {

    private final int MINLENGTHPASSWORD = 4;
    private final int MAXLENGTHPASSWORD = 20;
    private final String PATTERNPASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])(?!.*\\s).*$";

    interface UserInteractor{
        void onSuccessPasswordChanged();
        void onError(int error);
        void onWrongActualPassword();
        void onWrongNewPassword();
        void onErrorConfirmNewPassword();
        void onSuccessStatusChanged();
        void loadedAvatars(List<String> avatars);
        void onSuccessAvatarChanged(String avatar);
    }

    public void changeAvatar(final UserInteractor listener, final String avatar) {
        Call<Void> callPost = ApiRestClient.getInstance().changeAvatar(PikaosApplication.token,avatar);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){

                    case 200:
                        listener.onSuccessAvatarChanged(avatar);
                        //Actualizamos el avatar del menú
                        PikaosApplication.userAvatar = avatar;
                        break;

                    default:
                        listener.onError(R.string.error_strange);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });

    }

    public void getAvatars(final UserInteractor listener) {
        Call<List<String>> callGet = ApiRestClient.getInstance().getURLAvatars();

        callGet.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.code()==200) {
                    listener.loadedAvatars(response.body());
                }
                else
                    listener.onError(R.string.error_strange);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }

    public void changePassword(final UserInteractor listener, String actualPassword, String newPasword, String confirmPassword){
        if(!confirmNewPassword(newPasword,confirmPassword))
            listener.onErrorConfirmNewPassword();
        else{
            if(!validateNewPassword(newPasword))
                listener.onWrongNewPassword();
            else{
                Call<Void> callPost = ApiRestClient.getInstance().changePassword(PikaosApplication.token,
                        Utils.SHA256(actualPassword),Utils.SHA256(newPasword));
                callPost.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        switch (response.code()){

                            case 200:
                                listener.onSuccessPasswordChanged();
                                break;

                            case 401:
                                listener.onWrongActualPassword();
                                break;

                            default:
                                listener.onError(R.string.error_strange);
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
    }

    public void changeStatus(final UserInteractor listener, String status) {
        Call<Void> callPost = ApiRestClient.getInstance().changeStatus(PikaosApplication.token,status);
        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){

                    case 200:
                        listener.onSuccessStatusChanged();
                        break;

                    default:
                        listener.onError(R.string.error_strange);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }

    private boolean confirmNewPassword(String newPassword,String confirmPassword){
        return newPassword.equals(confirmPassword);
    }

    private boolean validateNewPassword(String password){
        if(password.length()>MAXLENGTHPASSWORD || password.length()<MINLENGTHPASSWORD || !password.matches(PATTERNPASSWORD))
            return false;
        else
            return true;
    }
}
