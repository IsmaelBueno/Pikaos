package com.example.ismael.pikaos.UI.competitionsLocal;

import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;

import java.util.ArrayList;

public class CompetitionLocalPresenter implements CompetitionLocalContract.Presenter,
        CompetitionLocalInteractor.onLoadCompetitionsLocal,CompetitionLocalInteractor.onDeleteCompetitionLocal{

    CompetitionLocalListView view;
    CompetitionLocalInteractor interactor;

    public CompetitionLocalPresenter(CompetitionLocalListView view){
        this.view = view;
        interactor = new CompetitionLocalInteractor();
    }

    @Override
    public void loadCompetitions() {
        view.titleLoading();
        interactor.loadingCompetitionsLocal(this);
    }

    @Override
    public void deleteCompetition(int id) {
        view.titleLoading();
        interactor.deletingCompetitionLocal(this,id);
    }

    //Del interactor para listar
    @Override
    public void onLoadCompetitionsLocal(ArrayList<CompetitionLocalView> competitions) {
        view.setCompetitionsLocal(competitions);
        view.setTitleFragment();
    }

    @Override
    public void onErrorLoading(int error) {
        view.setError(error);
        view.setTitleFragment();
    }

    //Del interactor para borrar
    @Override
    public void onDeleteCompetitionLocal() {
        view.deleteCompetition();
        view.setTitleFragment();
    }

    @Override
    public void onErrorDeleting(int error) {
        view.setError(error);
        view.setTitleFragment();
    }
}
