package com.example.ismael.pikaos.UI.newCompetition;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.Utils;
import com.example.ismael.pikaos.data.ApiRestClient;
import com.example.ismael.pikaos.data.model.online.Videogame;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewCompetitionInteractorImpl {

    private final int MAXLENGTHNAME = 50;
    private final int MINLENGHTNAME = 5;

    private final int MAXPASSWORD = 20;
    private final int MINPASSWORD = 4;
    private final String REGEXPASSWORD = "^[a-zA-Z0-9]+$";

    private final int MAXLENGHTDESCRIPTION = 255;


    public interface NewCompetitionInteractor{
        void onSuccessLoadedVideogames(List<Videogame> videogames);
        void onMessageError(int message);

        void onSuccessCompetitionCreated();
        void onErrorName(int message);
        void onErrorPassword(int message);
        void onErrorDescription(int message);
    }

    public void createCompetition(final NewCompetitionInteractor listener, String name, String typeCompetition, String videogame, String passwordCompetition, String descriptionCompetition, boolean isPublic) {

        boolean allRight = true;

        if(!validateName(name)){
            allRight = false;
            listener.onErrorName(R.string.error_newcomp_wrong_name);
        }

        if(!validateGame(isPublic,passwordCompetition)){
            allRight = false;
            listener.onErrorPassword(R.string.error_newcomp_wrong_password);
        }

        if(!validateDescription(descriptionCompetition)){
            allRight = false;
            listener.onErrorDescription(R.string.error_newcomp_wrong_description);
        }

        if(allRight){

            int privateGame;
            if (isPublic)
                privateGame = 0;
            else
                privateGame =1;

            Call<Void> callPost = ApiRestClient.getInstance().createCompetition(PikaosApplication.token,
                    name.trim(),descriptionCompetition.trim(),videogame, Utils.getTypeCompetitionForDB(typeCompetition),
                    privateGame,passwordCompetition);

            callPost.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    switch (response.code()){
                        case 200:
                            listener.onSuccessCompetitionCreated();
                            break;
                        case 408:
                            listener.onMessageError(R.string.error_user_not_captain);
                            break;
                        case 409:
                            listener.onMessageError(R.string.error_user_not_captain);
                            break;
                        default:
                            listener.onMessageError(R.string.error_unexpeted);
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    listener.onMessageError(R.string.error_cant_connect);
                }
            });
        }

    }

    private boolean validateName(String name){
        return name.trim().length() <= MAXLENGTHNAME && name.trim().length() >= MINLENGHTNAME;
    }

    private boolean validateGame(boolean isPublic, String password){
        if(isPublic)
            return true;
        else{
            return password.length() >= MINPASSWORD && password.length() <= MAXPASSWORD && password.matches(REGEXPASSWORD);
        }
    }

    private boolean validateDescription(String description){
        return description.trim().length() <= MAXLENGHTDESCRIPTION;
    }


    public void getAllVideogamesIndividual(final NewCompetitionInteractor listener) {
        Call<List<Videogame>> callGet = ApiRestClient.getInstance().getAllVideogamesIndividual();

        callGet.enqueue(new Callback<List<Videogame>>() {
            @Override
            public void onResponse(Call<List<Videogame>> call, Response<List<Videogame>> response) {
                if(response.code()==200)
                    listener.onSuccessLoadedVideogames(response.body());
                else
                    listener.onMessageError(R.string.error_unexpeted);

            }

            @Override
            public void onFailure(Call<List<Videogame>> call, Throwable t) {
                listener.onMessageError(R.string.error_cant_connect);
            }
        });
    }

    public void getAllVideogamesTeam(final NewCompetitionInteractor listener){
        Call<List<Videogame>> callGet = ApiRestClient.getInstance().getAllVideogamesTeam();

        callGet.enqueue(new Callback<List<Videogame>>() {
            @Override
            public void onResponse(Call<List<Videogame>> call, Response<List<Videogame>> response) {
                if(response.code()==200)
                    listener.onSuccessLoadedVideogames(response.body());
                else
                    listener.onMessageError(R.string.error_unexpeted);

            }

            @Override
            public void onFailure(Call<List<Videogame>> call, Throwable t) {
                listener.onMessageError(R.string.error_cant_connect);
            }
        });
    }
}
