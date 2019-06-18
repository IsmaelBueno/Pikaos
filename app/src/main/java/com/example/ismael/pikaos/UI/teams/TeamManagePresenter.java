package com.example.ismael.pikaos.UI.teams;

import com.example.ismael.pikaos.data.model.online.Team;

class TeamManagePresenter implements TeamManageContract.Presenter,TeamInteractorImpl.TeamsInteractor {

    TeamManageFragment view;
    TeamInteractorImpl interactor;

    public TeamManagePresenter(TeamManageFragment view){
        this.view = view;
        this.interactor = new TeamInteractorImpl();
    }


    //CONTRACT
    @Override
    public void loadTeam() {
        view.titleLoading();
        interactor.loadTeam(this);

    }

    @Override
    public void leaveTeam() {
        view.titleLoading();
        interactor.leaveTeam(this);
    }

    @Override
    public void sendRequest(String userTarget) {
        view.titleLoading();
        interactor.sendRequest(this,userTarget);
    }

    //INTERACTOR
    @Override
    public void setLoadedTeam(Team team) {
        view.setTeam(team);
        view.setTitleFragment();
    }

    @Override
    public void setAnyTeam() {
        view.setAnyTeams();
        view.setTitleFragment();
    }

    @Override
    public void setMessageError(int message) {
        view.setMessage(message);
        view.setTitleFragment();
    }

    @Override
    public void onSuccessLeaveTeam() {
        view.onSuccessLeaved();
        view.setTitleFragment();
    }

    @Override
    public void onSuccessRequestSent() {
        view.onSuccessInvited();
        view.setTitleFragment();
    }
}
