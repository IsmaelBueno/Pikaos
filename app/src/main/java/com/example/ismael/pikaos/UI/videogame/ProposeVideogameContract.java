package com.example.ismael.pikaos.UI.videogame;

public interface ProposeVideogameContract {
    interface view{
        void onSuccess();
        void setMessageError(int error);
        void errorName(int error);
    }

    interface presenter{
        void sendVideogame(String name,Boolean individual,Boolean team, Boolean custom,String information);
    }
}
