package com.example.administrator.notetest.EventBus;

/**
 * Created by Administrator on 2017/5/8.
 */

public class MyEventBus {
    String message;

    public MyEventBus(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
