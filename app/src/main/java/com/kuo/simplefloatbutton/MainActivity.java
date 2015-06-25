package com.kuo.simplefloatbutton;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatButtonLayout floatButtonLayout;
    private RelativeLayout mainLayout;

    private boolean isShow = true;

    private int disy = 0;
    private int touchCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewTarget();
        onInitRecyclerViewAdapter();

    }

    private void findViewTarget(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        toolbar.setTitle("SimpleFloatButton");
        setSupportActionBar(toolbar);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(180, 180);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.setMargins(0, 0, 30, 30);

        ImageButton addButton = new ImageButton(this);
        addButton.setLayoutParams(layoutParams);
        addButton.setBackgroundResource(R.drawable.add_button);

        ImageButton deleteButton = new ImageButton(this);
        deleteButton.setLayoutParams(layoutParams);
        deleteButton.setBackgroundResource(R.drawable.delete_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touchCount++;
                if (touchCount == 2) {
                    touchCount = 0;
                    floatButtonLayout.setOnItemAnimator(onBackItemAnimator);
                    floatButtonLayout.startAnimator();
                } else {
                    floatButtonLayout.setOnItemAnimator(onStartItemAnimator);
                    floatButtonLayout.startAnimator();
                }
            }
        });

        mainLayout.addView(deleteButton);
        mainLayout.addView(addButton);

        floatButtonLayout = new FloatButtonLayout();
        floatButtonLayout.addButton(deleteButton);
        floatButtonLayout.addButton(addButton);

    }

    private void onInitRecyclerViewAdapter(){

        int[] colors = {R.color.Grey_700,
                R.color.LightGreen_500,
                R.color.Red_500,
                R.color.Pink_500,
                R.color.Purple_500,
                R.color.DeepPurple_500,
                R.color.Indigo_500,
                R.color.Blue_500};

        List<Integer> colorsList = new ArrayList<Integer>();

        for(int i = 0 ; i < colors.length ; i++){
            colorsList.add(colors[i]);
        }

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new RecyclerViewAdapter(this, colorsList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private FloatButtonLayout.OnItemAnimator onStartItemAnimator = new FloatButtonLayout.OnItemAnimator() {
        @Override
        public void setItemAnimator(View button, int index) {
            switch (index){
                case 0:
                    //Delete Button Animator
                    ViewPropertyAnimatorCompat deleteAnimator = ViewCompat.animate(button);
                    deleteAnimator
                            .translationY(-(button.getHeight() + 20))
                            .setInterpolator(new OvershootInterpolator())
                            .start();
                    break;
                case 1:
                    //Add Button Animator
                    ViewPropertyAnimatorCompat addAnimator = ViewCompat.animate(button);
                    addAnimator.rotation(405).start();
                    break;
            }
        }
    };

    private FloatButtonLayout.OnItemAnimator onBackItemAnimator = new FloatButtonLayout.OnItemAnimator() {
        @Override
        public void setItemAnimator(final View button, int index) {
            switch (index){
                case 0:
                    //Delete Button Animator-
                    ViewPropertyAnimatorCompat deleteAnimator = ViewCompat.animate(button);
                    deleteAnimator
                            .translationY(0)
                            .setDuration(500)
                            .setListener(new FloatButtonListener() {
                                @Override
                                public void onAnimationEnd(View view) {
                                    super.onAnimationEnd(view);
                                }
                            })
                            .start();
                    break;
                case 1:
                    //Add Button Animator
                    ViewPropertyAnimatorCompat addAnimator = ViewCompat.animate(button);
                    addAnimator.rotation(0).start();
                    break;
            }
        }
    };

    private FloatButtonLayout.OnItemAnimator onHideItemAnimator = new FloatButtonLayout.OnItemAnimator() {
        @Override
        public void setItemAnimator(View button, int index) {

            if(!isShow){
                ViewCompat.setRotation(button, 0);
                touchCount = 0;
                ViewPropertyAnimatorCompat deleteAnimator = ViewCompat.animate(button);
                deleteAnimator
                        .translationY(button.getHeight() + 100)
                        .setDuration(250)
                        .start();
            }else{
                ViewPropertyAnimatorCompat deleteAnimator = ViewCompat.animate(button);
                deleteAnimator
                        .translationY(0)
                        .setDuration(250)
                        .start();
            }
        }
    };

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if(disy > 30 && isShow){
                isShow = false;
                disy = 0;
                floatButtonLayout.setOnItemAnimator(onHideItemAnimator);
                floatButtonLayout.startAnimator();
            }else if(disy < -30 && !isShow){
                isShow = true;
                disy = 0;
                floatButtonLayout.setOnItemAnimator(onHideItemAnimator);
                floatButtonLayout.startAnimator();
            }

            if((isShow && dy>0) || (!isShow && dy<0)) {
                disy += dy;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
