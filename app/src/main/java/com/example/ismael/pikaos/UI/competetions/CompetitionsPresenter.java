package com.example.ismael.pikaos.UI.competetions;

import android.content.Context;

import com.example.ismael.pikaos.data.model.online.Competition;

import java.util.List;

public class CompetitionsPresenter implements CompetitionsContract.Presenter,CompetitionsInteractorImpl.CompetitionsInteractor {

    private CompetitionsFragment view;
    private CompetitionsInteractorImpl interactor;

    public CompetitionsPresenter(CompetitionsFragment view){
        this.view = view;
        interactor = new CompetitionsInteractorImpl();
    }

    @Override
    public void onLoadCompetitions(Context context) {
        view.titleLoading();
        interactor.loadCompetitions(this,context);
    }

    @Override
    public void joinGame(int id, String password) {
        view.titleLoading();
        interactor.joinGame(this,id,password);
    }

    @Override
    public void onSuccess(List<Competition> competitions) {
        view.setTitleFragment();
        view.setLoadedCompetitions(competitions);
    }

    @Override
    public void onError(int message) {
        view.setGenericError(message);
        view.setTitleFragment();
    }

    @Override
    public void joinedGame(int idCompetition) {
        view.setJoinedCompetition(idCompetition);
        view.setTitleFragment();
    }
}
