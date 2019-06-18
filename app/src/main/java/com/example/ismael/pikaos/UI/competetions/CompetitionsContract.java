package com.example.ismael.pikaos.UI.competetions;

import android.content.Context;

import com.example.ismael.pikaos.data.model.online.Competition;

import java.util.List;

public interface CompetitionsContract {
    interface View{
        void setLoadedCompetitions(List<Competition> competitions);
        void setGenericError(int message);
        void setJoinedCompetition(int idCompetition);

    }

    interface Presenter{
        void onLoadCompetitions(Context context);
        void joinGame(int id, String password);
    }
}
