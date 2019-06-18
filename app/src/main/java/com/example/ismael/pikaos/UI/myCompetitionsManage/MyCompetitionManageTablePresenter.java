package com.example.ismael.pikaos.UI.myCompetitionsManage;

import com.example.ismael.pikaos.data.model.online.PlayerPoint;

import java.util.List;

public class MyCompetitionManageTablePresenter implements MyCompetitionManageInteractorImpl.manageTable, MyCompetitionManageTableContract.presenter {


    private MyCompetitionsManageTableView view;
    private MyCompetitionManageInteractorImpl interactor;

    public MyCompetitionManageTablePresenter(MyCompetitionsManageTableView view){
        this.view = view;
        this.interactor = new MyCompetitionManageInteractorImpl();
    }


    //INTERACTOR
    @Override
    public void onSuccess(List<PlayerPoint> points) {
        view.setPoints(points);
        view.setTitleFragment();
    }

    @Override
    public void onError(int error) {
        view.setMessageError(error);
        view.setTitleFragment();
    }

    //CONTRACT
    @Override
    public void getPoints(int idCompetition) {
        view.titleLoading();
        interactor.getPoints(this,idCompetition);
    }
}
