package com.example.ismael.pikaos.UI.user;

import java.util.List;

public class UserPresenter implements UserContract.presenter,UserInteractorImpl.UserInteractor{

    private UserInteractorImpl interactor;
    private UserFragment view;

    public UserPresenter(UserFragment view){
        this.view = view;
        interactor = new UserInteractorImpl();
    }

    //CONTRACT
    @Override
    public void changePassword(String actualPassword, String newPassword, String confirmNewPassword) {
        interactor.changePassword(this,actualPassword,newPassword,confirmNewPassword);
    }

    @Override
    public void changeStatus(String status) {
        interactor.changeStatus(this,status);
    }

    @Override
    public void getAvatars() {
        interactor.getAvatars(this);
    }

    @Override
    public void changeAvatar(String avatar) {
        interactor.changeAvatar(this,avatar);
    }

    //INTERACTOR
    @Override
    public void onSuccessPasswordChanged() {
        view.onSuccessChangedPassword();
    }

    @Override
    public void onError(int error) {
        view.setMessageError(error);
    }

    @Override
    public void onWrongActualPassword() {
        view.onErrorActualPassword();
    }

    @Override
    public void onWrongNewPassword() {
        view.onErrorNewPassword();
    }

    @Override
    public void onErrorConfirmNewPassword() {
        view.onErrorConfirmPassword();
    }

    @Override
    public void onSuccessStatusChanged() {
        view.onSucceessChangedStatus();
    }

    @Override
    public void loadedAvatars(List<String> avatars) {
        view.setAvatars(avatars);
    }

    @Override
    public void onSuccessAvatarChanged(String avatar) {
        view.onSuccessAvatarChanged(avatar);
    }
}
