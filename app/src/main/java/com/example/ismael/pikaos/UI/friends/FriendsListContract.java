package com.example.ismael.pikaos.UI.friends;

import com.example.ismael.pikaos.data.model.online.Friend;

import java.util.List;

public interface FriendsListContract {
    interface View{
        void setFriends(List<Friend> friends);
        void setErrorMessage(int message);
        void setAnyFriends();
        void onSucessDeleted(int id);
    }

    interface Presenter{
        void loadFriends();
        void deleteFriend(int id);
    }
}
