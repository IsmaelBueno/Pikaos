package com.example.ismael.pikaos.UI.chatTeam;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.data.ApiRestClient;
import com.example.ismael.pikaos.data.model.online.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatTeamInteractorImpl {

    interface ChatTeamInteractor{
        void onSuccess(List<Message> messages);
        void onError(int error);
    }


    public void getMessages(final ChatTeamInteractor listener) {

        Call<List<Message>> callGet = ApiRestClient.getInstance().getMessagesFromTeam(PikaosApplication.token);

        callGet.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                switch (response.code()){
                    case 200:
                        listener.onSuccess(response.body());
                        break;
                    case 409:
                        listener.onError(R.string.error_user_not_team);
                        break;
                    default:
                        listener.onError(R.string.error_unexpeted);
                        break;

                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }
}
