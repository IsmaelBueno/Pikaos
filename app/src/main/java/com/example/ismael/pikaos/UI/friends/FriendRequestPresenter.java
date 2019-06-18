package com.example.ismael.pikaos.UI.friends;

import com.example.ismael.pikaos.data.model.online.FriendRequest;

import java.util.List;

public class FriendRequestPresenter implements FriendRequestContract.presenter,FriendsInteractorImpl.FriendRequestInteractor{

    private FriendRequestsFragment view;
    private FriendsInteractorImpl interactor;

    public FriendRequestPresenter(FriendRequestsFragment view){
        this.view = view;
        interactor = new FriendsInteractorImpl();
    }

    //CONTRACT
    @Override
    public void getFriendRequests() {
        view.titleLoading();
        interactor.getFriendRequests(this);
    }

    @Override
    public void acceptRequest(int id) {
        view.titleLoading();
        interactor.acceptFriendRequest(this,id);
    }

    @Override
    public void declineRequest(int id) {
        view.titleLoading();
        interactor.declineFriendRequest(this,id);
    }

    //INTERACTOR
    @Override
    public void setRequests(List<FriendRequest> requests) {
        view.setFriendRequests(requests);
        view.setTitleFragment();
    }

    @Override
    public void setAnyRequests() {
        view.setAnyRequest();
        view.setTitleFragment();
    }

    @Override
    public void setMessageError(int messageError) {
        view.setErrorMessage(messageError);
        view.setTitleFragment();
    }

    @Override
    public void onSuccess(int id) {
        view.onSuccess(id);
        view.setTitleFragment();
    }
}
