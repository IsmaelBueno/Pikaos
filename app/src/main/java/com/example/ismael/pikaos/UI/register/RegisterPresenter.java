package com.example.ismael.pikaos.UI.register;

public class RegisterPresenter implements RegisterContract.Presenter, RegisterInteractorImpl.RegisterInteractor {

    private RegisterActivity view;
    private RegisterInteractorImpl interactor;

    public RegisterPresenter(RegisterActivity view){
        this.view = view;
        interactor = new RegisterInteractorImpl(this);
    }

    @Override
    public void validateRegister(String nick, String email, String password, String confirmPassword) {
        //view.showProgressBar(view);
        interactor.validateRegister(nick,email,password,confirmPassword);
    }

    @Override
    public void onErrorMessage(int message) {
        view.setErrorMessage(message);
    }

    @Override
    public void onErrorNick(int errorMessage) {
        //view.hideProgressBar();
        view.setErrorNick(errorMessage);
    }

    @Override
    public void onErrorEmail(int errorMessage) {
        //view.hideProgressBar();
        view.setErrorEmail(errorMessage);
    }

    @Override
    public void onErrorPassword(int errorMessage) {
        //view.hideProgressBar();
        view.setErrorPassword(errorMessage);
    }

    @Override
    public void onErrorConfirmPassword(int errorMessage) {
        //view.hideProgressBar();
        view.setErrorConfirmPassword(errorMessage);
    }

    @Override
    public void onSucess() {
        //view.hideProgressBar();
        view.hideKeyBoard();
        view.onSucess();
    }
}
