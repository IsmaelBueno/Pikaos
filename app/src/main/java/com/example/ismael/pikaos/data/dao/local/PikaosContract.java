package com.example.ismael.pikaos.data.dao.local;

import android.provider.BaseColumns;

public final class PikaosContract {
    public static final String DATABASE_NAME = "local.db";
    public static final int DATABASE_VERSION = 7;


    //TODO COMPETITIONS
    public static class CompetitionEntry implements BaseColumns {

        //Nombre de la tabla
        public static final String TABLE_NAME = "competition";

        //Columnas de la tabla
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_FINISHED = "finished";
        public static final String COLUMN_NOTE = "note";
        public static final String COLUMN_DATE = "date";

        //Orden de la tabla
        public static final String DEFAULT_SORT = "date";

        //Array con todas las columnas
        public static final String[] ALL_COLUMN = new String[]{
                COLUMN_ID, COLUMN_NAME, COLUMN_TYPE,
                COLUMN_FINISHED, COLUMN_NOTE, COLUMN_DATE
        };

        //-------CREAR TABLA-----
        public static final String SQL_CREATE_ENTRIES = String.format
                ("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "%s TEXT NOT NULL," +
                                "%s TEXT NOT NULL," +
                                "%s INTEGER NOT NULL," +
                                "%s TEXT NULL," +
                                "%s DATE NOT NULL)"
                        , TABLE_NAME,
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_TYPE,
                        COLUMN_FINISHED,
                        COLUMN_NOTE,
                        COLUMN_DATE);

        //------ ELIMINAR TABLA -------
        public static final String SQL_DELETE_ENTRIES = String.format
                ("DROP TABLE IF EXISTS %s", TABLE_NAME);

        //------ INSERTAR DATOS PRUEBA --------
        public static final String SQL_INSERT_ENTRIES = String.format
                ("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', %s, '%s', '%s')",
                        TABLE_NAME,
                        COLUMN_NAME,
                        COLUMN_TYPE,
                        COLUMN_FINISHED,
                        COLUMN_NOTE,
                        COLUMN_DATE,
                        "test cup lol",
                        "cup",
                        "0",
                        "Pues esto es la nota de la copa de lol",
                        "2019-02-02");
    }


    //TODO CUP
    public static class CupEntry implements BaseColumns {

        //Nombre de la tabla
        public static final String TABLE_NAME = "cup";

        //Columnas de la tabla
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NROUNDS = "nRounds";

        //Orden de la tabla
        public static final String DEFAULT_SORT = "id";

        //Array con todas las columnas
        public static final String[] ALL_COLUMN = new String[]{
                COLUMN_ID, COLUMN_NROUNDS
        };

        private static final String FOREIGN_KEYS = "FOREIGN KEY ("+COLUMN_ID+") REFERENCES "+
                CompetitionEntry.TABLE_NAME+" ("+CompetitionEntry.COLUMN_ID+") ON UPDATE CASCADE ON DELETE CASCADE";

        //-------CREAR TABLA-----
        public static final String SQL_CREATE_ENTRIES = String.format
                ("CREATE TABLE %s (%s INTEGER PRIMARY KEY," +
                                "%s INTEGER NULL,%s)"
                        , TABLE_NAME,
                        COLUMN_ID,
                        COLUMN_NROUNDS,
                        FOREIGN_KEYS);

