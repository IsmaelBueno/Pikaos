package com.example.ismael.pikaos.UI.myCompetitionsManage;

import com.example.ismael.pikaos.data.model.online.Player;
import com.example.ismael.pikaos.data.model.online.Round;

import java.util.List;

public interface MyCompetitionManageConfrontationContract {

    interface view{
        void onSuccessSetWinner(String winner,String playerone,String playertwo);
        void onSuccessGetConfrontations(List<Round> rounds);
        void setMessageError(int messageError);
        void onSuccessGetPlayers(List<Player> players);
    }

    interface presenter{
        void getConfrontations(int id);
        void setWinner(int competition,String winner,int round, String type,String playerone,String playertwo);
        void getPlyers(int competition);
    }
}
