package com.example.ismael.pikaos.UI.competetions;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.data.ApiRestClient;
import com.example.ismael.pikaos.data.model.online.Competition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompetitionsInteractorImpl {

    interface CompetitionsInteractor{
        void onSuccess(List<Competition> competitions);
        void onError(int message);
        void joinedGame(int idCompetition);
    }

    public void loadCompetitions(final CompetitionsInteractor listener, final Context context){
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean indCup = preferences.getBoolean("pref_individualCup",true);
        boolean indLeague = preferences.getBoolean("pref_individualLeague",true);
        boolean teamCup = preferences.getBoolean("pref_teamsCup",true);
        boolean teamLeague =  preferences.getBoolean("pref_teamsLeague",true);

        Map<String, String> options = new HashMap<>();

        if(indCup)
            options.put("indcup", String.valueOf(1));
        if(indLeague)
            options.put("indleague", String.valueOf(1));
        if(teamCup)
            options.put("teamcup", String.valueOf(1));
        if(teamLeague)
            options.put("teamleague", String.valueOf(1));

        Call<List<Competition>> callGet = ApiRestClient.getInstance().getCompetitionsOpen(PikaosApplication.token,options);

        callGet.enqueue(new Callback<List<Competition>>() {
            @Override
            public void onResponse(Call<List<Competition>> call, Response<List<Competition>> response) {
                if(response.code()==200) {
                    //Filtramos si la competición es privada
                    boolean privateGames = preferences.getBoolean("pref_privateCompetitions",true);
                    if(!privateGames){
                        for (int i = 0; i < response.body().size(); i++) {
                            if(response.body().get(i).isPrivate()==1)
                                response.body().remove(i);
                        }
                    }
                    listener.onSuccess(response.body());
                }
                else
                    listener.onError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<List<Competition>> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }

    public void joinGame(final CompetitionsInteractor listener, final int idCompetition, String password){
        Call<Void> callPost = ApiRestClient.getInstance().joinToCompetition(PikaosApplication.token,idCompetition,password);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 200:
                        listener.joinedGame(idCompetition);
                        break;
                    case 401:
                        listener.onError(R.string.error_competition_team_no_captain);
                        break;
                    case 402:
                        listener.onError(R.string.error_comp_wrong_password_private_game);
                        break;
                    case 409:
                        listener.onError(R.string.error_competition_started);
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
