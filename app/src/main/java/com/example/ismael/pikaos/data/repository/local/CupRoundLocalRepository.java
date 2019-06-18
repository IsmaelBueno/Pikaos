package com.example.ismael.pikaos.data.repository.local;

import com.example.ismael.pikaos.data.dao.local.CupRoundLocalDao;
import com.example.ismael.pikaos.data.model.local.CupRoundLocal;

import java.util.ArrayList;

public class CupRoundLocalRepository {
    private static CupRoundLocalRepository cupRoundLocalRepository;
    private CupRoundLocalDao dao;
    static {
        cupRoundLocalRepository = new CupRoundLocalRepository();
    }

    private CupRoundLocalRepository(){
        dao = new CupRoundLocalDao();
    }

    public static CupRoundLocalRepository getInstance(){
        if(cupRoundLocalRepository == null)
            cupRoundLocalRepository = new CupRoundLocalRepository();
        return cupRoundLocalRepository;
    }

    public boolean add(long id, int round,String name){
        return dao.add(id,round,name);
    }

    public ArrayList<CupRoundLocal> getAll(long idCompetition){
        return dao.getAll(idCompetition);
    }
}
