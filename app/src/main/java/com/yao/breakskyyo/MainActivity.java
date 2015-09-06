package com.yao.breakskyyo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yao.breakskyyo.dummy.DummyContent;
import com.yao.breakskyyo.net.HttpUrl;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , AbsListView.OnItemClickListener {
    private AbsListView mListView;
    SwipeRefreshLayout refreshView;
    private ListAdapter mAdapter;
    final String zhengZeItem = "title=\"(.*?)\" target=\"_blank\" href=\"(.*?)\">[\\s\\S]*?<img alt=\".*?\" title=\".*?\" src=\"(.*?)\".*?>([\\s\\S]*?)</a>[\\s\\S]*?<span class=\"otherinfo\"> - (.*?)分</span></div>[\\s\\S]*?<div class=\"otherinfo\">类型：(.*?)</div>";
    final String zhengZeId = "id/(.*?).html";
    final String zhengZeType = "<a.*?class=\"movietype\">(.*?)</a>";
    final String zhengZeTag = "[\\s\\S]*?>(.*?)</";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();

    }
    private void init(){
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(this,
                R.layout.finditem_list_item, DummyContent.ITEMS) {
            @Override
            public DummyContent.DummyItem getItem(int position) {
                return super.getItem(position);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.finditem_list_item, parent, false);
                } else {
                    view = convertView;
                }
                DummyContent.DummyItem mDummyItem = getItem(position);
                ImageView img = (ImageView) view.findViewById(R.id.image);
                TextView title = (TextView) view.findViewById(R.id.title);
                TextView type = (TextView) view.findViewById(R.id.type);
                TextView tag = (TextView) view.findViewById(R.id.tag);
                TextView score = (TextView) view.findViewById(R.id.score);
                title.setText((String) mDummyItem.getContent());
                tag.setText(StringDo.removeNull(mDummyItem.getTag()));
                score.setText(StringDo.removeNull(mDummyItem.getScore())+"分");
                List<String> typelist=mDummyItem.getType();
                String typeStr="类型：";
                if(typelist!=null&&typelist.size()>0){
                    for(int item=0;item<typelist.size();item++){
                        if (item==0){
                            typeStr=typeStr+typelist.get(item);
                        }else{
                            typeStr=typeStr+"|"+typelist.get(item);
                        }
                    }
                }else{
                    typeStr="";
                }
                type.setText(StringDo.removeNull(typeStr));
                YOBitmap.getmKJBitmap().display(img,StringDo.removeNull(mDummyItem.getImgUrl()));
                return view;
            }
        };
        mListView = (AbsListView) findViewById(android.R.id.list);
        refreshView=(SwipeRefreshLayout) findViewById(R.id.refreshView);
        mListView.setAdapter(mAdapter);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpGetFindList(1);
            }
        });
        mListView.setOnItemClickListener(this);
        httpGetFindList(1);

    }


    public void httpGetFindList(final int page) {
        KJHttp kjh = new KJHttp();
        /*HttpParams params = new HttpParams();
        params.put("id", "1"); //传递参数
        params.put("name", "kymjs");*/
        //HttpCallback中有很多方法，可以根据需求选择实现

        String url=HttpUrl.FindList+"page="+page;
        kjh.get(url, new HttpCallBack() {
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
                    Map<String, Object> map = new HashMap<>();
                 /*   map.put("title", mr.group(1));//找到后group(1)是表达式第一个括号的内容
                    map.put("url", mr.group(2));//group(2)是表达式第二个括号的内容
                    map.put("imgurl", mr.group(3));//group(2)是表达式第三个括号的内容*//*

                    KJLoger.debug("groupCount--" + mr.groupCount());
                    for (int i = 1; i <= mr.groupCount(); i++) {
                        if (mr != null && mr.group(i) != null) {
                            KJLoger.debug("group(i)--" + i + "--" + mr.group(i));
                        }
                    }*/
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
                                    // KJLoger.debug("--" + mID.toMatchResult().group(1));
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
                //adapter = new ArrayAdapter(this,R.layout.view,R.id.textview1,list1);
                if(page==1){
                    ((ArrayAdapter) mAdapter).clear();
                    DummyContent.setData(result);
                }else{
                    DummyContent.addData(result);
                }
                ((ArrayAdapter) mAdapter).notifyDataSetChanged();
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                KJLoger.debug("exception:" + strMsg);
            }


            @Override
            public void onFinish() {
                super.onFinish();
                refreshView.setRefreshing(false);
                KJLoger.debug("请求完成，不管成功或者失败都会调用");
            }


        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            finish();
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_slideshow) {
            // Handle the camera action
        } else if (id == R.id.nav_save) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this,AboutActivity.class));
        } /*else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, InfoActivityScrollingActivity.class).putExtra("jsonFindItemInfo", JSON.toJSONString(((ArrayAdapter<DummyContent.DummyItem>) mAdapter).getItem(position))));
    }
}
