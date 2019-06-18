package com.example.ismael.pikaos.UI.friends;

import android.app.Application;
import android.util.Log;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.data.ApiRestClient;
import com.example.ismael.pikaos.data.model.online.Friend;
import com.example.ismael.pikaos.data.model.online.FriendRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsInteractorImpl {

    public interface FriendRequestInteractor{
        void setRequests(List<FriendRequest> requests);
        void setAnyRequests();
        void setMessageError(int messageError);
        void onSuccess(int id);
    }

    public interface FriendListInteractor{
        void onSucessDeleted(int id);
        void setFriends(List<Friend>friends);
        void setAnyFriends();
        void setMessageError(int message);
    }

    public interface SendFriendRequestInteractor{
        void setMessageResponse(int message);
    }

    public void getFriendsList(final FriendListInteractor listener){

        Call<List<Friend>> callGet = ApiRestClient.getInstance().getFriends(PikaosApplication.token);

        callGet.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                switch (response.code()) {
                    case 200:

                        if(response.body().size()==0)
                            listener.setAnyFriends();
                        else {

                            List<Friend> friends = response.body();

                            listener.setFriends(friends);
                        }
                        break;
                    default:
                        listener.setMessageError(R.string.error_unexpeted);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });
    }

    public void deleteFriend(final FriendListInteractor listener, final int id){
        Call<Void> callPost = ApiRestClient.getInstance().deleteFriend(PikaosApplication.token,id);
        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 200:
                        listener.onSucessDeleted(id);
                        break;
                    default:
                        listener.setMessageError(R.string.error_unexpeted);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });
    }

    public void sendFriendRequest(final SendFriendRequestInteractor listener,String user){
        Call<Void> callPost = ApiRestClient.getInstance().sendFriendRequest(PikaosApplication.token,user);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                switch (response.code()){
                    case 200:
                        listener.setMessageResponse(R.string.request_sent);
                        break;
                    case 403:
                        listener.setMessageResponse(R.string.error_user_not_exists);
                        break;
                    case 409:
                        listener.setMessageResponse(R.string.error_user_already_friend);
                        break;
                    case 410:
                        listener.setMessageResponse(R.string.error_user_request_already_exists);
                        break;
                    default:
                        listener.setMessageResponse(R.string.error_unexpeted);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.setMessageResponse(R.string.error_cant_connect);
            }
        });
    }

    public void getFriendRequests(final FriendRequestInteractor listener){
        Call<List<FriendRequest>> callGet = ApiRestClient.getInstance().getFriendRequests(PikaosApplication.token);

        callGet.enqueue(new Callback<List<FriendRequest>>() {
            @Override
            public void onResponse(Call<List<FriendRequest>> call, Response<List<FriendRequest>> response) {
                switch (response.code()) {
                    case 200:
                        if(response.body().size()==0)
                            listener.setAnyRequests();
                        else {

                            List<FriendRequest> requests = response.body();

                            listener.setRequests(requests);
                        }
                        break;
                    default:
                        listener.setMessageError(R.string.error_unexpeted);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<FriendRequest>> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });
    }

    public void acceptFriendRequest(final FriendRequestInteractor listener, final int id){
        Call<Void> callPost = ApiRestClient.getInstance().acceptFriendRequest(PikaosApplication.token,id);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 200:
                        listener.onSuccess(id);
                        break;
                    default:
                        listener.setMessageError(R.string.error_unexpeted);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });

    }

    public void declineFriendRequest(final FriendRequestInteractor listener, final int id){
        Call<Void> callPost = ApiRestClient.getInstance().declineFriendRequest(PikaosApplication.token,id);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 200:
                        listener.onSuccess(id);
                        break;
                    default:
                        listener.setMessageError(R.string.error_unexpeted);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });
    }

}
