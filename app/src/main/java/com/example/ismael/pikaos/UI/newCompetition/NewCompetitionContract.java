package com.example.ismael.pikaos.UI.newCompetition;

import android.support.annotation.Nullable;

import com.example.ismael.pikaos.data.model.online.Videogame;

import java.util.List;

public interface NewCompetitionContract {

    interface View{
        void setErrorName(int message);
        void setErrorPasswordCompetition(int message);
        void setErrorDescriptionCompetition(int message);
        void onSuccessVideogames(List<Videogame> videogames);
        void setMessageError(int message);
        void onSucess();

    }
    interface Presenter{
        void createCompetition(String name, String typeCompetition, String videogame, @Nullable String passwordCompetition, String descriptionCompetition,boolean IsPublic);
        void getAllVideogamesIndividual();
        void getAllVideogamesTeam();
    }
}
