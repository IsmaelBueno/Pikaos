package com.example.ismael.pikaos.UI.newCompetitionLocal;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.ismael.pikaos.UI.Utils;
import com.example.ismael.pikaos.data.repository.local.CompetitionLocalRepository;
import com.example.ismael.pikaos.data.repository.local.CupConfrontationLocalRepository;
import com.example.ismael.pikaos.data.repository.local.CupLocalRepository;
import com.example.ismael.pikaos.data.repository.local.CupPlayersLocalRepository;
import com.example.ismael.pikaos.data.repository.local.CupRoundLocalRepository;

import java.util.ArrayList;
import java.util.Random;

public class NewCompetitionLocalInteractor {

    onAddNewCompetition listener;

    interface onAddNewPlayer{
        void onSuccessNewPlayer();
        void onErrorNewPlayer();
        void onErrorMaxPlayers();
        void onErrorPlayerExists();
    }

    interface onAddNewCompetition{
        void onSuccess();
        void onErrorName();
        void onErrorDescription();
        void onErrorPlayers();
        void onErrorSQLite();
    }


    public void addNewCompetition(onAddNewCompetition listener, String name, String description, String typeCompetition, ArrayList<String> players){
        this.listener = listener;

        if(validateName(name) && validateDescription(description) && validateMaxPlayers(players)) {
            //Añadimos la competición y recogemos su id
            long idNewCompetition = CompetitionLocalRepository.getInstance().add(name,typeCompetition,description);
            if(idNewCompetition!=-1) {

                //Distinguimos si es copa o liga y creamos con el id de la competición todos los valores por defecto
                //y la tabla con sus jugadores
                switch (typeCompetition){
                    case "Copa":
                            if(createCup(idNewCompetition,players))
                                listener.onSuccess();
                            else {
                                CompetitionLocalRepository.getInstance().delete(idNewCompetition);
                                listener.onErrorSQLite();
                            }
                        break;
                    case "Liga":
                        listener.onSuccess();
                        break;
                        default:
                            break;
                }

            }
            else
                listener.onErrorSQLite();
        }

    }

    public void addNewPlayer(onAddNewPlayer listener,String newPlayer,ArrayAdapter<String> players) {
        final int MAXPLAYERS = 32;
        final int MINLENGHTPLAYER = 3;
        final int MAXLENGHTPLAYER = 50;

        boolean validatePlayer = true;

        if (players.getCount() >= MAXPLAYERS)
            listener.onErrorMaxPlayers();
        else {
            if (newPlayer.trim().length() > MAXLENGHTPLAYER || newPlayer.trim().length() < MINLENGHTPLAYER) {
                listener.onErrorNewPlayer();
            } else {
                for (int i = 0; i < players.getCount(); i++) {
                    if (players.getItem(i).equals(newPlayer)) {
                        listener.onErrorPlayerExists();
                        validatePlayer = false;
                    }
                }
                if(validatePlayer) {
                    players.add(newPlayer.trim());
                    listener.onSuccessNewPlayer();
                }
            }
        }
    }


    private boolean createCup(long idCompetition, ArrayList<String> players){

        boolean allCreated = true;
        final int DEFAULTFIRSTROUND = 1;
        final boolean DEFAULTREMOVEDPLAYER = false;
        final String DEFAULTWINNER = "";

        //Creación de la copa en la BBDD
        int nRounds = Utils.calculateRoundsCup(players.size());
        if(!CupLocalRepository.getInstance().add(idCompetition,nRounds))
            allCreated = false;

        //Creación del la primera ronda
        if(!CupRoundLocalRepository.getInstance().add(idCompetition, DEFAULTFIRSTROUND,Utils.nameOfRoundCup(DEFAULTFIRSTROUND,nRounds)))
            allCreated = false;

        //Añadir los jugadores
        for (int i = 0; i < players.size() ; i++) {
            if(!CupPlayersLocalRepository.getInstance().add(idCompetition,players.get(i),DEFAULTREMOVEDPLAYER))
                allCreated = false;
        }

        //Crear primeros enfrentamiento
        Random rnd = new Random();
        while (players.size()>1){
            int playerOnePosition = rnd.nextInt(players.size());
            String playerOne = players.get(playerOnePosition);
            players.remove(playerOnePosition);

            int playerTwoPosition = rnd.nextInt(players.size());
            String playerTwo = players.get(playerTwoPosition);
            players.remove(playerTwoPosition);

            if(!CupConfrontationLocalRepository.getInstance().add(idCompetition,DEFAULTFIRSTROUND,playerOne,playerTwo,""))
                allCreated = false;
        }

        if(players.size()==1){
            if(!CupConfrontationLocalRepository.getInstance().add(idCompetition,DEFAULTFIRSTROUND,players.get(0),"",players.get(0)))
                allCreated = false;
        }

        return allCreated;
    }

    private boolean validateName(String name){
        int MAXLENGTHNAME = 20;

        if(name.trim().isEmpty() || name.trim().length() > MAXLENGTHNAME){
            listener.onErrorName();
            return false;
        }else
            return true;
    }

    private boolean validateDescription(String description){
        int MAXLENGTHDESCRIPTION = 250;

        if(description.trim().length() > MAXLENGTHDESCRIPTION){
            listener.onErrorDescription();
            return false;
        }else
            return true;
    }

    private boolean validateMaxPlayers(ArrayList<String> players){
        int MINPLAYERS = 3;
        int MAXPLAYERS = 32;

        if(players.size()< MINPLAYERS || players.size()> MAXPLAYERS){
            listener.onErrorPlayers();
            return false;
        }else
            return true;
    }


}
