package com.example.ismael.pikaos.data.dao.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.ismael.pikaos.data.model.local.CupLocal;

public class CupLocalDao {

    public boolean add(long id,int nRounds){
        try {
            SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(PikaosContract.CupEntry.COLUMN_ID,id);
            contentValues.put(PikaosContract.CupEntry.COLUMN_NROUNDS,nRounds);

            sqLiteDatabase.insert(PikaosContract.CupEntry.TABLE_NAME,null,contentValues);

            PikaosOpenHelper.getInstance().closeDataBase();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public CupLocal get(long id) {
        SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

        String whereClause = PikaosContract.CupEntry.COLUMN_ID + "= ?";
        String [] whereArgs = new String[]{String.valueOf(id)};

        Cursor cursor = sqLiteDatabase.query(PikaosContract.CupEntry.TABLE_NAME,PikaosContract.CupEntry.ALL_COLUMN,whereClause,whereArgs,null,null,null);
        cursor.moveToFirst();

        CupLocal result = new CupLocal(cursor.getLong(0),cursor.getInt(1));

        return result;
    }
}
