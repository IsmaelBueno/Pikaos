package com.example.ismael.pikaos.UI.chat;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.data.ApiRestClient;
import com.example.ismael.pikaos.data.model.online.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatInteractorImpl {

    interface ChatInteractor{
        void onMessages(List<Message> messages);
        void onError(int error);
    }

    public void getMessages(final ChatInteractor listener, int user) {
        Call<List<Message>> callGet = ApiRestClient.getInstance().getMessagesFromFriend(PikaosApplication.token,user);

        callGet.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                switch (response.code()){
                    case 200:
                        listener.onMessages(response.body());
                        break;
                    case 409:
                        listener.onError(R.string.error_isNotAFriend);
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
