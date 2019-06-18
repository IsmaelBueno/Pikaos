package com.example.ismael.pikaos.UI.newCompetitionLocal;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public interface NewCompetitionLocalContract {
    interface View{
        void onSuccess();
        void errorName();
        void errorDescription();
        void errorPlayers();
        void errorSqlite();
        void onSucessNewPlayer();
        void onErrorPlayer();
        void onErrorMaxPlayers();
        void onErrorPlayerExists();
    }

    interface Presenter{
        void createCompetition(String name, String description, String typeCompetition, ArrayList<String> players);
        void addNewPlayer(String name,ArrayAdapter<String> players);
    }
}
