package com.example.scanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database_name";
//    private static final String TABLE_NAME = "VACCINE";

    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "create table WEIGHING(tagNO INTEGER, weight TEXT,dateWeighed TEXT)";
        db.execSQL(createTable1);
        String createTable0 = "create table FLOCK(tagNO INTEGER PRIMARY KEY, gender CHAR,status TEXT)";
        db.execSQL(createTable0);
        String createTable = "create table VACCINE (tagNO INTEGER, date TEXT, medicine TEXT)";
        db.execSQL(createTable);
        String createTable2 = "create table LAMBING(tagNO INTEGER, noOfLambs TEXT,dateLambed TEXT)";
        db.execSQL(createTable2);

        db.execSQL("INSERT INTO WEIGHING (tagNo, weight, dateWeighed) VALUES (01678,'40','04/05/2020 19:10')");
        db.execSQL("INSERT INTO WEIGHING (tagNo, weight, dateWeighed) VALUES (01265,'35','04/05/2020 19:11')");
        db.execSQL("INSERT INTO WEIGHING (tagNo, weight, dateWeighed) VALUES (03684,'50','04/05/2020 19:12')");
        db.execSQL("INSERT INTO WEIGHING (tagNo, weight, dateWeighed) VALUES (02354,'44','04/05/2020 19:14')");
        db.execSQL("INSERT INTO WEIGHING (tagNo, weight, dateWeighed) VALUES (03265,'46','04/05/2020 19:14')");

        db.execSQL("INSERT INTO FLOCK (tagNo, gender, status) VALUES (03265,'M','Alive')");
        db.execSQL("INSERT INTO FLOCK (tagNo, gender, status) VALUES (01234,'M','Alive')");
        db.execSQL("INSERT INTO FLOCK (tagNo, gender, status) VALUES (12365,'F','Alive')");
        db.execSQL("INSERT INTO FLOCK (tagNo, gender, status) VALUES (13657,'F','Dead')");
        db.execSQL("INSERT INTO FLOCK (tagNo, gender, status) VALUES (12145,'F','Alive')");

        db.execSQL("INSERT INTO VACCINE (tagNo, date, medicine) VALUES (12145,'04/05/2020 19:10','SpotOn, Flukiver')");
        db.execSQL("INSERT INTO VACCINE (tagNo, date, medicine) VALUES (12658,'04/05/2020 19:10','SpotOn, Flukiver')");
        db.execSQL("INSERT INTO VACCINE (tagNo, date, medicine) VALUES (01468,'04/05/2020 19:10','SpotOn, Flukiver')");
        db.execSQL("INSERT INTO VACCINE (tagNo, date, medicine) VALUES (01678,'04/05/2020 19:10','SpotOn, Flukiver')");
        db.execSQL("INSERT INTO VACCINE (tagNo, date, medicine) VALUES (13256,'04/05/2020 19:10','SpotOn, Flukiver')");

        db.execSQL("INSERT INTO LAMBING (tagNo, noOfLambs, dateLambed) VALUES (13256,'2','17/03/2020 19:10')");
        db.execSQL("INSERT INTO LAMBING (tagNo, noOfLambs, dateLambed) VALUES (13652,'2','20/03/2020 19:10')");
        db.execSQL("INSERT INTO LAMBING (tagNo, noOfLambs, dateLambed) VALUES (01747,'3','20/03/2020 10:13')");
        db.execSQL("INSERT INTO LAMBING (tagNo, noOfLambs, dateLambed) VALUES (01498,'1','19/03/2020 14:30')");
        db.execSQL("INSERT INTO LAMBING (tagNo, noOfLambs, dateLambed) VALUES (36541,'1','30/03/2020 20:10')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS WEIGHING");
        db.execSQL("DROP TABLE IF EXISTS FLOCK");
        db.execSQL("DROP TABLE IF EXISTS VACCINE");
        db.execSQL("DROP TABLE IF EXISTS LAMBING");
        onCreate(db);
    }

    public boolean addWeight(String tagnNo, int weight, String date){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("tagNO",tagnNo);
        contentValues.put("weight",weight);
        contentValues.put("dateWeighed",date);

        sqLiteDatabase.insert("WEIGHING",null,contentValues);
        return true;
    }

    public ArrayList getAllText(String table){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + table,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            if(table.contains("WEIGHING")){
                arrayList.add("Tag No. " + cursor.getString
                        (cursor.getColumnIndex("tagNO")) +"\n \n Weight(kg) "
                        + cursor.getString(cursor.getColumnIndex("weight"))
                        +" on "
                        + cursor.getString(cursor.getColumnIndex("dateWeighed"))
                );
                cursor.moveToNext();
            }
            else if(table.equals("FLOCK")){
                arrayList.add("Tag No: " + cursor.getString
                        (cursor.getColumnIndex("tagNO")) +"\n \n Gender:"
                        + cursor.getString(cursor.getColumnIndex("gender"))
                        +"\t\t Status: "
                        + cursor.getString(cursor.getColumnIndex("status"))
                );
                cursor.moveToNext();
            }
            else if(table.equals("LAMBING")){
                arrayList.add("Tag No: " + cursor.getString
                        (cursor.getColumnIndex("tagNO")) +"\n \n # of lambs:"
                        + cursor.getString(cursor.getColumnIndex("noOfLambs"))
                        +" on "
                        + cursor.getString(cursor.getColumnIndex("dateLambed"))
                );
                cursor.moveToNext();
            }
            else if(table.equals("VACCINE")){
                arrayList.add("Tag No: " + cursor.getString
                        (cursor.getColumnIndex("tagNO")) +"\n \n Dosed with :"
                        + cursor.getString(cursor.getColumnIndex("medicine"))
                        +" on "
                        + cursor.getString(cursor.getColumnIndex("date"))
                );
                cursor.moveToNext();
            }
            else{
                arrayList.add("Tag No. " + cursor.getString(cursor.getColumnIndex("tagNO")));
                cursor.moveToNext();
            }
        }
        return arrayList;
    }
    public boolean addTable(String tableName, String other){
        String createTable = "create table " + tableName
                + " (tagNo INTEGER PRIMARY KEY,"+ other +" TEXT)";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(createTable);

        return true;
    }
    public ArrayList getAllTables(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("name")));
            cursor.moveToNext();
        }
        arrayList.remove("android_metadata");
        return arrayList;
    }
}
