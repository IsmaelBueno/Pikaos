package com.example.ismael.pikaos.UI.videogame;

public class ProposeVideogamePresenter implements ProposeVideogameContract.presenter, ProposeVideogameInteractorImpl.ProposeVideogameInteractor {

    private ProposeVideogameView view;
    private ProposeVideogameInteractorImpl interactor;

    public ProposeVideogamePresenter(ProposeVideogameView view){
        this.view = view;
        interactor = new ProposeVideogameInteractorImpl();
    }

    @Override
    public void sendVideogame(String name, Boolean individual, Boolean team, Boolean custom, String information) {
        view.showProgressBar();
        interactor.sendGame(this,name,individual,team,custom,information);
    }

    //INTERACOTOR
    @Override
    public void onSuccess() {
        view.onSuccess();
        view.hideProgressBar();
    }

    @Override
    public void onErrorName(int error) {
        view.errorName(error);
        view.hideProgressBar();
    }

    @Override
    public void onError(int error) {
        view.setMessageError(error);
        view.hideProgressBar();
    }
}
