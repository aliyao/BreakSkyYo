package com.yao.breakskyyo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yao.breakskyyo.dummy.DummyItem;
import com.yao.breakskyyo.dummy.SearchItem;
import com.yao.breakskyyo.entity.JsonHead;
import com.yao.breakskyyo.entity.SearchInfo;
import com.yao.breakskyyo.entity.SearchInfoItem;
import com.yao.breakskyyo.net.HttpUrl;
import com.yao.breakskyyo.tools.YOBitmap;

import org.json.JSONObject;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.listener.CloudCodeListener;

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
        mListView = (ListView) findViewById(R.id.lv_search_list);

        refreshView = (SwipeRefreshLayout) findViewById(R.id.refreshView);
        mAdapter = new ArrayAdapter<SearchItem>(SearchActivity.this, R.layout.search_item) {
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
                SearchItem mSearchItem = getItem(position);
                TextView tv_hdtag = (TextView) view.findViewById(R.id.tv_hdtag);
                TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
                ImageView iv_image = (ImageView) view.findViewById(R.id.iv_image);
                iv_image.setImageBitmap(null);
               /* if (YOBitmap.getmKJBitmap().getCache(StringDo.removeNull(mSearchItem.getImgUrl())).length <= 0) {

                }*/
                YOBitmap.getmKJBitmap().display(iv_image, mSearchItem.getImgUrl());
                tv_hdtag.setText(mSearchItem.getHdtag());
                String strHtml = mSearchItem.getTitle();
                if (!TextUtils.isEmpty(strHtml)) {
                    tv_content.setText(Html.fromHtml(strHtml));
                }
                return view;
            }
        };
        View headerView = View.inflate(this, R.layout.search_item_head, null);
        et_word = (EditText) headerView.findViewById(R.id.et_word);
        bt_search = (Button) headerView.findViewById(R.id.bt_search);
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
                SearchItem searchItem=(SearchItem) parent.getAdapter().getItem(position);
                DummyItem dummyItem = new DummyItem(searchItem.getId(),
                        searchItem.getTitle(),searchItem.getUrl(),
                        searchItem.getImgUrl(),searchItem.getHdtag(),null,
                        searchItem.getScore());
              //  String id, String content,String url, String imgUrl, String tag,String type,String score
                startActivity(new Intent(SearchActivity.this, InfoActivityScrollingActivity.class).putExtra("jsonFindItemInfo", JSON.toJSONString(dummyItem)));
            }
        });
        mListView.setAdapter(mAdapter);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpGetSearchInfo();
            }
        });
    }

    // 隐藏键盘
    private void hintKb() {


        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }

    public void httpGetSearchInfo() {
        String word = et_word.getText() + "";
        if (TextUtils.isEmpty(word)) {
            Snackbar.make(et_word, "请输入关键字", Snackbar.LENGTH_LONG).show();
            refreshView.setRefreshing(false);
            return;
        }
        /*try {
            word = URLEncoder.encode(word, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        //第一个参数是上下文对象，第二个参数是云端逻辑的方法名称，第三个参数是上传到云端逻辑的参数列表（JSONObject cloudCodeParams），第四个参数是回调类
        JSONObject params = new JSONObject();
        try {
            params.put("word", word);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hintKb();
        onHttpStart();
        ace.callEndpoint(SearchActivity.this, HttpUrl.getSearchInfoCloudCodeName, params, new CloudCodeListener() {
            @Override
            public void onSuccess(Object object) {
                ViewInject.longToast("请求成功");
                KJLoger.debug("log:" + object.toString());
                JsonHead<SearchInfo> jsonHead = JSON.parseObject(object.toString(), new TypeReference<JsonHead<SearchInfo>>() {
                });
                List<SearchItem> searchItemList =new ArrayList<>();
                if(jsonHead!=null&&jsonHead.getInfo()!=null&&jsonHead.getInfo().getSearchInfo()!=null){
                    for(SearchInfoItem searchInfoItem:jsonHead.getInfo().getSearchInfo()){
                        searchItemList.add(new SearchItem(searchInfoItem.getId(),searchInfoItem.getTitle(),searchInfoItem.getImgUrl(),searchInfoItem.getTag(),  searchInfoItem.getHrefStr(),searchInfoItem.getScore()));
                    }
                }
                mAdapter.clear();
                if (searchItemList != null && searchItemList.size() > 0) {
                    mAdapter.addAll(searchItemList);
                } else {
                    Snackbar.make(et_word, "没有找到你想看的电影", Snackbar.LENGTH_LONG).show();
                }
                mAdapter.notifyDataSetChanged();
                onFinish();
            }

            @Override
            public void onFailure(int code, String msg) {
                KJLoger.debug("exception:" + msg);
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
                Snackbar.make(et_word, "网络不给力！！！", Snackbar.LENGTH_LONG).show();
                onFinish();
            }

            private void onFinish() {
                if (refreshView.isRefreshing()) {
                    refreshView.setRefreshing(false);
                }
                KJLoger.debug("请求完成，不管成功或者失败都会调用");
            }
        });
    }
    public void onHttpStart() {
        KJLoger.debug("在请求开始之前调用");
        if (!refreshView.isRefreshing()) {
            refreshView.setRefreshing(true);
        }
    }


    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                httpGetSearchInfo();
                break;
        }

    }

}
