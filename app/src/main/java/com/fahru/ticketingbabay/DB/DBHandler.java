package com.fahru.ticketingbabay.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.fahru.ticketingbabay.object.ItemModel1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB = "database";
    private static final int DB_VERSION = 1;
    ArrayList<String> result;

    //for SQLite table
    protected final String TBL_LOCATION = "location";
    protected final String TBL_DEPARTMENT = "department";
    protected final String TBL_CAT = "category";
    protected final String TBL_SUB = "sub_category";
    protected final String TBL_ORDER_BY = "order_by";
    protected final String TBL_IT_SUPPORT = "it_support";

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
        String query_subCat = "CREATE TABLE "+TBL_SUB+" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, fk INTEGER NOT NULL)";
        db.execSQL(query_subCat);
        String inSub = "INSERT INTO "+TBL_SUB+
                " (name, fk) VALUES" +
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Map<Integer, String[]> getAllRecordModel1(String table){
        Map<Integer ,String[]> data = new HashMap<>();
        String query = "SELECT * FROM "+table+" ORDER BY name";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int i =0;
        while (!cursor.isAfterLast()){
            String[] value = {cursor.getString(0),cursor.getString(1)};
            data.put(i, value);
            i++;
            cursor.moveToNext();
        }
        return data;
    }

    public Map<Integer, String[]> getAllRecordModel2WhereId(String table, String id){
        Map<Integer ,String[]> data = new HashMap<>();
        String query = "SELECT * FROM "+table+" WHERE fk = "+id+" ORDER BY name";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int i =0;
        while (!cursor.isAfterLast()){
            String[] value = {cursor.getString(0),cursor.getString(1)};
            data.put(i, value);
            i++;
            cursor.moveToNext();
        }
        return data;
    }

    public void insertRecordModel1(String table, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", value);
        db.insert(table, null, values);
        db.close();
    }

    //inset with condition
    public void insertRecordModel2(String table, String value, String fk){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", value);
        values.put("fk", fk);
        db.insert(table, null, values);
        db.close();
    }

    public void updateRecord(String table, String id, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", value);

        db.update(table, values,"id=?", new String[]{id});
        db.close();
    }

    public void deleteRecord(String table, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, "id=?", new String[]{id});
        db.close();
    }

    public ArrayList<String> getRecordInOneColumnByField(String field, String table,String order){
        result = new ArrayList<>();
        String query = "SELECT "+field+" FROM "+table+" ORDER BY "+order;
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
