package com.example.ismael.pikaos.data.repository.local;

import com.example.ismael.pikaos.data.dao.local.CupPlayersLocalDao;
import com.example.ismael.pikaos.data.model.local.CupPlayersLocal;

import java.util.ArrayList;

public class CupPlayersLocalRepository {
    private static CupPlayersLocalRepository cupPlayersLocalRepository;
    private CupPlayersLocalDao dao;

    static {
        cupPlayersLocalRepository = new CupPlayersLocalRepository();
    }

    private CupPlayersLocalRepository(){
        dao = new CupPlayersLocalDao();
    }

    public static CupPlayersLocalRepository getInstance(){
        if(cupPlayersLocalRepository ==null)
            cupPlayersLocalRepository = new CupPlayersLocalRepository();
        return cupPlayersLocalRepository;
    }

    public boolean add(long id, String name,boolean removed){
        return dao.add(id,name,removed);
    }

    public void updateLoosers(long idCompetition,ArrayList<String> loosersLastRound) {
        dao.updateLoosers(idCompetition,loosersLastRound);
    }

    public ArrayList<CupPlayersLocal> getAllNotRemoved(long idCompetition) {
        return dao.getAllNotRemoved(idCompetition);
    }
}
