package com.example.ismael.pikaos.UI.myCompetitionsManage;

import com.example.ismael.pikaos.data.model.online.PlayerPoint;

import java.util.List;

public interface MyCompetitionManageTableContract {

    interface presenter{
        void getPoints(int idCompetition);
    }

    interface view{
        void setPoints(List<PlayerPoint> points);
        void setMessageError(int messageError);
    }
}
