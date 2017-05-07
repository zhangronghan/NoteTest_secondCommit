package com.example.administrator.notetest.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.notetest.Adapter.MyNoteAdapter;
import com.example.administrator.notetest.Listener.OnItemClickListener;
import com.example.administrator.notetest.NoteConstant;
import com.example.administrator.notetest.R;
import com.example.administrator.notetest.SQLite.MyContentProvider;
import com.example.administrator.notetest.SQLite.NoteMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */
public class Activity_Search extends AppCompatActivity{
    private ImageView ivBack;
    private EditText edtContent;
    private ImageView ivSearch;
    private List<NoteMsg> list=new ArrayList<>();
    private MyNoteAdapter myAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        initViews();
        initRecyclerView();
        init();
    }

    private void initViews() {
        ivBack= (ImageView) findViewById(R.id.image_back);
        edtContent= (EditText) findViewById(R.id.edt_searchContent);
        ivSearch= (ImageView) findViewById(R.id.iv_Search);
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        myAdapter = new MyNoteAdapter(list);
        mRecyclerView.setAdapter(myAdapter);

    }

    private void init() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=edtContent.getText().toString();
                list=SearchData(content);
                myAdapter.notifyDataSetChanged();

            }
        });

        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                updateData(position);
            }
        });

        }


    private List<NoteMsg> SearchData(String content) {
        list.clear();
        Cursor cur = getContentResolver().query(MyContentProvider.notebook, null, null, null, null);
        if (cur == null) {
            return null;
        }

        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            String title = cur.getString(cur.getColumnIndex(NoteConstant.NOTE_TITLE));

            if(title.equals(content)){
                String id = String.valueOf(cur.getInt(cur.getColumnIndex(NoteConstant.NOTE_ID)));
                String mycontent = cur.getString(cur.getColumnIndex(NoteConstant.NOTE_CONTENT));
                String type = cur.getString(cur.getColumnIndex(NoteConstant.NOTE_TYPE));
                String date = cur.getString(cur.getColumnIndex(NoteConstant.NOTE_DATE));
                int spinnerindex = cur.getInt(cur.getColumnIndex(NoteConstant.NOTE_SPINNER_INDEX));
                NoteMsg note = new NoteMsg(id, title, type, date, mycontent, spinnerindex);
                list.add(note);
            } else {
                Toast.makeText(this, "找不到事件", Toast.LENGTH_SHORT).show();
            }

        }
        return list;
    }


    private void updateData(int position) {
        NoteMsg noteMsg = list.get(position);

        String id = noteMsg.getId();
        String title = noteMsg.getTitle();
        String type = noteMsg.getType();
        String date = noteMsg.getDate();
        String content = noteMsg.getContent();
        int spinnerIndex = noteMsg.getSpinnerIndex();

        Intent intent = new Intent(Activity_Search.this, Activity_update.class);

        intent.putExtra(NoteConstant.NOTE_ID, id);
        intent.putExtra(NoteConstant.NOTE_TITLE, title);
        intent.putExtra(NoteConstant.NOTE_TYPE, type);
        intent.putExtra(NoteConstant.NOTE_DATE, date);
        intent.putExtra(NoteConstant.NOTE_CONTENT, content);
        intent.putExtra(NoteConstant.NOTE_SPINNER_INDEX, spinnerIndex);
        startActivity(intent);
    }


}

