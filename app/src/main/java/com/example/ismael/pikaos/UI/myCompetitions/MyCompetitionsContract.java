package com.example.ismael.pikaos.UI.myCompetitions;

import android.content.Context;

import com.example.ismael.pikaos.data.model.online.MyCompetition;

import java.util.List;

public interface MyCompetitionsContract {

    interface View{
        void setLoadedMyCompetitions(List<MyCompetition> competitions);
        void setMessageError(int message);
    }

    interface Presenter{
        void loadMyCompetitions(Context context);
    }
}
