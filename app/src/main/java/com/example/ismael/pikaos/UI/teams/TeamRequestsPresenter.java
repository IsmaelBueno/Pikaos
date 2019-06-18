package com.example.ismael.pikaos.UI.teams;

import android.util.Log;

import com.example.ismael.pikaos.data.model.online.TeamRequest;

import java.util.List;

public class TeamRequestsPresenter implements TeamRequestContract.presenter, TeamInteractorImpl.TeamRequestInteractor {

    TeamRequestFragment view;
    TeamInteractorImpl interactor;

    public TeamRequestsPresenter(TeamRequestFragment view){
        this.view = view;
        interactor = new TeamInteractorImpl();
    }

    //CONTRACT
    @Override
    public void getTeamRequests() {
        view.titleLoading();
        interactor.loadTeamRequests(this);
    }

    @Override
    public void acceptTeamRequest(int id) {
        view.titleLoading();
        interactor.acceptRequest(this,id);

    }

    @Override
    public void declineTeamRequest(int id) {
        view.titleLoading();
        interactor.declineRequest(this,id);
    }

    //INTERACTOR
    @Override
    public void setRequests(List<TeamRequest> requests) {
        Log.d("puto","vamos bien");
        view.setTeamRequests(requests);
        view.setTitleFragment();

    }

    @Override
    public void setAnyRequests() {
        view.setAnyRequest();
        view.setTitleFragment();
    }

    @Override
    public void setMessageError(int messageError) {
        view.onErrorMessage(messageError);
        view.setTitleFragment();
    }

    @Override
    public void onSuccess(int id) {
        view.onSuccessResponseRequest(id);
        view.setTitleFragment();
    }
}
