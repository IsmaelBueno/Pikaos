package com.example.ismael.pikaos.UI.myCompetitions;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.data.ApiRestClient;
import com.example.ismael.pikaos.data.model.online.Competition;
import com.example.ismael.pikaos.data.model.online.MyCompetition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MyCompetitionsInteractorImpl {

    interface MyCompetitionsInteractor{
        void onSuccess(List<MyCompetition> competitions);
        void onError(int message);
    }


    public void loadMyCompetitions(final MyCompetitionsInteractor listener, final Context context){

        Map<String, String> options = new HashMap<>();
        options.put("indcup", String.valueOf(1));
        options.put("indleague", String.valueOf(1));
        options.put("teamcup", String.valueOf(1));
        options.put("teamleague", String.valueOf(1));

        Call<List<MyCompetition>> callGet = ApiRestClient.getInstance().getCompetitionsJoined(PikaosApplication.token,options);

        callGet.enqueue(new Callback<List<MyCompetition>>() {
            @Override
            public void onResponse(Call<List<MyCompetition>> call, Response<List<MyCompetition>> response) {
                if(response.code()==200) {
                    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    boolean finishedGame = preferences.getBoolean("pref_finishedGame",true);
                    boolean cancelledGame = preferences.getBoolean("pref_cancelledGame",true);

                    for (int i = 0; i < response.body().size(); i++) {
                        if(response.body().get(i).getState().equals("finished") && !finishedGame)
                            response.body().remove(i);
                        if(response.body().get(i).getState().equals("cancelled") && !cancelledGame)
                            response.body().remove(i);
                    }

                    listener.onSuccess(response.body());
                }
                else
                    listener.onError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<List<MyCompetition>> call, Throwable t) {
                listener.onError(R.string.error_cant_connect);
            }
        });
    }
}
