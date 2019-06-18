package com.example.ismael.pikaos.UI.myCompetitionsManage;

import com.example.ismael.pikaos.data.model.online.Player;
import com.example.ismael.pikaos.data.model.online.Round;

import java.util.List;

public class MyCompetitionManageConfrontationPresenter implements MyCompetitionManageConfrontationContract.presenter, MyCompetitionManageInteractorImpl.manageConfrontations {

    private MyCompetitionsManageConfrontationView view;
    private MyCompetitionManageInteractorImpl interactor;

    public MyCompetitionManageConfrontationPresenter (MyCompetitionsManageConfrontationView view){
        this.view = view;
        this.interactor = new MyCompetitionManageInteractorImpl();
    }

    //CONTRACT
    @Override
    public void getConfrontations(int id) {
        view.titleLoading();
        interactor.getAllConfrontations(this,id);
    }

    @Override
    public void setWinner(int competition, String winner, int round, String type,String playerone,String playertwo) {
        view.titleLoading();
        interactor.setWinner(this, competition, winner, round, type,playerone,playertwo);
    }

    @Override
    public void getPlyers(int competition) {
        view.titleLoading();
        interactor.getPlayers(this,competition);
    }


    //INTERACTOR
    @Override
    public void onSuccessConfrontations(List<Round> rounds) {
        view.onSuccessGetConfrontations(rounds);
        view.setTitleFragment();
    }

    @Override
    public void onError(int error) {
        view.setMessageError(error);
        view.setTitleFragment();
    }

    @Override
    public void onSuccessSetWinner(String winner,String playerone,String playertwo) {
        view.onSuccessSetWinner(winner,playerone,playertwo);
        view.setTitleFragment();
    }

    @Override
    public void onSuccessPlayers(List<Player> players) {
        view.onSuccessGetPlayers(players);
        view.setTitleFragment();
    }
}
