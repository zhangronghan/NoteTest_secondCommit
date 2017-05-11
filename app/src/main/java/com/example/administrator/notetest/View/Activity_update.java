package com.example.administrator.notetest.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.administrator.notetest.NoteConstant;
import com.example.administrator.notetest.R;
import com.example.administrator.notetest.presenter.SQLitePresenter;
import com.example.administrator.notetest.presenter.impl.SQLitePresenterImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Activity_update extends AppCompatActivity {
    private ImageView ivBackAndUpdate;
    private EditText edtTitle;
    private Spinner mSpinner;
    private EditText edtContent;
    private int id;
    private String[] arr;
    private int spinnerIndex;
    private String mtype;
    private SQLitePresenter mSQLitePresenter=new SQLitePresenterImpl();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init();
        getData();

    }

    private void init() {
        ivBackAndUpdate= (ImageView) findViewById(R.id.iv_backAndUpdate);
        edtTitle= (EditText) findViewById(R.id.edt_title);
        edtContent= (EditText) findViewById(R.id.edt_content);
        mSpinner= (Spinner) findViewById(R.id.spinner);

        ivBackAndUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOperate();
            }
        });


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                arr=getResources().getStringArray(R.array.mytype);
                spinnerIndex=position;
                mtype=arr[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void updateOperate() {
        String title=edtTitle.getText().toString();
        String content=edtContent.getText().toString();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = mFormat.format(new Date());
        String type=mtype;

/*        ContentValues values=new ContentValues();
        values.put(NoteConstant.NOTE_ID, id);
        values.put(NoteConstant.NOTE_TITLE, title);
        values.put(NoteConstant.NOTE_TYPE, type);
        values.put(NoteConstant.NOTE_CONTENT, content);
        values.put(NoteConstant.NOTE_DATE, date);
        values.put(NoteConstant.NOTE_SPINNER_INDEX,spinnerIndex);

        getContentResolver().update(MyContentProvider.notebook,values,"id="+id,null);*/

        mSQLitePresenter.update(this,id,title,type,content,date,spinnerIndex);
        finish();

    }

    private void getData() {
        id=getIntent().getIntExtra(NoteConstant.NOTE_ID,0);
        String title=getIntent().getStringExtra(NoteConstant.NOTE_TITLE);
        String content=getIntent().getStringExtra(NoteConstant.NOTE_CONTENT);
        int spinnerIndex=getIntent().getIntExtra(NoteConstant.NOTE_SPINNER_INDEX,0);

        mSpinner.setSelection(spinnerIndex,true);
        edtTitle.setText(title);
        edtContent.setText(content);

    }
}
