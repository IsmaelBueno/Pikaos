package com.example.ismael.pikaos.UI.teams;

import com.example.ismael.pikaos.data.model.online.TeamRequest;

import java.util.List;

public interface TeamRequestContract {

    interface view{
        void onErrorMessage(int Message);
        void setTeamRequests(List<TeamRequest> requests);
        void onSuccessResponseRequest(int id);
        void setAnyRequest();

    }

    interface presenter{
        void getTeamRequests();
        void acceptTeamRequest(int id);
        void declineTeamRequest(int id);
    }

}
