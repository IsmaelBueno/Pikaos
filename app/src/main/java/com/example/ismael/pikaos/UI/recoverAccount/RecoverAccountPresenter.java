package com.example.ismael.pikaos.UI.recoverAccount;

public class RecoverAccountPresenter implements RecoverAccountContract.presenter,
        RecoverAccountInteractorImpl.RecoverAccountInteractor{

    private RecoverAccountView view;
    private RecoverAccountInteractorImpl interactor;

    public RecoverAccountPresenter (RecoverAccountView view){
        this.view = view;
        this.interactor = new RecoverAccountInteractorImpl();
    }

    //CONTRACT
    @Override
    public void sendRecoverData(String user) {
        view.showProgressBar();
        interactor.SendRecoverEmail(this,user);
    }

    //INTERACTOR
    @Override
    public void onSuccess() {
        view.onSuccess();
        view.hideProgressBar();
    }

    @Override
    public void onError(int error) {
        view.setMessageError(error);
        view.hideProgressBar();
    }

    @Override
    public void onErrorUser(int error) {
        view.setWrongUser(error);
        view.hideProgressBar();
    }
}
