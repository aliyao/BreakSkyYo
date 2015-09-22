package com.yao.breakskyyo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yao.breakskyyo.dummy.DummyContent;
import com.yao.breakskyyo.dummy.InfoVideos;
import com.yao.breakskyyo.tools.RegularId97;
import com.yao.breakskyyo.tools.StringDo;
import com.yao.breakskyyo.tools.YOBitmap;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoActivityScrollingActivity extends AppCompatActivity {
    DummyContent.DummyItem mDummyItem;
    InfoVideos mInfoVideos;
    ImageView showImg;
    TextView tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        init();
        toolbar.setTitle((String) mDummyItem.getContent());
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void init(){
        showImg=(ImageView)findViewById(R.id.showImg);
        tag=(TextView)findViewById(R.id.tag);
        String jsonFindItemInfo=getIntent().getStringExtra("jsonFindItemInfo");
        if(TextUtils.isEmpty(jsonFindItemInfo)){
            finish();
            return;
        }
        mDummyItem= JSON.parseObject(jsonFindItemInfo,DummyContent.DummyItem.class);
        if(mDummyItem==null){
            finish();
            return;
        }
        tag.setText(StringDo.removeNull(mDummyItem.getTag()));
        YOBitmap.getmKJBitmap().display(showImg, StringDo.removeNull(mDummyItem.getImgUrl()));
        httpGetItemInfo();
    }

    public void httpGetItemInfo() {
        KJHttp kjh = new KJHttp();
        kjh.get((String)mDummyItem.getUrl(), new HttpCallBack() {
            @Override
            public void onPreStart() {
                super.onPreStart();
                KJLoger.debug("在请求开始之前调用");
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                ViewInject.longToast("请求成功");
                KJLoger.debug("yoyo 结果:"+(String)mDummyItem.getUrl()+"--"+ t);

                mInfoVideos= RegularId97.getInfoVideos(t);

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                KJLoger.debug("exception:" + strMsg);
            }


            @Override
            public void onFinish() {
                super.onFinish();
                KJLoger.debug("请求完成，不管成功或者失败都会调用");
            }


        });

    }

}
