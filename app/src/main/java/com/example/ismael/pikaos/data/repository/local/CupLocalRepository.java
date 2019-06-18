package com.example.ismael.pikaos.data.repository.local;

import com.example.ismael.pikaos.data.dao.local.CupLocalDao;
import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;
import com.example.ismael.pikaos.data.model.local.CupLocal;

public class CupLocalRepository {
    private static CupLocalRepository cupLocalRepository;
    private CupLocalDao dao;

    static {
        cupLocalRepository = new CupLocalRepository();
    }

    private CupLocalRepository(){
        dao = new CupLocalDao();
    }

    public static CupLocalRepository getInstance(){
        if(cupLocalRepository == null)
            cupLocalRepository = new CupLocalRepository();
        return cupLocalRepository;
    }

    public boolean add (long id,int nRounds ){
        return dao.add(id,nRounds);
    }

    public CupLocal get(long id) {
        return dao.get(id);
    }
}
