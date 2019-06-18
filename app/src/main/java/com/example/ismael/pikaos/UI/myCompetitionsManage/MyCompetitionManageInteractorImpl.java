package com.example.ismael.pikaos.UI.myCompetitionsManage;

import android.util.Log;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.data.ApiRestClient;
import com.example.ismael.pikaos.data.model.online.Message;
import com.example.ismael.pikaos.data.model.online.Player;
import com.example.ismael.pikaos.data.model.online.PlayerPoint;
import com.example.ismael.pikaos.data.model.online.ResponseConfrontation;
import com.example.ismael.pikaos.data.model.online.Round;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCompetitionManageInteractorImpl {

    interface manageOptions{
        void onSuccessReported();
        void onError(int error);
        void onSuccessDescrpitionEdited(String description);
        void onSuccessNextRound();
        void onSuccessCancelled();
        void onSuccessFinished();
    }

    interface manageChat{
        void onSuccess(List<Message> messages);
        void onError(int error);
    }

    interface manageConfrontations{
        void onSuccessConfrontations(List<Round> rounds);
        void onError(int error);
        void onSuccessSetWinner(String winner,String playerOne,String playerTwo);
        void onSuccessPlayers(List<Player> players);
    }

    interface manageTable{
        void onSuccess(List<PlayerPoint> points);
        void onError(int error);
    }

    public void sendReport(final manageOptions listener, int idCompetition, String description){
        Call<Void> callPost = ApiRestClient.getInstance().reportCompetition(PikaosApplication.token,idCompetition,description);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                    listener.onSuccessReported();
                else
                    listener.onError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }

    public void editDescription(final manageOptions listener, int idCompetition, final String newDescription) {
        Call<Void> callPost = ApiRestClient.getInstance().editDescription(idCompetition,newDescription);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                    listener.onSuccessDescrpitionEdited(newDescription);
                else
                    listener.onError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }

    public void getAllMessages(final manageChat listener, int chat){
        Call<List<Message>> callPost = ApiRestClient.getInstance().getMessagesFromCompetition(chat);

        callPost.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(response.code()==200)
                    listener.onSuccess(response.body());
                else
                    listener.onError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }

    public void nextRoundCompetition(final manageOptions listener, int idCompetition) {
        Call<ResponseConfrontation> callPost = ApiRestClient.getInstance().nextRound(PikaosApplication.token,idCompetition);

        callPost.enqueue(new Callback<ResponseConfrontation>() {
            @Override
            public void onResponse(Call<ResponseConfrontation> call, Response<ResponseConfrontation> response) {

                switch (response.code()){
                    case 200:
                        //Si ha comenzado o es una ronda siguiente no discriminamos
                        if(response.body().getResponse()==2)
                            listener.onSuccessFinished();
                        else
                            listener.onSuccessNextRound();
                        break;
                    case 401:
                        listener.onError(R.string.error_start_competition_players);
                        break;
                    case 403:
                        listener.onError(R.string.error_confrontations_not_resolved);
                        break;
                    default:
                        listener.onError(R.string.error_unexpeted);
                        break;
                }

            }

            @Override
            public void onFailure(Call<ResponseConfrontation> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });


    }

    public void getAllConfrontations(final manageConfrontations listener,int idCompetition){
        Call<List<Round>> callPost = ApiRestClient.getInstance().getConfrontations(idCompetition);

        callPost.enqueue(new Callback<List<Round>>() {
            @Override
            public void onResponse(Call<List<Round>> call, Response<List<Round>> response) {
                if(response.code()==200)
                    listener.onSuccessConfrontations(response.body());
                else
                    listener.onError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<List<Round>> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }

    public void setWinner(final manageConfrontations listener, int competition, final String winner, int round, String type, final String playerone, final String playertwo) {
        Call<Void> callPost = ApiRestClient.getInstance().setWinner(PikaosApplication.token,competition,round,type,winner,playerone,playertwo);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                    listener.onSuccessSetWinner(winner,playerone,playertwo);
                else
                    listener.onError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }

    public void getPlayers(final manageConfrontations listener, int competition) {
        Call<List<Player>> callGet = ApiRestClient.getInstance().getPlayers(competition);

        callGet.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if(response.code()==200)
                    listener.onSuccessPlayers(response.body());
                else
                    listener.onError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }

    public void getPoints(final manageTable listener, int competitionID){
        Call<List<PlayerPoint>> callGet = ApiRestClient.getInstance().getPoints(competitionID);

        callGet.enqueue(new Callback<List<PlayerPoint>>() {
            @Override
            public void onResponse(Call<List<PlayerPoint>> call, Response<List<PlayerPoint>> response) {
                if(response.code()==200)
                    listener.onSuccess(response.body());
                else
                    listener.onError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<List<PlayerPoint>> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }

    public void cancellCompetition(final manageOptions listener, int id) {
        Call<Void> callPost = ApiRestClient.getInstance().cancellCompetition(id);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                    listener.onSuccessCancelled();
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
