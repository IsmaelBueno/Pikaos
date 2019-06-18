package com.example.ismael.pikaos.UI.login;

public class LoginPresenter implements LoginContract.Presenter,LoginInteractorImpl.LoginInteractor{

    private LoginActivity view;
    private LoginInteractorImpl interactor;

    public LoginPresenter(LoginActivity view){
        this.view = view;
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void validateCredentials(String email, String password) {
        view.showProgressBar();
        interactor.validateCredentials(email,password);
    }

    @Override
    public void resendEmailVerified(String email) {
        interactor.resendEmailVirify(this,email);
    }

    @Override
    public void onCantConnect(int message) {
        view.hideProgressBar();
        view.hideKeyBoard();
        view.setErrorMessage(message);
    }

    @Override
    public void onWrongCredentials(int message) {
        view.hideProgressBar();
        view.hideKeyBoard();
        view.setErrorMessage(message);
    }

    @Override
    public void onUnvalidatedEmail() {
        view.hideProgressBar();
        view.hideKeyBoard();
        view.setInvalidEmail();
    }

    @Override
    public void onEmptyEmail(int message) {
        view.hideProgressBar();
        view.setEmptyEmail(message);
    }

    @Override
    public void onEmptyPassword(int message) {
        view.hideProgressBar();
        view.setEmptyPassword(message);
    }

    @Override
    public void onSetProgress(int progress) {
        view.updateProgressBar(progress);
    }

    @Override
    public void onSucess() {
        view.hideProgressBar();
        view.hideKeyBoard();
        view.onSucess();
    }

    @Override
    public void onSuccessEmailResend() {
        view.onSuccessEmailResend();
    }

    @Override
    public void onError(int error) {
        view.setErrorMessage(error);
    }
}