        //------ ELIMINAR TABLA -------
        public static final String SQL_DELETE_ENTRIES = String.format
                ("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }

    //TODO LEAGUE
    public static class LeagueEntry implements BaseColumns {

        //Nombre de la tabla
        public static final String TABLE_NAME = "league";

        //Columnas de la tabla
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NLEAGUEDAYS = "nLeagueDays";

        private static final String FOREIGN_KEYS = "FOREIGN KEY ("+COLUMN_ID+") REFERENCES "+
                CompetitionEntry.TABLE_NAME+" ("+CompetitionEntry.COLUMN_ID+") ON UPDATE CASCADE ON DELETE CASCADE";

        //Orden de la tabla
        public static final String DEFAULT_SORT = "id";

        //Array con todas las columnas
        public static final String[] ALL_COLUMN = new String[]{
                COLUMN_ID, COLUMN_NLEAGUEDAYS
        };

        //-------CREAR TABLA-----
        public static final String SQL_CREATE_ENTRIES = String.format
                ("CREATE TABLE %s (%s INTEGER PRIMARY KEY," +
                                "%s INTEGER NULL,%s)"
                        , TABLE_NAME,
                        COLUMN_ID,
                        COLUMN_NLEAGUEDAYS,
                        FOREIGN_KEYS);

        //------ ELIMINAR TABLA -------
        public static final String SQL_DELETE_ENTRIES = String.format
                ("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }


    //TODO CUP PLAYERS
    public static class CupPlayersEntry implements BaseColumns {

        //Nombre de la tabla
        public static final String TABLE_NAME = "cup_players";

        //Columnas de la tabla
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_REMOVED = "removed";

        //Orden de la tabla
        public static final String DEFAULT_SORT = "id";

        //Array con todas las columnas
        public static final String[] ALL_COLUMN = new String[]{
                COLUMN_ID, COLUMN_NAME,COLUMN_REMOVED
        };

        private static final String FOREIGN_KEYS = "FOREIGN KEY ("+COLUMN_ID+") REFERENCES "+
                CupEntry.TABLE_NAME+" ("+CupEntry.COLUMN_ID+") ON UPDATE CASCADE ON DELETE CASCADE";

        //-------CREAR TABLA-----
        public static final String SQL_CREATE_ENTRIES = String.format
                ("CREATE TABLE %s (%s INTEGER," +
                                "%s TEXT NOT NULL, %s INTEGER NOT NULL, PRIMARY KEY(%s,%s),%s)"
                        , TABLE_NAME,
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_REMOVED,
                        COLUMN_ID,
                        COLUMN_NAME,
                        FOREIGN_KEYS);

        //------ ELIMINAR TABLA -------
        public static final String SQL_DELETE_ENTRIES = String.format
                ("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }


    //TODO LEAGUE PLAYERS
    public static class LeaguePlayersEntry implements BaseColumns {

        //Nombre de la tabla
        public static final String TABLE_NAME = "league_players";

        //Columnas de la tabla
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_POINTS = "points";

        //Orden de la tabla
        public static final String DEFAULT_SORT = "id";

        //Array con todas las columnas
        public static final String[] ALL_COLUMN = new String[]{
                COLUMN_ID, COLUMN_NAME,COLUMN_POINTS
        };

        private static final String FOREIGN_KEYS = "FOREIGN KEY ("+COLUMN_ID+") REFERENCES "+
                LeagueEntry.TABLE_NAME+" ("+LeagueEntry.COLUMN_ID+") ON UPDATE CASCADE ON DELETE CASCADE";

        //-------CREAR TABLA-----
        public static final String SQL_CREATE_ENTRIES = String.format
                ("CREATE TABLE %s (%s INTEGER," +
                                "%s TEXT NOT NULL, %s INTEGER NOT NULL, PRIMARY KEY(%s,%s),%s)"
                        , TABLE_NAME,
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_POINTS,
                        COLUMN_ID,
                        COLUMN_NAME,
                        FOREIGN_KEYS);

        //------ ELIMINAR TABLA -------
        public static final String SQL_DELETE_ENTRIES = String.format
                ("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }


    //TODO CUP ROUNDS
    public static class CupRoundsEntry implements BaseColumns {

        //Nombre de la tabla
        public static final String TABLE_NAME = "cup_rounds";

        //Columnas de la tabla
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NROUND = "nRound";
        public static final String COLUMN_ROUNDNAME = "roundName";

        //Orden de la tabla
        public static final String DEFAULT_SORT = "nRound";

        //Array con todas las columnas
        public static final String[] ALL_COLUMN = new String[]{
                COLUMN_ID, COLUMN_NROUND,COLUMN_ROUNDNAME
        };

        private static final String FOREIGN_KEYS = "FOREIGN KEY ("+COLUMN_ID+") REFERENCES "+
                CupEntry.TABLE_NAME+" ("+CupEntry.COLUMN_ID+") ON UPDATE CASCADE ON DELETE CASCADE";

        //-------CREAR TABLA-----
        public static final String SQL_CREATE_ENTRIES = String.format
                ("CREATE TABLE %s (%s INTEGER," +
                                "%s INTEGER NULL, %s TEXT NULL, PRIMARY KEY(%s,%s),%s)"
                        , TABLE_NAME,
                        COLUMN_ID,
                        COLUMN_NROUND,
                        COLUMN_ROUNDNAME,
                        COLUMN_ID,
                        COLUMN_NROUND,
                        FOREIGN_KEYS);

        //------ ELIMINAR TABLA -------
        public static final String SQL_DELETE_ENTRIES = String.format
                ("DROP TABLE IF EXISTS %s", TABLE_NAME);

    }


    //TODO LEAGUE LAGUEDAYS
    public static class LeagueDaysLeagueEntry implements BaseColumns {

        //Nombre de la tabla
        public static final String TABLE_NAME = "league_leagueDays";

        //Columnas de la tabla
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LEAGUEDAY = "leagueDay";

        //Orden de la tabla
        public static final String DEFAULT_SORT = "id";

        //Array con todas las columnas
        public static final String[] ALL_COLUMN = new String[]{
                COLUMN_ID, COLUMN_LEAGUEDAY
        };

        private static final String FOREIGN_KEYS = "FOREIGN KEY ("+COLUMN_ID+") REFERENCES "+
                LeagueEntry.TABLE_NAME+" ("+LeagueEntry.COLUMN_ID+") ON UPDATE CASCADE ON DELETE CASCADE";

        //-------CREAR TABLA-----
        public static final String SQL_CREATE_ENTRIES = String.format
                ("CREATE TABLE %s (%s INTEGER," +
                                "%s INTEGER NOT NULL,PRIMARY KEY(%s,%s),%s)"
                        , TABLE_NAME,
                        COLUMN_ID,
                        COLUMN_LEAGUEDAY,
                        COLUMN_ID,
                        COLUMN_LEAGUEDAY,
                        FOREIGN_KEYS);

        //------ ELIMINAR TABLA -------
        public static final String SQL_DELETE_ENTRIES = String.format
                ("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }


    //TODO CUP CONFRONTATIONS
    public static class CupConfrontationsEntry implements BaseColumns {

        //Nombre de la tabla
        public static final String TABLE_NAME = "cup_confrontations";

        //Columnas de la tabla
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NROUND = "nRound";
        public static final String COLUMN_PLAYERONE = "playerOne";
        public static final String COLUMN_PLAYERTWO = "playerTwo";
        public static final String COLUMN_WINNER = "winner";

        //Orden de la tabla
        public static final String DEFAULT_SORT = "id";

        //Array con todas las columnas
        public static final String[] ALL_COLUMN = new String[]{
                COLUMN_ID, COLUMN_NROUND,COLUMN_PLAYERONE,COLUMN_PLAYERTWO,COLUMN_WINNER
        };

        private static final String FOREIGN_KEYS = "FOREIGN KEY ("+COLUMN_ID+","+COLUMN_NROUND+") REFERENCES "+
                CupRoundsEntry.TABLE_NAME+" ("+CupRoundsEntry.COLUMN_ID+","+CupRoundsEntry.COLUMN_NROUND+") ON UPDATE CASCADE ON DELETE CASCADE";

        //-------CREAR TABLA-----
        public static final String SQL_CREATE_ENTRIES = String.format
                ("CREATE TABLE %s (%s INTEGER," +
                                "%s INTEGER NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NULL, " +
                                "PRIMARY KEY(%s,%s,%s,%s),%s)"
                        , TABLE_NAME,
                        COLUMN_ID,
                        COLUMN_NROUND,
                        COLUMN_PLAYERONE,
                        COLUMN_PLAYERTWO,
                        COLUMN_WINNER,
                        COLUMN_ID,
                        COLUMN_NROUND,
                        COLUMN_PLAYERONE,
                        COLUMN_PLAYERTWO,
                        FOREIGN_KEYS);

        //------ ELIMINAR TABLA -------
        public static final String SQL_DELETE_ENTRIES = String.format
                ("DROP TABLE IF EXISTS %s", TABLE_NAME);

    }


    //TODO LEAGUE CONFRONTATIONS
    public static class LeagueConfrontationsEntry implements BaseColumns {

        //Nombre de la tabla
        public static final String TABLE_NAME = "league_confrontations";

        //Columnas de la tabla
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LEAGUEDAY = "leagueDay";
        public static final String COLUMN_PLAYERONE = "playerOne";
        public static final String COLUMN_PLAYERTWO = "playerTwo";
        public static final String COLUMN_WINNER = "winner";

        //Orden de la tabla
        public static final String DEFAULT_SORT = "id";

        //Array con todas las columnas
        public static final String[] ALL_COLUMN = new String[]{
                COLUMN_ID, COLUMN_LEAGUEDAY,COLUMN_PLAYERONE,COLUMN_PLAYERTWO,COLUMN_WINNER
        };

        private static final String FOREIGN_KEYS = "FOREIGN KEY ("+COLUMN_ID+","+COLUMN_LEAGUEDAY+") REFERENCES "+
                LeagueDaysLeagueEntry.TABLE_NAME+" ("+LeagueDaysLeagueEntry.COLUMN_ID+","+LeagueDaysLeagueEntry.COLUMN_LEAGUEDAY+") ON UPDATE CASCADE ON DELETE CASCADE";

        //-------CREAR TABLA-----
        public static final String SQL_CREATE_ENTRIES = String.format
                ("CREATE TABLE %s (%s INTEGER," +
                                "%s INTEGER NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NULL, " +
                                "PRIMARY KEY(%s,%s,%s,%s),%s)"
                        , TABLE_NAME,
                        COLUMN_ID,
                        COLUMN_LEAGUEDAY,
                        COLUMN_PLAYERONE,
                        COLUMN_PLAYERTWO,
                        COLUMN_WINNER,
                        COLUMN_ID,
                        COLUMN_LEAGUEDAY ,
                        COLUMN_PLAYERONE,
                        COLUMN_PLAYERTWO,
                        FOREIGN_KEYS);

        //------ ELIMINAR TABLA -------
        public static final String SQL_DELETE_ENTRIES = String.format
                ("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }




}
