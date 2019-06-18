package com.example.ismael.pikaos.UI.competitionManageLocal;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.ismael.pikaos.UI.Utils;
import com.example.ismael.pikaos.data.dao.local.PikaosContract;
import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;
import com.example.ismael.pikaos.data.model.local.CupLocal;
import com.example.ismael.pikaos.data.model.local.CupPlayersLocal;
import com.example.ismael.pikaos.data.model.local.CupRoundLocal;
import com.example.ismael.pikaos.data.repository.local.CompetitionLocalRepository;
import com.example.ismael.pikaos.data.repository.local.CupConfrontationLocalRepository;
import com.example.ismael.pikaos.data.repository.local.CupLocalRepository;
import com.example.ismael.pikaos.data.repository.local.CupPlayersLocalRepository;
import com.example.ismael.pikaos.data.repository.local.CupRoundLocalRepository;

import java.util.ArrayList;
import java.util.Random;

public class CompetitionManageLocalInteractor {




    interface ICompetitionManageLocalInteractor{
        void onSuccessLoadingAllRounds(ArrayList<CupRoundLocal> rounds);
        void onSucessLoadingAllConfrontations(ArrayList<CupConfrontationsLocal> confrontations);
        void onSucessFinishCompetition(String winner);
        void onErrorLoadingAllRounds();
        void onErrorLoadingAllConfrontations();
        void onErrorSetWinner();
        void onErrorWinnerEmpty();
        void onErrorCreatingNewRound();
    }

    public void getAllRounds(ICompetitionManageLocalInteractor listener,long idCompetition){
        try {
            listener.onSuccessLoadingAllRounds(CupRoundLocalRepository.getInstance().getAll(idCompetition));
        }catch (SQLiteException e){
            listener.onErrorLoadingAllRounds();
        }
    }

    public void getAllConfrontations(ICompetitionManageLocalInteractor listener, long idCompetition,int round){
        try {
            listener.onSucessLoadingAllConfrontations(CupConfrontationLocalRepository.getInstance().getAll(idCompetition, round));
        }catch (SQLiteException e){
            listener.onErrorLoadingAllConfrontations();
        }
    }

    public void setWinnerConfrontation(ICompetitionManageLocalInteractor listener, long idCup, int nRound, String playerOne, String playerTwo, String winner) {
        try {
            if(CupConfrontationLocalRepository.getInstance().update(idCup,nRound,playerOne,playerTwo,winner))
                listener.onSucessLoadingAllConfrontations(CupConfrontationLocalRepository.getInstance().getAll(idCup,nRound));
            else
                listener.onErrorSetWinner();
        }catch (SQLiteException e) {
            listener.onErrorSetWinner();
        }
    }

    public void setNewRound(ICompetitionManageLocalInteractor listener, long idCompetition, int lastRound) {

        boolean validateWinners = true;

        //1. Primero compruebo que todas los enfrentamientos tienen un ganador establecido
        ArrayList<CupConfrontationsLocal> confrontations = CupConfrontationLocalRepository.getInstance().getAll(idCompetition,lastRound);
        for (int i = 0; i < confrontations.size(); i++) {
            if(confrontations.get(i).getWinner().isEmpty()){
                validateWinners = false;
            }
        }

        //2 En caso de que todos los ganadores de cada enfrentamiento esten establecidos añado la siguiente ronda
        if(!validateWinners)
            listener.onErrorWinnerEmpty();
        else{
            //3. Comprobar que no sea la ultima ronda, en caso de serlo la competición ha finalizado.
            CupLocal cup = CupLocalRepository.getInstance().get(idCompetition);
            if(cup.getnRounds()==lastRound)
                listener.onSucessFinishCompetition(endCompetition(idCompetition,lastRound));
            else {
                //3. Creación del la ronda
                if(createNewRound(idCompetition,lastRound,cup.getnRounds()))
                    listener.onSuccessLoadingAllRounds(CupRoundLocalRepository.getInstance().getAll(idCompetition));
                else
                    listener.onErrorCreatingNewRound();
            }
        }

    }

    private String endCompetition(long idCompetition,int lastRound) {
        //Marcar como elminado al perdedor
        setRemovedPlayers(idCompetition,lastRound);

        //Marcar la competición como acabada
        CompetitionLocalRepository.getInstance().updateFinished(idCompetition);

        //Le pasamos el ganador al presenter
        ArrayList<CupConfrontationsLocal> confrontationsLastRound = CupConfrontationLocalRepository.getInstance().getAll(idCompetition,lastRound);

        return confrontationsLastRound.get(0).getWinner();

    }

    private boolean createNewRound(long idCompetition,int lastRound,int nRounds){

        int newRound = lastRound+1;
        boolean allCreated = true;

        //Creación de la siguiente ronda
        if(!CupRoundLocalRepository.getInstance().add(idCompetition, newRound,Utils.nameOfRoundCup(newRound,nRounds)))
            allCreated = false;

        //Marcar como eliminado a los jugadores que no son ganadores
        setRemovedPlayers(idCompetition,lastRound);

        //Crear nuevos enfrentamientos con los jugadores que quedan
        ArrayList<CupPlayersLocal> players = CupPlayersLocalRepository.getInstance().getAllNotRemoved(idCompetition);


        Random rnd = new Random();
        while (players.size()>1){
            int playerOnePosition = rnd.nextInt(players.size());
            String playerOne = players.get(playerOnePosition).getName();
            players.remove(playerOnePosition);

            int playerTwoPosition = rnd.nextInt(players.size());
            String playerTwo = players.get(playerTwoPosition).getName();
            players.remove(playerTwoPosition);

            if(!CupConfrontationLocalRepository.getInstance().add(idCompetition,newRound,playerOne,playerTwo,""))
                allCreated = false;
        }

        if(players.size()==1){
            if(!CupConfrontationLocalRepository.getInstance().add(idCompetition,newRound,players.get(0).getName(),"",players.get(0).getName()))
                allCreated = false;
        }

        return allCreated;
    }

    private void setRemovedPlayers(long idCompetition,int lastRound) {
        ArrayList<CupConfrontationsLocal> confrontationsLastRound = CupConfrontationLocalRepository.getInstance().getAll(idCompetition,lastRound);
        ArrayList<String> loosersLastRound = new ArrayList<>();

        for (int i = 0; i < confrontationsLastRound.size(); i++) {
            if(confrontationsLastRound.get(i).getPlayerOne().equals(confrontationsLastRound.get(i).getWinner())){
                loosersLastRound.add(confrontationsLastRound.get(i).getPlayerTwo());
            }else
                loosersLastRound.add(confrontationsLastRound.get(i).getPlayerOne());
        }

        CupPlayersLocalRepository.getInstance().updateLoosers(idCompetition,loosersLastRound);
    }

}
