package com.example.administrator.notetest.presenter;

import android.content.Context;

import com.example.administrator.notetest.SQLite.NoteMsg;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/8.
 */

public interface SQLitePresenter  {

    void insert(Context context,int id,String title,String type,String content,String date,int spinnerIndex);

    void update(Context context,int id,String title,String type,String content,String date,int spinnerIndex);

    void delete(Context context,String id);

    ArrayList<NoteMsg> query(Context context, ArrayList<NoteMsg> noteList);

}
