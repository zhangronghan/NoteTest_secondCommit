package com.example.administrator.notetest.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.administrator.notetest.EventBus.MyEventBus;
import com.example.administrator.notetest.Listener.OnItemClickListener;
import com.example.administrator.notetest.Listener.OnLongClickListener;
import com.example.administrator.notetest.NoteConstant;
import com.example.administrator.notetest.R;
import com.example.administrator.notetest.SQLite.NoteMsg;
import com.example.administrator.notetest.presenter.MenuPresenter;
import com.example.administrator.notetest.presenter.SQLitePresenter;
import com.example.administrator.notetest.presenter.impl.MenuPresenterImpl;
import com.example.administrator.notetest.presenter.impl.SQLitePresenterImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnLongClickListener {
    private RecyclerView mRecyclerView;
    private MyNoteAdapter mMyNoteAdapter;
    private FloatingActionButton floatButton;
    private boolean isfabopened = true;
    private LinearLayout mLinearLayout;
    private ImageView mImageView;
    private TextView tvWrite;
    private View btnView;
    private ArrayList<NoteMsg> noteList = new ArrayList<>();
    private SQLitePresenter mSQLitePresenter = new SQLitePresenterImpl();
    private MenuPresenter mMenuPresenter = new MenuPresenterImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        noteList = mSQLitePresenter.query(this,noteList);

        initRecyclerView();
        initEventBus();
    }

    private void initEventBus() {
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEventMainThread(MyEventBus event){
        String message=event.getMessage().trim();
        if(message.equals("INSERT")){
            noteList = mSQLitePresenter.query(this,noteList);
            mMyNoteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mMyNoteAdapter = new MyNoteAdapter(noteList, this);
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

/*    private ArrayList<NoteMsg> readSQLiteOnApp() {
        return mSQLitePresenter.query(this,noteList);
    }*/

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
        mMenuPresenter.closeMenu(v, mLinearLayout);
        isfabopened = true;
    }

    private void openMenu(View v) {
        mMenuPresenter.openMenu(v, mLinearLayout);
        isfabopened = false;
    }


    private void updateData(int position) {
        NoteMsg noteMsg = noteList.get(position);
        int id = Integer.parseInt(noteMsg.getId());
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

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确定删除？");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String id = noteList.get(position).getId();
                mSQLitePresenter.delete(MainActivity.this, id);
                noteList = mSQLitePresenter.query(MainActivity.this,noteList);
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
