package com.example.ismael.pikaos.UI.friends;

import com.example.ismael.pikaos.data.model.online.Friend;

import java.util.List;

public class FriendsListPresenter implements FriendsListContract.Presenter, FriendsInteractorImpl.FriendListInteractor {

    private FriendsListFragment view;
    private FriendsInteractorImpl interactor;

    public FriendsListPresenter(FriendsListFragment view){
        this.view = view;
        this.interactor = new FriendsInteractorImpl();
    }

    //CONTRACT
    @Override
    public void loadFriends() {
        view.titleLoading();
        interactor.getFriendsList(this);

    }

    @Override
    public void deleteFriend(int id) {
        view.titleLoading();
        interactor.deleteFriend(this,id);
    }

    //INTERACTOR
    @Override
    public void setFriends(List<Friend> friends) {
        view.setFriends(friends);
        view.setTitleFragment();

    }

    @Override
    public void setAnyFriends() {
        view.setAnyFriends();
        view.setTitleFragment();
    }

    @Override
    public void setMessageError(int message) {
        view.setErrorMessage(message);
        view.setTitleFragment();
    }

    @Override
    public void onSucessDeleted(int id) {
        view.onSucessDeleted(id);
        view.setTitleFragment();
    }
}
