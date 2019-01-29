package zw.co.vokers.vinceg.wpm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vince G on 28/11/2018
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "wpmLite";

    // Login table name
    private static final String TABLE_USER = "user";
    // Saved content table
    private static final String TABLE_SAVED_CONTENT = "saved_content";

    //public static ArrayList<Wins> mSaveWinList = new ArrayList<Wins>();


    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PAYNUMBER = "pay_number";
    private static final String KEY_PASSW = "password";
    private static final String KEY_JOBTITLE= "job_title";
    private static final String KEY_DEPT= "department";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EXTENSION = "extension";
    private static final String KEY_CREATED_AT = "created_at";

    //Saved content Table
    private static final String SAVE_ID = "id";
    private static final String SAVE_NAME = "name";
    private static final String SAVE_BIBLE_STUDY = "bible";
    private static final String SAVE_EMAIL = "email";
    private static final String SAVE_PASSWORD = "password";
    private static final String SAVE_PHONE_NUMBER = "no";
    private static final String SAVE_WINNER = "winner";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_PAYNUMBER + " TEXT,"
                + KEY_PASSW + " TEXT,"
                + KEY_JOBTITLE + " TEXT,"
                + KEY_DEPT + " TEXT,"
                + KEY_MOBILE + " TEXT,"
                + KEY_EXTENSION + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        //Creating save content table

        String CREATE_SAVE_TABLE = "CREATE TABLE " + TABLE_SAVED_CONTENT + "("
                + SAVE_ID + " INTEGER PRIMARY KEY,"
                + SAVE_NAME + " TEXT,"
                + SAVE_BIBLE_STUDY + " TEXT,"
                + SAVE_EMAIL + " TEXT UNIQUE,"
                + SAVE_PASSWORD+ " TEXT,"
                + SAVE_PHONE_NUMBER + " TEXT,"
                + SAVE_WINNER+ " TEXT" + ")";
        db.execSQL(CREATE_SAVE_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_CONTENT);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String paynumber,String passw,String jobtitle,String department, String email,String mobile,String extension, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_PAYNUMBER, paynumber);
        values.put(KEY_PASSW, passw);
        values.put(KEY_JOBTITLE, jobtitle);
        values.put(KEY_DEPT, department);
        values.put(KEY_EMAIL, email);
        values.put(KEY_MOBILE, mobile);
        values.put(KEY_EXTENSION, extension);
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Storing saved details in database
     * */
    public void addSavedContent(String name, String bible, String email, String password, String no,String winner) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SAVE_NAME, name); // Name
        values.put(SAVE_BIBLE_STUDY, bible);
        values.put(SAVE_EMAIL, email); // Email
        values.put(SAVE_PASSWORD, password); // Password
        values.put(SAVE_PHONE_NUMBER, no); // no
        values.put(SAVE_WINNER,winner);

        // Inserting Row
        long id = db.insert(TABLE_SAVED_CONTENT, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }



    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("pay_number", cursor.getString(3));
            user.put("password", cursor.getString(4));
            user.put("job_title", cursor.getString(5));
            user.put("department", cursor.getString(6));
            user.put("mobile", cursor.getString(7));
            user.put("extension", cursor.getString(8));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Getting saved data from database
     * */
    public HashMap<String, String> getSacedContentDetails() {
        HashMap<String, String> content = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_SAVED_CONTENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            content.put("name", cursor.getString(1));
            content.put("bible", cursor.getString(2));
            content.put("email", cursor.getString(3));
            content.put("password", cursor.getString(4));
            content.put("no", cursor.getString(5));
            content.put("winner", cursor.getString(6));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + content.toString());

        return content;
    }

    /**
     * Getting order data from database
     * */
    /*public ArrayList<Wins> getSavedDetails() {
        String selectQuery = "SELECT  * FROM " + TABLE_SAVED_CONTENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        if (cursor.moveToFirst()) {
            do {


                mSaveWinList.add(new Wins(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));

                // get the data into array, or class variable
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mSaveWinList;
    }*/

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteSavedContent() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_SAVED_CONTENT, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public boolean isContentEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean empty = true;
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM "+ TABLE_SAVED_CONTENT, null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt (0) == 0);
        }
        cur.close();

        return empty;
    }


}

