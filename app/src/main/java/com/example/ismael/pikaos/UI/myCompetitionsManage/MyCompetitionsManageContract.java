package com.example.ismael.pikaos.UI.myCompetitionsManage;

public interface MyCompetitionsManageContract {

    interface view {
        void onSuccessDescriptionEdited(String description);
        void onSuccessReported();
        void onSuccessNextRound();
        void setMessageError(int message);
        void onSuccessCancelled();
        void onSuccessFinished();
    }

    interface presenter{
        void sendReport(int idCompetition, String description);
        void editDescription(int idCompetition, String newDescription);
        void nextRoundCompetition(int idCompetition);
        void cancellCompetition(int id);
    }

}
