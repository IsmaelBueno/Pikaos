package com.example.ismael.pikaos.UI.competitionsLocal;

import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;
import com.example.ismael.pikaos.data.repository.local.CompetitionLocalRepository;

import java.util.ArrayList;

class CompetitionLocalInteractor {

    interface onLoadCompetitionsLocal{
        void onLoadCompetitionsLocal(ArrayList<CompetitionLocalView> competitions);
        void onErrorLoading(int error);
    }

    interface onDeleteCompetitionLocal{
        void onDeleteCompetitionLocal();
        void onErrorDeleting(int error);
    }

    public void loadingCompetitionsLocal(onLoadCompetitionsLocal listener){
        try {
            listener.onLoadCompetitionsLocal(CompetitionLocalRepository.getInstance().getAllView());
        }catch (NullPointerException e) {//TODO bien el catch
            listener.onErrorLoading(0);
        }
    }

    public void deletingCompetitionLocal(onDeleteCompetitionLocal listener, int id){
        if(CompetitionLocalRepository.getInstance().delete(id))
            listener.onDeleteCompetitionLocal();
        else
            listener.onErrorDeleting(0);//todo el error
    }
}
