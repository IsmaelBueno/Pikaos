package com.example.ismael.pikaos.data;

import com.example.ismael.pikaos.data.model.online.Competition;
import com.example.ismael.pikaos.data.model.online.Friend;
import com.example.ismael.pikaos.data.model.online.FriendRequest;
import com.example.ismael.pikaos.data.model.online.LoginResponse;
import com.example.ismael.pikaos.data.model.online.Message;
import com.example.ismael.pikaos.data.model.online.MyCompetition;
import com.example.ismael.pikaos.data.model.online.Player;
import com.example.ismael.pikaos.data.model.online.PlayerPoint;
import com.example.ismael.pikaos.data.model.online.ResponseConfrontation;
import com.example.ismael.pikaos.data.model.online.Round;
import com.example.ismael.pikaos.data.model.online.Team;
import com.example.ismael.pikaos.data.model.online.TeamRequest;
import com.example.ismael.pikaos.data.model.online.Videogame;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {


    //region Login & Register
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("user") String user,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<String> register(
            @Field("user") String user,
            @Field("password") String password,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("login/sendverifyemail")
    Call<Void> sendVerifyEmail(
            @Field("credential") String user
    );

    @FormUrlEncoded
    @POST("login/recoverAccount")
    Call<Void> sendEmailRecoverAccount(
            @Field("user") String user
    );

    //endregion

    //region Team
    @Multipart
    @POST("team/create")
    Call<ResponseBody> createTeam(
            @Header("Authorization") String token,
            @Part("nameTeam") String nameTeam,

            @Part MultipartBody.Part image,
            @Part("upload") RequestBody name);

    @GET("team")
    Call<Team> getTeam(
            @Header("Authorization") String token
    );


    @POST("team/leave")
    Call<Void> leaveTeam(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("team/invite")
    Call<Void> inviteToTeam(
            @Header("Authorization") String token,
            @Field("user") String user
    );

    @FormUrlEncoded
    @POST("team/request_accept")
    Call<Void> acceptTeamRequest(
            @Header("Authorization") String token,
            @Field("idTeam") int idTeam
    );

    @FormUrlEncoded
    @POST("team/request_decline")
    Call<Void> declineTeamRequest(
            @Header("Authorization") String token,
            @Field("idTeam") int idTeam
    );

    @GET("team/requests")
    Call<List<TeamRequest>> getTeamRequests(
            @Header("Authorization") String token
    );
    //endregion

    //region Friends
    @GET("friends")
    Call<List<Friend>> getFriends(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("friends/request")
    Call<Void> sendFriendRequest(
            @Header("Authorization") String token,
            @Field("user") String user
    );

    @GET("friends/request")
    Call<List<FriendRequest>> getFriendRequests(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("friends/request_accept")
    Call<Void> acceptFriendRequest(
            @Header("Authorization") String token,
            @Field("userID") int userID
    );

    @FormUrlEncoded
    @POST("friends/request_decline")
    Call<Void> declineFriendRequest(
            @Header("Authorization") String token,
            @Field("userID") int userID
    );

    @FormUrlEncoded
    @POST("friends/delete")
    Call<Void> deleteFriend(
            @Header("Authorization") String token,
            @Field("userID") int userID
    );
    //chat
    @FormUrlEncoded
    @POST("messages")
    Call<List<Message>> getMessagesFromFriend(
            @Header("Authorization") String token,
            @Field("user") int user
    );

    @GET("messages/team")
    Call<List<Message>> getMessagesFromTeam(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("messages/competition")
    Call<List<Message>> getMessagesFromCompetition(
            @Field("chat") int chat
    );

    //endregion

    //region USER
    @FormUrlEncoded
    @POST("user/password")
    Call<Void> changePassword(
            @Header("Authorization") String token,
            @Field("actualPassword") String actualPassword,
            @Field("newPassword") String newPassword
    );

    @FormUrlEncoded
    @POST("user/status")
    Call<Void> changeStatus(
            @Header("Authorization") String token,
            @Field("status") String status
    );

    @GET("user/avatars")
    Call<List<String>> getURLAvatars();

    @FormUrlEncoded
    @POST("user/avatar")
    Call<Void> changeAvatar(
            @Header("Authorization") String token,
            @Field("avatar") String avatar
    );

    //endregion

    //region COMPETITION

    @GET("competition")
    Call<List<Competition>> getCompetitionsOpen(
            @Header("Authorization") String token,
            @QueryMap Map<String, String> options
    );

    @FormUrlEncoded
    @POST("competition/create")
    Call<Void> createCompetition(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("description") String description,
            @Field("videogame") String videogame,
            @Field("type") String type,
            @Field("isPrivate") int isPrivate,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("competition/join")
    Call<Void> joinToCompetition(
            @Header("Authorization") String token,
            @Field("idCompetition") int competition,
            @Field("password") String password
    );
    //endregion

    //region MyCompetitions
    @GET("owncompetition")
    Call<List<MyCompetition>> getCompetitionsJoined(
            @Header("Authorization") String token,
            @QueryMap Map<String, String> options
    );

    @FormUrlEncoded
    @POST("owncompetition/report")
    Call<Void> reportCompetition(
            @Header("Authorization") String token,
            @Field("idCompetition") int competition,
            @Field("description") String description
            );


    @FormUrlEncoded
    @POST("owncompetition/description")
    Call<Void> editDescription(
            @Field("idCompetition") int competition,
            @Field("description") String description
    );

    @FormUrlEncoded
    @POST("owncompetition/nextround")
    Call<ResponseConfrontation> nextRound(
            @Header("Authorization") String token,
            @Field("competition") int competition
    );

    @GET("owncompetition/info/{id}")
    Call<List<Round>> getConfrontations(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("owncompetition/winner")
    Call<Void> setWinner(
            @Header("Authorization") String token,
            @Field("competition") int competition,
            @Field("round") int round,
            @Field("type") String type,
            @Field("winner") String winner,
            @Field("playerone") String playerone,
            @Field("playertwo") String playertwo

    );

    @GET("owncompetition/players/{id}")
    Call<List<Player>> getPlayers(
            @Path("id") int id
    );

    //Si la competición no se trata de una liga devolverá 400
    @GET("owncompetition/table/{id}")
    Call<List<PlayerPoint>> getPoints(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("owncompetition/cancell")
    Call<Void> cancellCompetition(
            @Field("competition") int competition
    );

    //endregion

    //region VIDEOGAMES

    @GET("videogames/individual")
    Call<List<Videogame>> getAllVideogamesIndividual();

    @GET("videogames/team")
    Call<List<Videogame>> getAllVideogamesTeam();

    @FormUrlEncoded
    @POST("videogames/proposal")
    Call<Void> sendProporsal(
            @Field("name") String name,
            @Field("information") String information,
            @Field("team") Boolean team,
            @Field("individual") Boolean individual,
            @Field("customGames") Boolean custom
    );

    //endregion

}


