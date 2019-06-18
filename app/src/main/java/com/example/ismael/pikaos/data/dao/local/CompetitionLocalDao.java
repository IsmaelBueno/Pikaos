package com.example.ismael.pikaos.data.dao.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.data.model.local.CompetitionLocal;
import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CompetitionLocalDao {

    public ArrayList<CompetitionLocal> getAll(){
        SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

        ArrayList<CompetitionLocal> listResult = new ArrayList<>();

        //Sentencia parametrizada
        Cursor cursor = sqLiteDatabase.query(PikaosContract.CompetitionEntry.TABLE_NAME,PikaosContract.CompetitionEntry.ALL_COLUMN,null,
                null,null,null,PikaosContract.CompetitionEntry.DEFAULT_SORT);

        //1. Si hay datos
        if(cursor.moveToFirst()){
            do{
                CompetitionLocal tmpCompetitionLocal = new CompetitionLocal(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        Boolean.parseBoolean(String.valueOf(cursor.getInt(3))),
                        cursor.getString(4),
                        cursor.getString(5));

                listResult.add(tmpCompetitionLocal);

            }while(cursor.moveToNext());
        }

        PikaosOpenHelper.getInstance().closeDataBase();

        return listResult;
    }

    public boolean delete(long idCompetition) {
        try {
            SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();
            String[] whereArgs = new String[]{String.valueOf(idCompetition)};

            sqLiteDatabase.delete(PikaosContract.CompetitionEntry.TABLE_NAME, PikaosContract.CompetitionEntry.COLUMN_ID+"=?", whereArgs);

            PikaosOpenHelper.getInstance().closeDataBase();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public long add(String name,String type,String description){
        try{
            SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

            String curentDate= new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            //Por defecto una competición al crearse no está finalizada
            ContentValues contentValues = new ContentValues();
            contentValues.put(PikaosContract.CompetitionEntry.COLUMN_NAME,name);
            contentValues.put(PikaosContract.CompetitionEntry.COLUMN_TYPE,type);
            contentValues.put(PikaosContract.CompetitionEntry.COLUMN_FINISHED,0);
            contentValues.put(PikaosContract.CompetitionEntry.COLUMN_NOTE,description);
            contentValues.put(PikaosContract.CompetitionEntry.COLUMN_DATE,curentDate);

            long id  = sqLiteDatabase.insert(PikaosContract.CompetitionEntry.TABLE_NAME,null,contentValues);


            PikaosOpenHelper.getInstance().closeDataBase();
            return id;

        }catch (SQLiteException e){
            return -1;
        }
    }


    /**
     * Crea las competicinoes en un modelo especial que hereda de competitionsLocal para mayor facilidad
     * a la hora de obtener los datos
     * @return
     */
    public ArrayList<CompetitionLocalView> getAllView(){
        SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

        ArrayList<CompetitionLocalView> result = new ArrayList<>();

        //Sentencia parametrizada
        Cursor cursor = sqLiteDatabase.query(PikaosContract.CompetitionEntry.TABLE_NAME,PikaosContract.CompetitionEntry.ALL_COLUMN,null,
                null,null,null,PikaosContract.CompetitionEntry.DEFAULT_SORT);

        //1. Recoger los datos de la competición salvo el nPlayers y nameRound
        boolean finished = false;
        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(3).equals("1"))
                    finished = true;
                else
                    finished = false;


                CompetitionLocalView tmpCompetitionLocal = new CompetitionLocalView(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        finished,
                        cursor.getString(4),
                        cursor.getString(5));

                result.add(tmpCompetitionLocal);

            } while (cursor.moveToNext());
        }


        //2. Recoger el número de jugadores de cada competición
        long nPlayers;
        String id;
        for (int i = 0; i < result.size(); i++) {
            id = String.valueOf(result.get(i).getId());
            nPlayers = DatabaseUtils.queryNumEntries(sqLiteDatabase, PikaosContract.CupPlayersEntry.TABLE_NAME,
                    "id = ?", new String[]{id});
            result.get(i).setnPlayers(nPlayers);
        }

        //TODO poner esto de forma más eficiente
        //3. Recoger el nombre la ronda actual de la competición
        String nameRound;
        String[] column =new String[]{ "roundName"};
        String whereClause = "id = ? and nround = (select max(nround) from cup_rounds where id = ?)";
        String[] whereArgs;

        for (int i = 0; i < result.size() ; i++) {
            id = String.valueOf(result.get(i).getId());
            whereArgs = new String[]{id,id};
            cursor = sqLiteDatabase.query(PikaosContract.CupRoundsEntry.TABLE_NAME,column,whereClause,whereArgs,null,null,null);
            cursor.moveToFirst();
            nameRound = cursor.getString(0);
            result.get(i).setNameRound(nameRound);
        }

        PikaosOpenHelper.getInstance().closeDataBase();
        return result;
    }

    /**
     * Cambia la columna que indica el estado de una competición a acabado
     * @param idCompetition
     */
    public void updateFinished(long idCompetition) {
        SQLiteDatabase sqLiteDatabase = PikaosOpenHelper.getInstance().openDataBase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PikaosContract.CompetitionEntry.COLUMN_FINISHED,true);

        String whereClause = PikaosContract.CompetitionEntry.COLUMN_ID+"=?";
        String[] whereArgs = new String[]{String.valueOf(idCompetition)};

        sqLiteDatabase.update(PikaosContract.CompetitionEntry.TABLE_NAME,contentValues,whereClause,whereArgs);

        PikaosOpenHelper.getInstance().closeDataBase();
    }
}
