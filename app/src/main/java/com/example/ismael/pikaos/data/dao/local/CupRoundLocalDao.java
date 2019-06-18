package com.example.ismael.pikaos.data.dao.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.ismael.pikaos.data.model.local.CompetitionLocal;
import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;
import com.example.ismael.pikaos.data.model.local.CupRoundLocal;

import java.util.ArrayList;

public class CupRoundLocalDao {

    public boolean add(long id,int nRound,String nameRound){
        try{
            SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PikaosContract.CupRoundsEntry.COLUMN_ID,id);
            contentValues.put(PikaosContract.CupRoundsEntry.COLUMN_NROUND,nRound);
            contentValues.put(PikaosContract.CupRoundsEntry.COLUMN_ROUNDNAME,nameRound);
            sqLiteDatabase.insert(PikaosContract.CupRoundsEntry.TABLE_NAME,null,contentValues);

        PikaosOpenHelper.getInstance().closeDataBase();
        return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public ArrayList<CupRoundLocal> getAll(long id){

        ArrayList<CupRoundLocal> result = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

        //Sentencia parametrizada
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{id+""};

        Cursor cursor = sqLiteDatabase.query(PikaosContract.CupRoundsEntry.TABLE_NAME,PikaosContract.CupRoundsEntry.ALL_COLUMN,whereClause,
                whereArgs,null,null,PikaosContract.CupRoundsEntry.DEFAULT_SORT);

        //1. Si hay datos
        if(cursor.moveToFirst()){
            do {
                CupRoundLocal tmpRound = new CupRoundLocal(
                        cursor.getLong(0),
                        cursor.getInt(1),
                        cursor.getString(2));

                result.add(tmpRound);

            } while (cursor.moveToNext());
        }

        PikaosOpenHelper.getInstance().closeDataBase();

        return result;
    }
}
