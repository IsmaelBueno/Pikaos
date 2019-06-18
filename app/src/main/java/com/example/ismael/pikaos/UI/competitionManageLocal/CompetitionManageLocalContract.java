package com.example.ismael.pikaos.UI.competitionManageLocal;

import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;
import com.example.ismael.pikaos.data.model.local.CupRoundLocal;

import java.util.ArrayList;

public interface CompetitionManageLocalContract {
    interface View{
        void setAllRounds(ArrayList<CupRoundLocal> rounds);
        void setActualsConfrontations(ArrayList<CupConfrontationsLocal> confrontations);
        void setFinishedCompetition(String winner);
        void setErrorAllRounds();
        void setErrorActualsConfrontations();
        void setErrorSetWinner();
        void setErrorWinnersEmpty();
        void setErrorCreatingNewRound();
    }

    interface Presenter{
        void getAllRounds(long idCompetition);
        void getAllConfrontations(long idCompetition, int round);
        void setWinner(long idCup, int nRound,String playerOne,String playerTwo, String winner);
        void setNewRound(long idCompetition,int lastRound);
    }
}
