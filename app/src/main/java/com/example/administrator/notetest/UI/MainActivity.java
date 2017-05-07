package com.example.administrator.notetest.UI;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.notetest.Adapter.MyNoteAdapter;
import com.example.administrator.notetest.Listener.OnItemClickListener;
import com.example.administrator.notetest.Listener.OnLongClickListener;
import com.example.administrator.notetest.NoteConstant;
import com.example.administrator.notetest.R;
import com.example.administrator.notetest.SQLite.MyContentProvider;
import com.example.administrator.notetest.SQLite.NoteMsg;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnLongClickListener{
    private RecyclerView mRecyclerView;
    private MyNoteAdapter mMyNoteAdapter;
    private FloatingActionButton floatButton;
    private boolean isfabopened = true;
    private LinearLayout mLinearLayout;
    private ImageView mImageView;
    private TextView tvWrite;
    private View btnView;
    private ArrayList<NoteMsg> noteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        noteList = readSQLiteOnApp();

        initRecyclerView();
    }



    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mMyNoteAdapter = new MyNoteAdapter(noteList,this);
        mRecyclerView.setAdapter(mMyNoteAdapter);

        mMyNoteAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                updateData(position);
            }
        });

    }



    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        floatButton = (FloatingActionButton) findViewById(R.id.floatBtn);
        mLinearLayout = (LinearLayout) findViewById(R.id.bottomLinear);
        mImageView = (ImageView) findViewById(R.id.image_head);
        tvWrite = (TextView) findViewById(R.id.tv_write);

        ButtonClick();


    }

    private ArrayList<NoteMsg> readSQLiteOnApp() {
        noteList.clear();
        Cursor cur = getContentResolver().query(MyContentProvider.notebook, null, null, null, null);
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

    private void ButtonClick() {
        tvWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayout.setVisibility(View.GONE);
                closeMenu(btnView);
                startActivity(new Intent(MainActivity.this, Activity_add.class));

            }
        });


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity_Search.class));
            }
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnView = v;
                if (isfabopened) {
                    openMenu(v);
                } else {
                    closeMenu(v);
                }
            }
        });
    }


    private void closeMenu(View v) {
        mLinearLayout.setVisibility(View.GONE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", -135, 20, 0);
        animator.setDuration(500);
        animator.start();
        isfabopened = true;
    }


    private void openMenu(View v) {
        mLinearLayout.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", 0, -155, -135);
        animator.setDuration(500);
        animator.start();
        isfabopened = false;

    }

    @Override
    protected void onResume() {
        super.onResume();
        noteList = readSQLiteOnApp();
        mMyNoteAdapter.notifyDataSetChanged();
    }

    private void updateData(int position) {
        NoteMsg noteMsg = noteList.get(position);

        String id = noteMsg.getId();
        String title = noteMsg.getTitle();
        String type = noteMsg.getType();
        String date = noteMsg.getDate();
        String content = noteMsg.getContent();
        int spinnerIndex = noteMsg.getSpinnerIndex();

        Intent intent = new Intent(MainActivity.this, Activity_update.class);

        intent.putExtra(NoteConstant.NOTE_ID, id);
        intent.putExtra(NoteConstant.NOTE_TITLE, title);
        intent.putExtra(NoteConstant.NOTE_TYPE, type);
        intent.putExtra(NoteConstant.NOTE_DATE, date);
        intent.putExtra(NoteConstant.NOTE_CONTENT, content);
        intent.putExtra(NoteConstant.NOTE_SPINNER_INDEX, spinnerIndex);
        startActivity(intent);
    }



    @Override
    public void OnLongItemClick(View view, final int position) {

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确定删除？");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String id=noteList.get(position).getId();
                getContentResolver().delete(MyContentProvider.notebook,"id="+id,null);
                noteList=readSQLiteOnApp();
                mMyNoteAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }
}
