package com.example.ismael.pikaos.UI;


import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String nameOfRoundCup(int actuallyRound,int nRounds){

        int roundsLeft = nRounds - actuallyRound;

        switch (roundsLeft){
            case 0:
                return "Final";
            case 1:
                return "Semifinal";
            case 2:
                return "Cuartos";
            case 3:
                return "Octavos";
            case 4:
                return "Dieciseisavos";
            case 5:
                return "Treintaidosavos";
            default:
                return String.valueOf(actuallyRound);
        }
    }

    //Calcula el número de rondas de una copa según la cantidad de jugadores que haya
    public static int calculateRoundsCup(int nJugadores){
        if(nJugadores<= 32 && nJugadores> 16)
            return  5;
        if(nJugadores<=16 && nJugadores>8)
            return  4;
        if(nJugadores<=8 && nJugadores >4)
            return  3;
        if(nJugadores<=4 && nJugadores >2)
            return  2;
        return  1;
    }


    public static String SHA256 (String text){

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(text.getBytes());
        byte[] digest = md.digest();

        return Base64.encodeToString(digest, Base64.DEFAULT);
    }

    public static String getTypeCompetitionFromDB(String typeCompetition){

        switch (typeCompetition){
            case "ind_cup":
                return "copa individual";
            case "ind_league":
                return "liga individual";
            case "team_cup":
                return "copa en equipo";
            case "team_league":
                return "liga en equipo";
            default:
                return "error cargando el tipo de competición";
        }
    }

    public static String getTypeCompetitionForDB(String typeCompetition){
        switch (typeCompetition){
            case "copa individual":
                return "ind_cup";
            case "liga individual":
                return "ind_league";
            case "copa en equipo":
                return "team_cup";
            case "liga en equipo":
                return "team_league";
                default:
                    return "error";
        }
    }

}
