package com.example.ismael.pikaos.UI.videogame;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.data.ApiRestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProposeVideogameInteractorImpl {

    interface ProposeVideogameInteractor{
        void onSuccess();
        void onErrorName(int error);
        void onError(int error);
    }

    public void sendGame(final ProposeVideogameInteractor listener, String name, Boolean individual, Boolean team, Boolean custom, String information) {

        if(name.trim().isEmpty()) {
            listener.onErrorName(R.string.error_name_videogame);
        }else {

            Call<Void> callPost = ApiRestClient.getInstance().sendProporsal(name, information, team, individual, custom);

            callPost.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code()==200)
                        listener.onSuccess();
                    else
                        listener.onError(R.string.error_unexpeted);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    listener.onError(R.string.error_cant_connect);
                }
            });
        }
    }
}
