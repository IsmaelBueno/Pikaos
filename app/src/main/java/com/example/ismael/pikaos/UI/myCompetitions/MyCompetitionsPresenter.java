package com.example.ismael.pikaos.UI.myCompetitions;

import android.content.Context;

import com.example.ismael.pikaos.data.model.online.MyCompetition;

import java.util.ArrayList;
import java.util.List;

public class MyCompetitionsPresenter implements MyCompetitionsContract.Presenter,MyCompetitionsInteractorImpl.MyCompetitionsInteractor{

    private MyCompetitionsFragment view;
    private MyCompetitionsInteractorImpl interactor;

    public MyCompetitionsPresenter(MyCompetitionsFragment view){
        this.view = view;
        interactor = new MyCompetitionsInteractorImpl();
    }

    @Override
    public void loadMyCompetitions(Context context) {
        view.titleLoading();
        interactor.loadMyCompetitions(this,context);
    }

    @Override
    public void onSuccess(List<MyCompetition> competitions) {
        view.setLoadedMyCompetitions(competitions);
        view.setTitleFragment();
    }

    @Override
    public void onError(int message) {
        view.setMessageError(message);
        view.setTitleFragment();

    }
}
