package com.example.ismael.pikaos.UI.recoverAccount;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.data.ApiRestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoverAccountInteractorImpl {

    interface RecoverAccountInteractor{
        void onSuccess();
        void onError(int error);
        void onErrorUser(int error);
    }

    public void SendRecoverEmail(final RecoverAccountInteractor listener, String data){

        if(data.trim().isEmpty()){
            listener.onErrorUser(R.string.error_wrong_data_recover);
        }else {

            Call<Void> callPost = ApiRestClient.getInstance().sendEmailRecoverAccount(data);

            callPost.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    switch (response.code()) {
                        case 200:
                            listener.onSuccess();
                            break;
                        case 401:
                            listener.onErrorUser(R.string.error_user_not_exists);
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
}
