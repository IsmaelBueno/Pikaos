package com.example.ismael.pikaos.UI.friends;

public class FriendsPresenter implements FriendsContract.presenter,FriendsInteractorImpl.SendFriendRequestInteractor{

    private FriendsFragment view;
    private FriendsInteractorImpl interactor;

    public FriendsPresenter(FriendsFragment view){
        this.view = view;
        this.interactor = new FriendsInteractorImpl();
    }


    @Override
    public void sendRequest(String user) {
        view.showProgressBar();
        interactor.sendFriendRequest(this,user);

    }

    @Override
    public void setMessageResponse(int message) {
        view.showMessage(message);
        view.hideProgressBar();
    }
}
