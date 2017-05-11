package com.example.administrator.notetest.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.notetest.DataConstant;
import com.example.administrator.notetest.EventBus.MyEventBus;
import com.example.administrator.notetest.R;
import com.example.administrator.notetest.presenter.GetImageUriPresenter;
import com.example.administrator.notetest.presenter.SQLitePresenter;
import com.example.administrator.notetest.presenter.impl.GetImgaePresenterImpl;
import com.example.administrator.notetest.presenter.impl.SQLitePresenterImpl;
import com.example.administrator.notetest.weiget.MyEditText;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cn.zeffect.view.recordbutton.RecordButton;

/**
 * Created by Administrator on 2017/5/3.
 */
public class Activity_add extends AppCompatActivity{
    private TextView tvCancel;
    private Spinner mSpinner;
    private EditText edtTitle;
    private MyEditText edtContent;
    private ImageView ivAddImage;
    private RecordButton mRecordButton;
    private String[] arr;
    private String mtype;
    private TextView tvSave;
    private int spinnerIndex;
    private SQLitePresenter mSQLitePresenter=new SQLitePresenterImpl();
    private GetImageUriPresenter mGetImageUriPresenter=new GetImgaePresenterImpl();
    private String recordButtonPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initViews();
        init();
        initRecordButton();

    }


    private void initViews() {
        tvCancel= (TextView) findViewById(R.id.tv_Cancel);
        mSpinner= (Spinner) findViewById(R.id.spinner);
        tvSave= (TextView) findViewById(R.id.tv_Save);
        edtTitle= (EditText) findViewById(R.id.edt_title);
        edtContent= (MyEditText) findViewById(R.id.edt_content);
        ivAddImage= (ImageView) findViewById(R.id.iv_addImage);
        mRecordButton= (RecordButton) findViewById(R.id.record_addSound);
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

        ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, DataConstant.GET_IMAGE);
            }
        });

    }

    private void initRecordButton() {
        recordButtonPath=getApplicationContext().getFilesDir().getAbsolutePath();

        mRecordButton.setSavePath(recordButtonPath);//设置存储路径,6.0注意申请权限
        mRecordButton.setSaveName("MyRecordButton");//设置文件名字，如果设置了名字，将会一直使用，后面的录音文件会覆盖前面的文件
        mRecordButton.setPrefix("record_");//设置文件名前缀，不设置名字，只设置前缀，保证文件不重复，存带前缀标志
        mRecordButton.setMaxIntervalTime(10000);//毫秒，设置最长时间
        mRecordButton.setMinIntervalTime(2000);//毫秒，设置最短录音时间
        mRecordButton.setTooShortToastMessage("录音时间过短，请重新录入");//设置时间太短的提示语

        mRecordButton.setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {
            @Override
            public void onFinishedRecord(String s) {

            }

            @Override
            public void readCancel() {

            }

            @Override
            public void noCancel() {

            }

            @Override
            public void onActionDown() {

            }

            @Override
            public void onActionUp() {

            }

            @Override
            public void onActionMove() {

            }
        });


    }


    private void InsertOperate() {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String type = mtype;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = mFormat.format(new Date());
        int id = getID();

        mSQLitePresenter.insert(this,id,title,type,content,date,spinnerIndex);
        EventBus.getDefault().post(new MyEventBus("INSERT"));
        finish();
    }


    public int getID() {
        Random random=new Random();
        int mID=random.nextInt(10000);
        return mID;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DataConstant.GET_IMAGE){
            if(data != null){
                Uri imageUrl=data.getData();
                String path=mGetImageUriPresenter.getAbsoluteImagePath(this,data);
                Log.d("Activity_add","路径为"+path);
                edtContent.insertBitmap(path);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
