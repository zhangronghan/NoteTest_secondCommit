package com.example.administrator.notetest.presenter.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.administrator.notetest.NoteConstant;
import com.example.administrator.notetest.SQLite.MyContentProvider;
import com.example.administrator.notetest.SQLite.NoteMsg;
import com.example.administrator.notetest.presenter.SQLitePresenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/8.
 */

public class SQLitePresenterImpl implements SQLitePresenter{


    @Override
    public void insert(Context context,int id,String title,String type,String content,String date,int spinnerIndex) {
        ContentValues values = new ContentValues();
        values.put(NoteConstant.NOTE_ID, id);
        values.put(NoteConstant.NOTE_TITLE, title);
        values.put(NoteConstant.NOTE_TYPE, type);
        values.put(NoteConstant.NOTE_CONTENT, content);
        values.put(NoteConstant.NOTE_DATE, date);
        values.put(NoteConstant.NOTE_SPINNER_INDEX,spinnerIndex);

        context.getContentResolver().insert(MyContentProvider.notebook, values);

        Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void update(Context context, int id, String title, String type, String content, String date, int spinnerIndex) {
        ContentValues values=new ContentValues();
        values.put(NoteConstant.NOTE_ID, id);
        values.put(NoteConstant.NOTE_TITLE, title);
        values.put(NoteConstant.NOTE_TYPE, type);
        values.put(NoteConstant.NOTE_CONTENT, content);
        values.put(NoteConstant.NOTE_DATE, date);
        values.put(NoteConstant.NOTE_SPINNER_INDEX,spinnerIndex);

        context.getContentResolver().update(MyContentProvider.notebook,values,"id="+id,null);


    }

    @Override
    public void delete(Context context, String id) {
        context.getContentResolver().delete(MyContentProvider.notebook, "id=" + id, null);

    }

    @Override
    public ArrayList<NoteMsg> query(Context context, ArrayList<NoteMsg> noteList) {
        noteList.clear();
        Cursor cur = context.getContentResolver().query(MyContentProvider.notebook, null, null, null, null);
        if (cur == null) {
            return null;
        }
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            String id = String.valueOf(cur.getInt(cur.getColumnIndex(NoteConstant.NOTE_ID)));
            String title = cur.getString(cur.getColumnIndex(NoteConstant.NOTE_TITLE));
            String content = cur.getString(cur.getColumnIndex(NoteConstant.NOTE_CONTENT));
            String type = cur.getString(cur.getColumnIndex(NoteConstant.NOTE_TYPE));
            String date = cur.getString(cur.getColumnIndex(NoteConstant.NOTE_DATE));
            int spinnerindex = cur.getInt(cur.getColumnIndex(NoteConstant.NOTE_SPINNER_INDEX));

            NoteMsg note = new NoteMsg(id, title, type, date, content, spinnerindex);
            noteList.add(note);
        }
        return noteList;
    }


}
