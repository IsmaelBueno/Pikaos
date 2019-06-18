package com.example.ismael.pikaos.UI.newTeam;

import android.graphics.Bitmap;

class NewTeamPresenter implements NewTeamContract.presenter, NewTeamInteractorImpl.NewTeamInteractor {

    private NewTeamView view;
    private NewTeamInteractorImpl interactor;

    public NewTeamPresenter(NewTeamView view){
        this.view = view;
        this.interactor = new NewTeamInteractorImpl();
    }

    //CONTRACT
    @Override
    public void createTeam(String name, Bitmap mBitmap) {
        view.showProgressBar();
        interactor.createTeam(this, name,mBitmap);

    }

    //INTERACTOR
    @Override
    public void onSuccess() {
        view.onSuccess();
        view.hideProgressBar();

    }

    @Override
    public void onMessageError(int error) {
        view.setError(error);
        view.hideProgressBar();
    }

    @Override
    public void onErrorName(int error) {
        view.setErrorName(error);
        view.hideProgressBar();
    }
}
