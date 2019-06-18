package com.example.ismael.pikaos.data.dao.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;
import com.example.ismael.pikaos.data.model.local.CupRoundLocal;

import java.util.ArrayList;

public class CupConfrontationLocalDao {
    public boolean add(long id,int nRound,String playerOne,String playerTwo,String winner){
        try{
            SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PikaosContract.CupConfrontationsEntry.COLUMN_ID,id);
            contentValues.put(PikaosContract.CupConfrontationsEntry.COLUMN_NROUND,nRound);
            contentValues.put(PikaosContract.CupConfrontationsEntry.COLUMN_PLAYERONE,playerOne);
            contentValues.put(PikaosContract.CupConfrontationsEntry.COLUMN_PLAYERTWO,playerTwo);
            contentValues.put(PikaosContract.CupConfrontationsEntry.COLUMN_WINNER,winner);
            sqLiteDatabase.insert(PikaosContract.CupConfrontationsEntry.TABLE_NAME,null,contentValues);

            PikaosOpenHelper.getInstance().closeDataBase();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public ArrayList<CupConfrontationsLocal> getAll(long idCompetition, int round) {

        ArrayList<CupConfrontationsLocal> result = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

        //Sentencia parametrizada
        String whereClause = "id = ? AND nRound = ?";
        String[] whereArgs = new String[]{String.valueOf(idCompetition),String.valueOf(round)};

        Cursor cursor = sqLiteDatabase.query(PikaosContract.CupConfrontationsEntry.TABLE_NAME,PikaosContract.CupConfrontationsEntry.ALL_COLUMN,whereClause,
                whereArgs,null,null,null);

        //1. Si hay datos
        if(cursor.moveToFirst()){
            do {
                CupConfrontationsLocal tmpConfrontation = new CupConfrontationsLocal(
                        cursor.getLong(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));

                result.add(tmpConfrontation);

            } while (cursor.moveToNext());
        }

        PikaosOpenHelper.getInstance().closeDataBase();

        return result;
    }

    /**
     * Actualiza el ganador de un enfrentamiento
     * @param idCup
     * @param nRound
     * @param playerOne
     * @param playerTwo
     * @param winner
     * @return
     */
    public boolean updateWinner(long idCup, int nRound, String playerOne, String playerTwo, String winner) {
        SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

        Log.e("testing",winner);

        ContentValues contentValues = new ContentValues();
        contentValues.put(PikaosContract.CupConfrontationsEntry.COLUMN_WINNER,winner);
        String whereClause = "id=? AND nRound=? AND playerOne = ? AND playerTwo = ?";
        String[] whereArgs = new String[]{String.valueOf(idCup),String.valueOf(nRound),playerOne,playerTwo};

        int result = sqLiteDatabase.update(PikaosContract.CupConfrontationsEntry.TABLE_NAME,contentValues,whereClause,whereArgs);

        PikaosOpenHelper.getInstance().closeDataBase();

        if(result == 1)
            return true;
        else
            return false;
    }
}
