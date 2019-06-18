package com.example.ismael.pikaos.UI.competitionsLocal;

import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;

import java.util.ArrayList;

public interface CompetitionLocalContract {
    interface View{
        void setCompetitionsLocal(ArrayList<CompetitionLocalView> competitions);
        void deleteCompetition();
        void setError(int messageError);
    }

    interface Presenter{
        void loadCompetitions();
        void deleteCompetition(int id);
    }
}
