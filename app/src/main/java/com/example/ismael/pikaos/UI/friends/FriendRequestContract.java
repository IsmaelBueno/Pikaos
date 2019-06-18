package com.example.ismael.pikaos.UI.friends;

import com.example.ismael.pikaos.data.model.online.FriendRequest;

import java.util.List;

public interface FriendRequestContract {
    interface view{
        void setErrorMessage(int message);
        void setFriendRequests(List<FriendRequest> requests);
        void onSuccess(int id);
        void setAnyRequest();
    }

    interface presenter{
        void getFriendRequests();
        void acceptRequest(int id);
        void declineRequest(int id);
    }
}
