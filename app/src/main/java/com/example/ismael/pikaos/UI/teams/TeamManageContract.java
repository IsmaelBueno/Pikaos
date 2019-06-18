package com.example.ismael.pikaos.UI.teams;

import com.example.ismael.pikaos.data.model.online.Team;

public interface TeamManageContract {
    interface View{
        void setTeam(Team team);
        void setAnyTeams();
        void setMessage(int message);
        void onSuccessLeaved();
        void onSuccessInvited();
    }

    interface Presenter{
        void loadTeam();
        void leaveTeam();
        void sendRequest(String userTarget);
    }
}
