package com.example.ismael.pikaos.UI.newCompetition;

import android.support.annotation.Nullable;

import com.example.ismael.pikaos.data.model.online.Videogame;

import java.util.List;

public class NewCompetitionPresenter implements NewCompetitionContract.Presenter, NewCompetitionInteractorImpl.NewCompetitionInteractor {

    private NewCompetitionView view;
    private NewCompetitionInteractorImpl interactor;

    public NewCompetitionPresenter(NewCompetitionView view){
        this.view = view;
        this.interactor = new NewCompetitionInteractorImpl();
    }

    //CONTRACT
    @Override
    public void createCompetition(String name, String typeCompetition, String videogame, @Nullable String passwordCompetition, String descriptionCompetition, boolean IsPublic) {
        interactor.createCompetition(this,name,typeCompetition,videogame,passwordCompetition,descriptionCompetition,IsPublic);
    }

    @Override
    public void getAllVideogamesIndividual() {
        view.showProgressBar();
        interactor.getAllVideogamesIndividual(this);
    }

    @Override
    public void getAllVideogamesTeam() {
        view.showProgressBar();
        interactor.getAllVideogamesTeam(this);
    }

    //INTERACTOR
    @Override
    public void onSuccessLoadedVideogames(List<Videogame> videogames) {
        view.onSuccessVideogames(videogames);
        view.hideProgressBar();
    }

    @Override
    public void onMessageError(int message) {
        view.setMessageError(message);
        view.hideProgressBar();
    }

    @Override
    public void onSuccessCompetitionCreated() {
        view.onSucess();
        view.hideProgressBar();
    }

    @Override
    public void onErrorName(int message) {
        view.setErrorName(message);
        view.hideProgressBar();
    }

    @Override
    public void onErrorPassword(int message) {
        view.setErrorPasswordCompetition(message);
        view.hideProgressBar();
    }

    @Override
    public void onErrorDescription(int message) {
        view.setErrorDescriptionCompetition(message);
        view.hideProgressBar();
    }
}
