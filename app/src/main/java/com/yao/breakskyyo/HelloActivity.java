package com.yao.breakskyyo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yao.breakskyyo.entity.UpdateApp;
import com.yao.breakskyyo.tools.ACacheUtil;
import com.yao.breakskyyo.tools.AppInfoUtil;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class HelloActivity extends AppCompatActivity {
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private View mControlsView;
    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        Bmob.initialize(this, "54801b44cd3013af64a5e478dd162ea9");
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        findViewById(R.id.dummy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toMainActivity();
            }
        });
        // mainHandle.sendEmptyMessageDelayed(1,3000);
        getJsonMainBy();
    }

    public void getJsonMainBy() {
        int versionCode = (int) AppInfoUtil.getVersionCode(HelloActivity.this);
        if (versionCode > 0) {
            BmobQuery<UpdateApp> query = new BmobQuery<>();
            //条件：版本号大于versionCode
            query.addWhereGreaterThan("versionCode", versionCode);
            query.order("-versionCode");

           /* // 根据score字段升序显示数据
            query.order("score");
            // 根据score字段降序显示数据
            query.order("-score");
            // 多个排序字段可以用（，）号分隔
            query.order("-score,createdAt");*/

            //返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(1);
            //执行查询方法
            query.findObjects(this, new FindListener<UpdateApp>() {
                @Override
                public void onSuccess(List<UpdateApp> object) {
                    if(object.size()>0){
                        UpdateApp mUpdateApp=object.get(object.size()-1);
                        ACacheUtil.put(HelloActivity.this, ACacheUtil.UpdateAppJson, JSON.toJSONString(mUpdateApp));
                    }
                    mainHandle.sendEmptyMessageDelayed(1, 3000);
                }

                @Override
                public void onError(int code, String msg) {
                    mainHandle.sendEmptyMessageDelayed(1, 3000);
                }
            });

        }
    }

    private void toMainActivity() {
        startActivity(new Intent(HelloActivity.this, MainActivity.class));
        finish();
    }

    Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                toMainActivity();
            }
        }
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @SuppressLint("InlinedApi")
    private void show() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            getSupportActionBar().show();
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
