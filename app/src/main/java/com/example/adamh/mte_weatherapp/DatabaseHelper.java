package com.example.adamh.mte_weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.database.Cursor;
import android.util.Log;

    public class DatabaseHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "cities.db";
        public static final String TABLE_NAME = "Cities_table";
        public static final String COLUMN_1 = "ID";
        public static final String COLUMN_2 = "name";

        private static final String TAG = "DatabaseHelper";

        public DatabaseHelper(Context context) {
            super(context, TABLE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_2 + " TEXT)";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean addData(String item) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_2, item);

            Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

            long result = db.insert(TABLE_NAME, null, contentValues);

            //if date as inserted incorrectly it will return -1
            if (result == -1) {
                return false;
            } else {
                return true;
            }

        }
        public Cursor getData(){
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME;
            Cursor data = db.rawQuery(query, null);
            return data;
        }

        public Cursor getItemID(String name){
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT " + COLUMN_1 + " FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_2 + " = '" + name + "'";
            Cursor data = db.rawQuery(query, null);
            return data;
        }
    }

