package com.kuo.simplefloatbutton;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by User on 2015/6/24.
 */
public class FloatButtonLayout {

    private List<View> buttons = new ArrayList<View>();
    private OnItemAnimator onItemAnimator;

    public interface OnItemAnimator{
        void setItemAnimator(View button, int index);
    }

    public View getButton(int index){
        return buttons.get(index);
    }

    public void addButton(View button){
        buttons.add(button);
    }

    public void setOnItemAnimator(OnItemAnimator onItemAnimator){
        this.onItemAnimator = onItemAnimator;
    }

    public void startAnimator(){
        if(onItemAnimator != null){
            for(int i = 0 ; i < buttons.size() ; i++){
                onItemAnimator.setItemAnimator(buttons.get(i), i);
            }
        }
    }

}

abstract class  FloatButtonListener implements ViewPropertyAnimatorListener{

    @Override
    public void onAnimationStart(View view) {

    }

    @Override
    public void onAnimationCancel(View view) {

    }

    @Override
    public void onAnimationEnd(View view) {

    }

}