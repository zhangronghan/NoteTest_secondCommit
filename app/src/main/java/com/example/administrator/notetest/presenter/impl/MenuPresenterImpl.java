package com.example.administrator.notetest.presenter.impl;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.notetest.presenter.MenuPresenter;

/**
 * Created by Administrator on 2017/5/8.
 */

public class MenuPresenterImpl implements MenuPresenter{


    @Override
    public void closeMenu(View v, LinearLayout linearLayout) {
        linearLayout.setVisibility(View.GONE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", -135, 20, 0);
        animator.setDuration(500);
        animator.start();

    }

    @Override
    public void openMenu(View v, LinearLayout linearLayout) {
        linearLayout.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", 0, -155, -135);
        animator.setDuration(500);
        animator.start();
    }
}
