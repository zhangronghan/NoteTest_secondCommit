package com.example.administrator.notetest.presenter.impl;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.example.administrator.notetest.presenter.GetImageUriPresenter;

/**
 * Created by Administrator on 2017/5/10.
 */

public class GetImgaePresenterImpl implements GetImageUriPresenter {


    @Override
    public String getAbsoluteImagePath(Context context,Intent data) {
        String imagepath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(context,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection= MediaStore.Images.Media._ID + "=" + id;
                imagepath=getImagePath(context,MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            } else if("com.android.provider.downloads.document".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagepath=getImagePath(context,contentUri,null);
            } else if("content".equalsIgnoreCase(uri.getScheme())){
                imagepath=getImagePath(context,uri,null);
            } else if("file".equalsIgnoreCase(uri.getScheme())){
                imagepath=uri.getPath();
            }
        }
        return imagepath;
    }



    private String getImagePath(Context context,Uri uri, String selection) {
        String path=null;
        Cursor cursor=context.getContentResolver().query(uri,null,null,null,null);
        if(cursor !=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }




}
