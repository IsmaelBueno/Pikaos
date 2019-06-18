package com.example.ismael.pikaos.data.dao.local;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ismael.pikaos.UI.PikaosApplication;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase que sólo contien CONSTANTES para definir la  base de datos, las tablas,
 * las columnas de las tablas, los datos a insertar
 */
public final class PikaosOpenHelper extends SQLiteOpenHelper {

    private static PikaosOpenHelper pikaosOpenHelper;
    private SQLiteDatabase sqLiteDataBase;
    private AtomicInteger openCounter = new AtomicInteger(0);

    //TODO test
    private PikaosOpenHelper() {
        super(PikaosApplication.getAppContext(), PikaosContract.DATABASE_NAME, null, PikaosContract.DATABASE_VERSION);
    }

    public synchronized static PikaosOpenHelper getInstance(){
        if(pikaosOpenHelper==null)
            pikaosOpenHelper = new PikaosOpenHelper();

        return pikaosOpenHelper;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_key=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            db.execSQL(PikaosContract.CompetitionEntry.SQL_CREATE_ENTRIES);
            //db.execSQL(PikaosContract.CompetitionEntry.SQL_INSERT_ENTRIES);
            db.execSQL(PikaosContract.CupEntry.SQL_CREATE_ENTRIES);
            db.execSQL(PikaosContract.LeagueEntry.SQL_CREATE_ENTRIES);
            db.execSQL(PikaosContract.CupRoundsEntry.SQL_CREATE_ENTRIES);
            db.execSQL(PikaosContract.CupPlayersEntry.SQL_CREATE_ENTRIES);
            db.execSQL(PikaosContract.CupConfrontationsEntry.SQL_CREATE_ENTRIES);
            db.execSQL(PikaosContract.LeagueDaysLeagueEntry.SQL_CREATE_ENTRIES);
            db.execSQL(PikaosContract.LeaguePlayersEntry.SQL_CREATE_ENTRIES);
            db.execSQL(PikaosContract.LeagueConfrontationsEntry.SQL_CREATE_ENTRIES);
            db.setTransactionSuccessful();
        }catch (SQLException e){

        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.beginTransaction();

            //Código de borrado
            db.execSQL(PikaosContract.CompetitionEntry.SQL_DELETE_ENTRIES);
            db.execSQL(PikaosContract.CupEntry.SQL_DELETE_ENTRIES);
            db.execSQL(PikaosContract.LeagueEntry.SQL_DELETE_ENTRIES);
            db.execSQL(PikaosContract.CupRoundsEntry.SQL_DELETE_ENTRIES);
            db.execSQL(PikaosContract.CupPlayersEntry.SQL_DELETE_ENTRIES);
            db.execSQL(PikaosContract.CupConfrontationsEntry.SQL_DELETE_ENTRIES);
            db.execSQL(PikaosContract.LeaguePlayersEntry.SQL_DELETE_ENTRIES);
            db.execSQL(PikaosContract.LeagueDaysLeagueEntry.SQL_DELETE_ENTRIES);
            db.execSQL(PikaosContract.LeagueConfrontationsEntry.SQL_DELETE_ENTRIES);

            onCreate(db);
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.e("BBDD",e.getMessage());
        }finally {
            db.endTransaction();
        }
    }

    //Los dos métodos para abrir la BBDD y cerrar
    public synchronized SQLiteDatabase openDataBase(){
        if (openCounter.incrementAndGet() == 1) {
            sqLiteDataBase = getWritableDatabase();
        }
        return sqLiteDataBase;
    }

    public synchronized void closeDataBase(){
        if(openCounter.decrementAndGet()==0)
            sqLiteDataBase.close();
    }
}
