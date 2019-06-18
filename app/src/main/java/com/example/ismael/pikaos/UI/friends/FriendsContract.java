package com.example.ismael.pikaos.UI.friends;

public interface FriendsContract {

    interface view{
        void showMessage(int message);
    }

    interface presenter{
        void sendRequest(String user);
    }
}
