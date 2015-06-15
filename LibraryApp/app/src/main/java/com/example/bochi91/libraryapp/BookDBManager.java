package com.example.bochi91.libraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.RowId;
import java.sql.SQLException;

/*
This class has been given to me by my lecturer in college
 */

/**
 * Created by Bochi91 on 30/03/2015.
 */
public class BookDBManager {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_BORROWED = "borrowed";

    private static final String DATABASE_NAME = "Books";
    private static final String DATABASE_TABLE = "Book_info";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table Book_info (_id integer primary key autoincrement, " + "title text not null, " + "author text not null, " + "borrowed text not null);)";

    private final Context context;
    private MyHelper helper;
    private SQLiteDatabase db;

    public BookDBManager(Context ctx) {
        this.context = ctx;
        helper = new MyHelper(context);

    }

    private static class MyHelper extends SQLiteOpenHelper {
        MyHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);


        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_TITLE, "How to program in C#");
            initialValues.put(KEY_AUTHOR, "John Delaney");
            initialValues.put(KEY_BORROWED, "N");

            db.insert(DATABASE_TABLE, null, initialValues);

            initialValues.put(KEY_TITLE, "The Posix command line");
            initialValues.put(KEY_AUTHOR, "Harry Samson");
            initialValues.put(KEY_BORROWED, "N");

            db.insert(DATABASE_TABLE, null, initialValues);

            initialValues.put(KEY_TITLE, "Networking");
            initialValues.put(KEY_AUTHOR, "Gerry Harris");
            initialValues.put(KEY_BORROWED, "N");


            db.insert(DATABASE_TABLE, null, initialValues);

            initialValues.put(KEY_TITLE, "Lets get down to coding");
            initialValues.put(KEY_AUTHOR, "Larry Niel");
            initialValues.put(KEY_BORROWED, "N");

            db.insert(DATABASE_TABLE, null, initialValues);

            initialValues.put(KEY_TITLE, "SQL for dummies");
            initialValues.put(KEY_AUTHOR, "Lee Down");
            initialValues.put(KEY_BORROWED, "N");
            db.insert(DATABASE_TABLE, null, initialValues);

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public BookDBManager open() throws SQLException {
        db = helper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        return this;
    }

    public void close() {
        helper.close();
    }

    public long insertBook(String title, String author, String borrowed) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_AUTHOR, author);
        initialValues.put(KEY_BORROWED, borrowed);
        return db.insert(DATABASE_TABLE, null, initialValues);

    }

    public int deleteBook(int rowId) {

        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null);

    }

    public void createEntries() {


        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, "How to program in C#");
        initialValues.put(KEY_AUTHOR, "John Delaney");
        initialValues.put(KEY_BORROWED, "N");

        db.insert(DATABASE_TABLE, null, initialValues);

        initialValues.put(KEY_TITLE, "The Posix command line");
        initialValues.put(KEY_AUTHOR, "Harry Samson");
        initialValues.put(KEY_BORROWED, "N");

        db.insert(DATABASE_TABLE, null, initialValues);

        initialValues.put(KEY_TITLE, "The ways of the Android application");
        initialValues.put(KEY_AUTHOR, "Gerry Harris");
        initialValues.put(KEY_BORROWED, "N");


        db.insert(DATABASE_TABLE, null, initialValues);

        initialValues.put(KEY_TITLE, "Lets get down to coding");
        initialValues.put(KEY_AUTHOR, "Larry Niel");
        initialValues.put(KEY_BORROWED, "N");

        db.insert(DATABASE_TABLE, null, initialValues);

        initialValues.put(KEY_TITLE, "SQL for dummies");
        initialValues.put(KEY_AUTHOR, "Lee Down");
        initialValues.put(KEY_BORROWED, "N");
        db.insert(DATABASE_TABLE, null, initialValues);

    }

    public Cursor getAllBooks() {
        return db.query(DATABASE_TABLE, new String[]
                        {
                                KEY_ROWID,
                                KEY_TITLE,
                                KEY_AUTHOR,
                                KEY_BORROWED
                        },
                null, null, null, null, null);
    }

    public Cursor getTitle(int position) {
        Cursor mcursor = db.query(true, DATABASE_TABLE, new String []
                {
                        KEY_TITLE
                },
                KEY_ROWID + "=" + position, null, null, null, null, null, null);

        if(mcursor != null)
        {
            mcursor.moveToFirst();
        }
        return mcursor;

    }

    public Cursor getAuthor(int position) {
        Cursor mcursor = db.query(true, DATABASE_TABLE, new String []
                        {
                                KEY_AUTHOR
                        },
                KEY_ROWID + "=" + position, null, null, null, null, null, null);

        if(mcursor != null)
        {
            mcursor.moveToFirst();
        }
        return mcursor;
    }

    public Cursor getRowID(int position) {
        Cursor mcursor = db.query(true, DATABASE_TABLE, new String []
                        {
                                KEY_ROWID
                        },
                KEY_ROWID + "=" + position, null, null, null, null, null, null);

        if(mcursor != null)
        {
            mcursor.moveToFirst();
        }
        return mcursor;
    }
}
