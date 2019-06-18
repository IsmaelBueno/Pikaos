package com.example.ismael.pikaos.data.dao.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.ismael.pikaos.data.model.local.CupPlayersLocal;

import java.util.ArrayList;

public class CupPlayersLocalDao {

    public boolean add(long id,String name,boolean removed){
        try{
            SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PikaosContract.CupPlayersEntry.COLUMN_ID,id);
            contentValues.put(PikaosContract.CupPlayersEntry.COLUMN_NAME,name);
            contentValues.put(PikaosContract.CupPlayersEntry.COLUMN_REMOVED,removed);
            sqLiteDatabase.insert(PikaosContract.CupPlayersEntry.TABLE_NAME,null,contentValues);

            PikaosOpenHelper.getInstance().closeDataBase();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    /**
     * Actualiza los jugadores que han perdido una ronda
     * @param idCompetition
     * @param loosersLastRound
     */
    public void updateLoosers(long idCompetition,ArrayList<String> loosersLastRound) {
        SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

        String whereClause = PikaosContract.CupPlayersEntry.COLUMN_ID+"=? AND "+PikaosContract.CupPlayersEntry.COLUMN_NAME+"=?";
        ContentValues contentValues = new ContentValues();
        contentValues.put(PikaosContract.CupPlayersEntry.COLUMN_REMOVED,true);

        for (int i = 0; i < loosersLastRound.size(); i++) {
            String[] whereArgs = new String[]{String.valueOf(idCompetition),loosersLastRound.get(i)};
            sqLiteDatabase.update(PikaosContract.CupPlayersEntry.TABLE_NAME,contentValues,whereClause,whereArgs);
        }

        PikaosOpenHelper.getInstance().closeDataBase();
    }

    /**
     * Devuelve los jugadores que no han sido elimnados
     * @param idCompetition
     * @return
     */
    public ArrayList<CupPlayersLocal> getAllNotRemoved(long idCompetition) {
        SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

        ArrayList<CupPlayersLocal> result = new ArrayList<>();

        String whereClause = PikaosContract.CupPlayersEntry.COLUMN_ID +"=? AND "+PikaosContract.CupPlayersEntry.COLUMN_REMOVED+"=0";
        String[] whereArgs = new String[]{String.valueOf(idCompetition)};

        Cursor cursor = sqLiteDatabase.query(PikaosContract.CupPlayersEntry.TABLE_NAME,PikaosContract.CupPlayersEntry.ALL_COLUMN,whereClause,whereArgs,null,null,null);

        if(cursor.moveToFirst()){
            do{
                result.add(new CupPlayersLocal(cursor.getLong(0),cursor.getString(1),Boolean.parseBoolean(cursor.getString(2))));
            }while (cursor.moveToNext());
        }

        PikaosOpenHelper.getInstance().closeDataBase();

        return result;
    }
}
