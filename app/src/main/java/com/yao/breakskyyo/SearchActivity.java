package com.yao.breakskyyo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yao.breakskyyo.dummy.DummyItem;
import com.yao.breakskyyo.dummy.SearchItem;
import com.yao.breakskyyo.net.HttpUrl;
import com.yao.breakskyyo.tools.RegularId97;
import com.yao.breakskyyo.tools.StringDo;
import com.yao.breakskyyo.tools.YOBitmap;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.net.URLEncoder;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ListView mListView;
    SwipeRefreshLayout refreshView;
    private ArrayAdapter<SearchItem> mAdapter;
    EditText et_word;
   Button bt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        mListView=(ListView)findViewById(R.id.lv_search_list);

        refreshView=(SwipeRefreshLayout)findViewById(R.id.refreshView);
        mAdapter = new ArrayAdapter<SearchItem>(SearchActivity.this,R.layout.search_item){
            @Override
            public SearchItem getItem(int position) {
                return super.getItem(position);
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.search_item, parent, false);
                } else {
                    view = convertView;
                }
                SearchItem mSearchItem=getItem( position);
                TextView tv_hdtag=(TextView)view.findViewById(R.id.tv_hdtag);
                TextView tv_content=(TextView)view.findViewById(R.id.tv_content);
                ImageView iv_image=(ImageView)view.findViewById(R.id.iv_image);
                if (YOBitmap.getmKJBitmap().getCache(StringDo.removeNull(mSearchItem.getImgUrl())).length <= 0) {
                    iv_image.setImageBitmap(null);
                }
                YOBitmap.getmKJBitmap().display(iv_image,mSearchItem.getImgUrl());
                tv_hdtag.setText(mSearchItem.getHdtag());
                String strHtml = mSearchItem.getContent();
                if(!TextUtils.isEmpty(strHtml)){
                    tv_content.setText(Html.fromHtml(strHtml));
                }
                return view;
            }
        };
        View headerView = View.inflate(this, R.layout.search_item_head, null);
        et_word=(EditText)headerView.findViewById(R.id.et_word);
        bt_search=(Button)headerView.findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick(v);
            }
        });
        mListView.addHeaderView(headerView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DummyItem dummyItem = new DummyItem(((SearchItem)parent.getAdapter().getItem(position)).getId(), ((SearchItem)parent.getAdapter().getItem(position)).getImgUrl(), ((SearchItem)parent.getAdapter().getItem(position)).getTitle(), null);
                startActivity(new Intent(SearchActivity.this, InfoActivityScrollingActivity.class).putExtra("jsonFindItemInfo", JSON.toJSONString(dummyItem)));
            }
        });
        mListView.setAdapter(mAdapter);
    }
    // 隐藏键盘
    private void hintKb() {


            InputMethodManager imm =  (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            if(imm != null) {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                        0);
            }

    }

    public void httpGetSearchInfo() {
        String word=et_word.getText()+"";
        if(TextUtils.isEmpty(word)){
            Snackbar.make(et_word, "请输入关键字", Snackbar.LENGTH_LONG).show();
            return;
        }
        try {
            word= URLEncoder.encode(word,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        KJHttp kjh = new KJHttp();
        String url = HttpUrl.SearchInfo + word;
        Log.e("url", "url:" + url);
        hintKb();
        kjh.get(url, new HttpCallBack() {
            @Override
            public void onPreStart() {
                super.onPreStart();
                KJLoger.debug("在请求开始之前调用");
                if (!refreshView.isRefreshing()) {
                    refreshView.setRefreshing(true);
                }
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                ViewInject.longToast("请求成功");
                KJLoger.debug("log:" + t.toString());
                List<SearchItem> searchItemList= RegularId97.getSearchItem(t);
                mAdapter.clear();
                if(searchItemList!=null&&searchItemList.size()>0){
                    mAdapter.addAll(searchItemList);
                }else {
                    Snackbar.make(et_word, "没有找到你想看的电影", Snackbar.LENGTH_LONG).show();
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                KJLoger.debug("exception:" + strMsg);
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
                Snackbar.make(et_word, "网络不给力！！！", Snackbar.LENGTH_LONG).show();
            }


            @Override
            public void onFinish() {
                super.onFinish();
                if (refreshView.isRefreshing()) {
                    refreshView.setRefreshing(false);
                }
                KJLoger.debug("请求完成，不管成功或者失败都会调用");
            }


        });

    }
    public void onclick(View view){
        switch (view.getId()){
            case R.id.bt_search:
                httpGetSearchInfo();
                break;
        }

    }

}
