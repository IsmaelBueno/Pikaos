package com.example.ismael.pikaos.UI.myCompetitionsManage;

import android.util.Log;

public class MyCompetitionManagePresenter implements MyCompetitionsManageContract.presenter, MyCompetitionManageInteractorImpl.manageOptions{

    private MyCompetitionsManageActivity view;
    private MyCompetitionManageInteractorImpl interactor;

    public MyCompetitionManagePresenter(MyCompetitionsManageActivity view){
        this.view = view;
        this.interactor = new MyCompetitionManageInteractorImpl();
    }

    //INTERACTOR
    @Override
    public void onSuccessReported() {
        view.onSuccessReported();
        view.hideProgressDialog();
    }

    @Override
    public void onError(int error) {
        view.setMessageError(error);
        view.hideProgressDialog();

    }

    @Override
    public void onSuccessDescrpitionEdited(String description) {
        view.onSuccessDescriptionEdited(description);
        view.hideProgressDialog();
    }

    @Override
    public void onSuccessNextRound() {
        view.onSuccessNextRound();
        view.hideProgressDialog();
    }

    @Override
    public void onSuccessCancelled() {
        view.onSuccessCancelled();
        view.hideProgressDialog();
    }

    @Override
    public void onSuccessFinished() {
        view.onSuccessFinished();
        view.hideProgressDialog();
    }

    //CONTRACT
    @Override
    public void sendReport(int idCompetition, String description) {
        view.showProgressDialog();
        interactor.sendReport(this,idCompetition,description);
    }

    @Override
    public void editDescription(int idCompetition, String newDescription) {
        view.showProgressDialog();
        interactor.editDescription(this,idCompetition,newDescription);
    }

    @Override
    public void nextRoundCompetition(int idCompetition) {
        view.showProgressDialog();
        interactor.nextRoundCompetition(this,idCompetition);
    }

    @Override
    public void cancellCompetition(int id) {
        view.showProgressDialog();
        interactor.cancellCompetition(this,id);
    }
}
