package com.example.ismael.pikaos.UI.teams;

import android.util.Log;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.data.ApiRestClient;
import com.example.ismael.pikaos.data.model.online.Team;
import com.example.ismael.pikaos.data.model.online.TeamRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class TeamInteractorImpl {

    interface TeamsInteractor{
        void setLoadedTeam(Team team);
        void setAnyTeam();
        void setMessageError(int message);
        void onSuccessLeaveTeam();
        void onSuccessRequestSent();
    }

    public interface TeamRequestInteractor{
        void setRequests(List<TeamRequest> requests);
        void setAnyRequests();
        void setMessageError(int messageError);
        void onSuccess(int id);
    }




    public void loadTeam(final TeamsInteractor listener) {

        Call<Team> callGet = ApiRestClient.getInstance().getTeam(PikaosApplication.token);

        callGet.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                switch (response.code()) {
                    case 200:
                        listener.setLoadedTeam(response.body());
                        break;
                    case 403:
                        listener.setAnyTeam();
                        break;
                    default:
                        listener.setMessageError(R.string.error_unexpeted);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });
    }

    public void leaveTeam(final TeamsInteractor listener){

        Call<Void> callPost = ApiRestClient.getInstance().leaveTeam(PikaosApplication.token);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200)
                    listener.onSuccessLeaveTeam();
                else
                    listener.setMessageError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });
    }

    public void sendRequest(final TeamsInteractor listener, String userTarget) {
        Call<Void> callPost = ApiRestClient.getInstance().inviteToTeam(PikaosApplication.token,userTarget);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                switch (response.code()){
                    case 200:
                        listener.onSuccessRequestSent();
                        break;
                    case 428:
                        listener.setMessageError(R.string.error_team_maxplayers);
                        break;
                    case 402:
                        listener.setMessageError(R.string.error_user_not_exists);
                        break;
                    case 409:
                        listener.setMessageError(R.string.error_user_request_already_exists);
                        break;
                    default:
                        listener.setMessageError(R.string.error_unexpeted);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });
    }

    public void loadTeamRequests(final TeamRequestInteractor listener){

        Call<List<TeamRequest>> callPost = ApiRestClient.getInstance().getTeamRequests(PikaosApplication.token);

        callPost.enqueue(new Callback<List<TeamRequest>>() {
            @Override
            public void onResponse(Call<List<TeamRequest>> call, Response<List<TeamRequest>> response) {
                if(response.code()==200){
                    if(response.body().size()!=0)
                        listener.setRequests(response.body());
                    else
                        listener.setAnyRequests();

                }else{
                    listener.setMessageError(R.string.error_unexpeted);
                }
            }

            @Override
            public void onFailure(Call<List<TeamRequest>> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });
    }

    public void acceptRequest(final TeamRequestInteractor listener, final int teamId){
        Call<Void> callPost = ApiRestClient.getInstance().acceptTeamRequest(PikaosApplication.token,teamId);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        listener.onSuccess(teamId);
                        break;
                    case 428:
                        listener.setMessageError(R.string.error_user_got_team);
                        break;
                    default:
                        listener.setMessageError(R.string.error_unexpeted);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });
    }

    public void declineRequest(final TeamRequestInteractor listener,final int teamId){

        Call<Void> callPost = ApiRestClient.getInstance().declineTeamRequest(PikaosApplication.token,teamId);

        callPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200)
                    listener.onSuccess(teamId);
                else
                    listener.setMessageError(R.string.error_unexpeted);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.setMessageError(R.string.error_cant_connect);
            }
        });
    }
}
