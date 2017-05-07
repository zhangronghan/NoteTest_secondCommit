package com.example.administrator.notetest.UI;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.notetest.NoteConstant;
import com.example.administrator.notetest.R;
import com.example.administrator.notetest.SQLite.MyContentProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2017/5/3.
 */
public class Activity_add extends AppCompatActivity{
    private TextView tvCancel;
    private Spinner mSpinner;
    private EditText edtTitle;
    private EditText edtContent;
    private String[] arr;
    private String mtype;
    private TextView tvSave;
    private int spinnerIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initViews();
        init();

    }

    private void initViews() {
        tvCancel= (TextView) findViewById(R.id.tv_Cancel);
        mSpinner= (Spinner) findViewById(R.id.spinner);
        tvSave= (TextView) findViewById(R.id.tv_Save);
        edtTitle= (EditText) findViewById(R.id.edt_title);
        edtContent= (EditText) findViewById(R.id.edt_content);

    }

    private void init() {


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                arr=getResources().getStringArray(R.array.mytype);
                mtype =arr[position];
                spinnerIndex=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertOperate();
                startActivity(new Intent(Activity_add.this,MainActivity.class));

            }
        });
    }

    private void InsertOperate() {
        ContentValues values = new ContentValues();
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String type = mtype;

        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = mFormat.format(new Date());

        int id = getID();
        values.put(NoteConstant.NOTE_ID, id);
        values.put(NoteConstant.NOTE_TITLE, title);
        values.put(NoteConstant.NOTE_TYPE, type);
        values.put(NoteConstant.NOTE_CONTENT, content);
        values.put(NoteConstant.NOTE_DATE, date);
        values.put(NoteConstant.NOTE_SPINNER_INDEX,spinnerIndex);

        getContentResolver().insert(MyContentProvider.notebook, values);

        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();

        finish();
    }


    public int getID() {
        Random random=new Random();
        int mID=random.nextInt(10000);
        return mID;
    }
}
