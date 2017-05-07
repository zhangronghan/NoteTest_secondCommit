package com.example.administrator.notetest.SQLite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.administrator.notetest.NoteConstant;

/**
 * Created by Administrator on 2017/5/5.
 */

public class MyContentProvider extends ContentProvider {
    private MySQLiteOpenHelper myOpenHelper;
    private static String authority="com.example.administrator.notetest";
    public static Uri notebook= Uri.parse("content://com.example.administrator.notetest/notebook");
    private static UriMatcher mUriMatcher;

    static{
        mUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(authority, NoteConstant.NOTE_TABLE,1);
    }

    @Override
    public boolean onCreate() {
        myOpenHelper=new MySQLiteOpenHelper(getContext(),"notebook",null,1);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=myOpenHelper.getReadableDatabase();
        if(mUriMatcher.match(uri)==1){
            return db.query(NoteConstant.NOTE_TABLE,projection,selection,selectionArgs,null,null,sortOrder);
        }
        return null;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=myOpenHelper.getWritableDatabase();
        if(mUriMatcher.match(uri) ==1){
            db.insert(NoteConstant.NOTE_TABLE,null,values);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=myOpenHelper.getWritableDatabase();
        if(mUriMatcher.match(uri) == 1){
            db.delete(NoteConstant.NOTE_TABLE,selection,null);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db=myOpenHelper.getWritableDatabase();
        if(mUriMatcher.match(uri) == 1){
            db.update(NoteConstant.NOTE_TABLE,values,selection,null);
        }
        return 0;

    }



}
