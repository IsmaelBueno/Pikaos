package com.example.ismael.pikaos.data.repository.local;

import com.example.ismael.pikaos.data.dao.local.CompetitionLocalDao;
import com.example.ismael.pikaos.data.model.local.CompetitionLocal;
import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;

import java.util.ArrayList;

public class CompetitionLocalRepository {

    private CompetitionLocalDao dao;
    private static CompetitionLocalRepository competitionLocalRepository;

    static{
        competitionLocalRepository = new CompetitionLocalRepository();
    }

    private CompetitionLocalRepository() {
        dao = new CompetitionLocalDao();
    }


    public ArrayList<CompetitionLocal> getAll() {
        return dao.getAll();
    }

    public boolean delete(long idCompetition){
        return dao.delete(idCompetition);
    }

    public ArrayList<CompetitionLocalView> getAllView(){
        return dao.getAllView();
    }

    public long add(String name, String type, String description){ return dao.add(name,type,description); }



    public static CompetitionLocalRepository getInstance() {

        if(competitionLocalRepository==null)
            competitionLocalRepository = new CompetitionLocalRepository();
        return competitionLocalRepository;
    }

    public void updateFinished(long idCompetition) {
        dao.updateFinished(idCompetition);
    }
}
