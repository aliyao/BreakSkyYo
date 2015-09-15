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
    final String zhengZeItem = "title=\"(.*?)\" target=\"_blank\" href=\"(.*?)\">[\\s\\S]*?<img alt=\".*?\" title=\".*?\" src=\"(.*?)\".*?>([\\s\\S]*?)</a>[\\s\\S]*?<span class=\"otherinfo\"> - (.*?)分</span></div>[\\s\\S]*?<div class=\"otherinfo\">类型：(.*?)</div>";
    final String zhengZeId = "id/(.*?).html";
    final String zhengZeType = "<a.*?class=\"movietype\">(.*?)</a>";
    final String zhengZeTag = "[\\s\\S]*?>(.*?)</";
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

        //HttpCallback中有很多方法，可以根据需求选择实现
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
                KJLoger.debug("log:" + t.toString());
                Pattern p = Pattern.compile(zhengZeItem);
                Matcher m = p.matcher(t.toString()); //csdn首页的源代码字符串
                List<Map<String, Object>> result = new ArrayList<>();
                while (m.find()) { //循环查找匹配字串
                    MatchResult mr = m.toMatchResult();
                    Map<String, Object> map = new HashMap<String, Object>();

                    for (int groupItem = 1; groupItem <= mr.groupCount(); groupItem++) {
                        if (mr != null && mr.group(groupItem) != null) {
                            //KJLoger.debug("group(i)--" + groupItem + "--" + mr.group(groupItem));
                            switch (groupItem) {
                                case 1:
                                    map.put("title", mr.group(groupItem));//找到后group(1)是表达式第一个括号的内容
                                    break;
                                case 2:
                                    map.put("url", mr.group(groupItem));//group(2)是表达式第二个括号的内容
                                    Pattern pID = Pattern.compile(zhengZeId);
                                    Matcher mID = pID.matcher(mr.group(groupItem)); //csdn首页的源代码字符串
                                    mID.find();
                                    map.put("id", mID.toMatchResult().group(1));//找到后group(1)是表达式第一个括号的内容
                                    break;
                                case 3:
                                    map.put("imgurl", mr.group(groupItem));//group(2)是表达式第三个括号的内容
                                    break;
                                case 4:
                                    String htmlTag = mr.group(groupItem);
                                    if (!TextUtils.isEmpty(htmlTag.trim())) {
                                        Pattern pTag = Pattern.compile(zhengZeTag);
                                        Matcher mTag = pTag.matcher(mr.group(groupItem)); //csdn首页的源代码字符串
                                        mTag.find();
                                        map.put("tag", mTag.toMatchResult().group(1));//找到后group(1)是表达式第一个括号的内容
                                    }
                                    // map.put("tag", mr.group(groupItem));
                                    break;
                                case 5:
                                    map.put("score", mr.group(groupItem));
                                    break;
                                case 6:
                                    if (!TextUtils.isEmpty(mr.group(groupItem).trim())) {
                                        Pattern pType = Pattern.compile(zhengZeType);
                                        Matcher mType = pType.matcher(mr.group(groupItem));
                                        List<String> typeList = new ArrayList<String>();
                                        while (mType.find()) { //循环查找匹配字串
                                            MatchResult mrType = mType.toMatchResult();
                                            for (int groupTypeItem = 1; groupTypeItem <= mrType.groupCount(); groupTypeItem++) {
                                                if (mrType != null && mrType.group(groupTypeItem) != null) {
                                                    typeList.add(mrType.group(groupTypeItem));
                                                }
                                            }
                                        }
                                        map.put("type", typeList);//找到后group(1)是表达式第一个括号的内容
                                    }

                                    break;

                            }
                        }
                    }
                    result.add(map);
                }
                DummyContent.setData(result);
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
