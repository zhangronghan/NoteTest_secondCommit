package com.example.administrator.notetest.SQLite;

/**
 * Created by Administrator on 2017/5/2.
 */

public class NoteMsg {
    private String id;
    private String title;
    private String type;
    private String date;
    private String content;
    private int spinnerIndex;

    public NoteMsg(String id,String title,String type,String date,String content,int spinnerIndex){
        this.id=id;
        this.title=title;
        this.content=content;
        this.type=type;
        this.date=date;
        this.spinnerIndex=spinnerIndex;
    }

    public int getSpinnerIndex() {
        return spinnerIndex;
    }

    public void setSpinnerIndex(int spinnerIndex) {
        this.spinnerIndex = spinnerIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




}
