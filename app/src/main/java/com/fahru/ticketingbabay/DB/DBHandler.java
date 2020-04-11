package com.fahru.ticketingbabay.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB = "database";
    private static final int DB_VERSION = 1;
    ArrayList<String> result;

    //for SQLite table
    private final String TBL_LOCATION = "location";
    protected final String TBL_DEPARTMENT = "department";
    protected final String TBL_CAT = "category";
    protected final String TBL_SUB = "sub_category";
    protected final String TBL_ORDER_BY = "order_by";
    protected final String TBL_IT_SUPPORT = "it_support";
    protected final String TBL_STATUS= "status";

    public DBHandler(@Nullable Context context) {
        super(context, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Location
        String query_loc = "CREATE TABLE "+TBL_LOCATION+" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL)";
        db.execSQL(query_loc);
        String inLoc = "INSERT INTO "+TBL_LOCATION+
                " (name) VALUES" +
                " ('Soekarno Hatta')";
        db.execSQL(inLoc);
        //Department
        String query_dep = "CREATE TABLE "+TBL_DEPARTMENT+" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL)";
        db.execSQL(query_dep);
        String inDep = "INSERT INTO "+TBL_DEPARTMENT+
                " (name) VALUES" +
                " ('Human Capital')";
        db.execSQL(inDep);
        //Category
        String query_cat = "CREATE TABLE "+TBL_CAT+" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL)";
        db.execSQL(query_cat);
        String inCat = "INSERT INTO "+TBL_CAT+
                " (name) VALUES" +
                " ('Software Problem')";
        db.execSQL(inCat);
        //Sub Category
        String query_subCat = "CREATE TABLE "+TBL_SUB+" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, id_category INTEGER NOT NULL)";
        db.execSQL(query_subCat);
        String inSub = "INSERT INTO "+TBL_SUB+
                " (name, id_category) VALUES" +
                " ('Akses', 1)";
        db.execSQL(inSub);
        //Order By
        String query_ord = "CREATE TABLE "+TBL_ORDER_BY+" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL)";
        db.execSQL(query_ord);
        String inOrd = "INSERT INTO "+TBL_ORDER_BY+
                " (name) VALUES" +
                " ('Primary Phone')";
        db.execSQL(inOrd);
        //Assign By
        String query_assign = "CREATE TABLE "+TBL_IT_SUPPORT+" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL)";
        db.execSQL(query_assign);
        String inAssign = "INSERT INTO "+TBL_IT_SUPPORT+
                " (name) VALUES" +
                " ('Nuryadin')";
        db.execSQL(inAssign);
        //Status
//        String query_stat = "CREATE TABLE "+TBL_STATUS+" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL)";
//        db.execSQL(query_stat);
//        String inStat = "INSERT INTO "+TBL_STATUS+
//                " (name, id_category) VALUES" +
//                " ('Primary Phone')";
//        db.execSQL(inStat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String getRecord(String query){
        String result = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            result = cursor.getString(0);
            cursor.moveToNext();
        }
        return result;
    }

    public ArrayList<String> getAllRecordInOneColumn(String query){
        result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            for (int i=1; i<=cursor.getColumnCount(); i++){
                result.add(cursor.getString(i-1));
            }
            cursor.moveToNext();
        }
        return result;
    }
}
