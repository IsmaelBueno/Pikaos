package com.example.ismael.pikaos.UI.newCompetitionLocal;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class NewCompetitionLocalPresenter implements NewCompetitionLocalContract.Presenter,
        NewCompetitionLocalInteractor.onAddNewCompetition,NewCompetitionLocalInteractor.onAddNewPlayer {

    NewCompetitionLocalView view;
    NewCompetitionLocalInteractor interactor;

    public NewCompetitionLocalPresenter(NewCompetitionLocalView view){
        this.view = view;
        interactor = new NewCompetitionLocalInteractor();
    }

    //Contrato
    @Override
    public void createCompetition(String name, String description, String typeCompetition, ArrayList<String> players) {
        view.titleLoading();
        interactor.addNewCompetition(this,name,description,typeCompetition,players);
    }

    @Override
    public void addNewPlayer(String name, ArrayAdapter<String> players) {
        view.titleLoading();
        interactor.addNewPlayer(this,name,players);
    }

    //Interactor
    @Override
    public void onSuccess() {
        view.onSuccess();
        view.setTitleFragment();
    }

    @Override
    public void onErrorName() {
        view.errorName();
        view.setTitleFragment();

    }

    @Override
    public void onErrorDescription() {
        view.errorDescription();
        view.setTitleFragment();

    }

    @Override
    public void onErrorPlayers() {
        view.errorPlayers();
        view.setTitleFragment();

    }

    @Override
    public void onErrorSQLite() {
        view.errorSqlite();
        view.setTitleFragment();
    }

    @Override
    public void onSuccessNewPlayer() {
        view.onSucessNewPlayer();
        view.setTitleFragment();
    }

    @Override
    public void onErrorNewPlayer() {
        view.onErrorPlayer();
        view.setTitleFragment();
    }

    @Override
    public void onErrorMaxPlayers() {
        view.onErrorMaxPlayers();
        view.setTitleFragment();
    }

    @Override
    public void onErrorPlayerExists() {
        view.onErrorPlayerExists();
        view.setTitleFragment();
    }
}
