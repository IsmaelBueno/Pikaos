package com.example.ismael.pikaos.UI.competitionManageLocal;


import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;
import com.example.ismael.pikaos.data.model.local.CupRoundLocal;

import java.util.ArrayList;

public class CompetitionManageLocalPresenter implements CompetitionManageLocalContract.Presenter,CompetitionManageLocalInteractor.ICompetitionManageLocalInteractor{

    private CompetitionManageLocalInteractor interactor;
    private CompetitionManageLocalView view;

    public CompetitionManageLocalPresenter(CompetitionManageLocalView view){
        this.view = view;
        this.interactor = new CompetitionManageLocalInteractor();
    }



    //Contrato
    @Override
    public void getAllRounds(long idCompetition) {
        view.titleLoading();
        interactor.getAllRounds(this,idCompetition);

    }

    @Override
    public void getAllConfrontations(long idCompetition, int round) {
        view.titleLoading();
        interactor.getAllConfrontations(this,idCompetition,round);

    }

    @Override
    public void setWinner(long idCup, int nRound,String playerOne,String playerTwo, String winner) {
        view.titleLoading();
        interactor.setWinnerConfrontation(this,idCup, nRound,playerOne,playerTwo, winner);
    }

    @Override
    public void setNewRound(long idCompetition, int lastRound) {
        view.titleLoading();
        interactor.setNewRound(this,idCompetition,lastRound);
    }


    //interactor
    @Override
    public void onSuccessLoadingAllRounds(ArrayList<CupRoundLocal> rounds) {
        view.setAllRounds(rounds);
        view.setTitleFragment();
    }

    @Override
    public void onSucessLoadingAllConfrontations(ArrayList<CupConfrontationsLocal> confrontations) {
        view.setActualsConfrontations(confrontations);
        view.setTitleFragment();
    }

    @Override
    public void onSucessFinishCompetition(String winner) {
        view.setFinishedCompetition(winner);
        view.setTitleFragment();
    }

    @Override
    public void onErrorLoadingAllRounds() {
        view.setErrorAllRounds();
        view.setTitleFragment();
    }

    @Override
    public void onErrorLoadingAllConfrontations() {
        view.setErrorActualsConfrontations();
        view.setTitleFragment();
    }

    @Override
    public void onErrorSetWinner() {
        view.setErrorSetWinner();
        view.setTitleFragment();
    }

    @Override
    public void onErrorWinnerEmpty() {
        view.setErrorWinnersEmpty();
        view.setTitleFragment();
    }


    @Override
    public void onErrorCreatingNewRound() {
        view.setErrorCreatingNewRound();
        view.setTitleFragment();
    }
}
