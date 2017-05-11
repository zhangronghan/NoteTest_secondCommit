package com.example.administrator.notetest.presenter;

import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/5/8.
 */

public interface MenuPresenter {

    void closeMenu(View v, LinearLayout linearLayout);

    void openMenu(View v, LinearLayout linearLayout);
}
