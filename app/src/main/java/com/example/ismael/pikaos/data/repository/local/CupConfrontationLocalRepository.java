package com.example.ismael.pikaos.data.repository.local;

import com.example.ismael.pikaos.data.dao.local.CupConfrontationLocalDao;
import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;

import java.util.ArrayList;

public class CupConfrontationLocalRepository {
    private static CupConfrontationLocalRepository cupConfrontationLocalRepository;
    private CupConfrontationLocalDao dao;

    static{
        cupConfrontationLocalRepository = new CupConfrontationLocalRepository();
    }

    private CupConfrontationLocalRepository(){
        dao = new CupConfrontationLocalDao();
    }

    public static CupConfrontationLocalRepository getInstance(){
        if(cupConfrontationLocalRepository == null)
            cupConfrontationLocalRepository = new CupConfrontationLocalRepository();
        return cupConfrontationLocalRepository;
    }

    public boolean add(long id,int round,String playerOne,String playerTwo,String winner){
        return dao.add(id,round,playerOne,playerTwo,winner);
    }

    public ArrayList<CupConfrontationsLocal> getAll(long idCompetition, int round) {
        return dao.getAll(idCompetition,round);
    }

    public boolean update(long idCup, int nRound, String playerOne, String playerTwo, String winner) {
        return dao.updateWinner(idCup,nRound,playerOne,playerTwo,winner);
    }
}
