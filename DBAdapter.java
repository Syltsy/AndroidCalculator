package com.example.metropolia.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	
	public static final String KEY_ROWID = "_id";
    public static final String KEY_CURRENCY_NAME = "name";
    public static final String KEY_CURRENCY_RELATION = "relation";
    public static final String KEY_DATE = "date";    
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "ccalculator";
    private static final String DATABASE_TABLE = "currencies";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE =
        "create table currencies (_id integer primary key autoincrement, "
        + "name text not null, relation text not null, " 
        + "date text not null);";
        
    private final Context context;  
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
                              int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                  + " to "
                  + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }
  //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    
    public DBAdapter openForReading() throws SQLException 
    {
        db = DBHelper.getReadableDatabase();
        return this;
    }
    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a title into the database---
    public long insertCurrency(String name, String relation, String date) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CURRENCY_NAME, name);
        initialValues.put(KEY_CURRENCY_RELATION, relation);
        initialValues.put(KEY_DATE, date);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteTitle(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    // delete all the rows, returns number of rows
    public int deleteAll() // 
    {
    	return db.delete(DATABASE_TABLE, null, null);
    }
    //---retrieves all the titles---
    public Cursor getAllTitles() 
    {
        return db.query(DATABASE_TABLE, new String[] {
        		KEY_ROWID, 
        		KEY_CURRENCY_NAME,
        		KEY_CURRENCY_RELATION,
                KEY_DATE}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }

    //---retrieves a particular title---
    //public Cursor getTitle(long rowId) throws SQLException 
    /*public Cursor getTitle(String currencyName) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_CURRENCY_NAME,
                		KEY_CURRENCY_RELATION,
                        KEY_DATE
                		}, 
                		KEY_CURRENCY_NAME + "=" + currencyName, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    } */

    public Cursor getTitle(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_CURRENCY_NAME,
                		KEY_CURRENCY_RELATION,
                        KEY_DATE
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //---updates a currency---
    public boolean updateTitle(long rowId, String name, 
    String relation, String date) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_CURRENCY_NAME, name);
        args.put(KEY_CURRENCY_RELATION, relation);
        args.put(KEY_DATE, date);
        return db.update(DATABASE_TABLE, args, 
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
}
