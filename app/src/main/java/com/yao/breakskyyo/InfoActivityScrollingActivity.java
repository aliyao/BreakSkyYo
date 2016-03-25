package com.yao.breakskyyo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yao.breakskyyo.db.DummyItemDb;
import com.yao.breakskyyo.dummy.AddViewAdapter;
import com.yao.breakskyyo.dummy.DownloadInfoItem;
import com.yao.breakskyyo.dummy.DummyItem;
import com.yao.breakskyyo.dummy.InfoVideos;
import com.yao.breakskyyo.entity.DownloadItem;
import com.yao.breakskyyo.entity.JsonHead;
import com.yao.breakskyyo.entity.VideoInfo;
import com.yao.breakskyyo.net.HttpUrl;
import com.yao.breakskyyo.tools.ACacheUtil;
import com.yao.breakskyyo.tools.StringDo;
import com.yao.breakskyyo.tools.YOBitmap;
import com.yao.breakskyyo.webview.PlayFullscreenActivity;

import org.json.JSONObject;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.listener.CloudCodeListener;

public class InfoActivityScrollingActivity extends AppCompatActivity {
    DummyItem mDummyItem;
    InfoVideos mInfoVideos;
    ImageView showImg;
    TextView tag;
    AddViewAdapter adapter;
    LinearLayout resource_download_list;
    TextView plot_introduction;
    Button play_bt;
    View resource_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        init();
        toolbar.setTitle(mDummyItem.getContent());
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivityScrollingActivity.this);
                //    指定下拉列表的显示数据
                String[] toDo = {"保存", "教程"};
                //    设置一个下拉的列表选择项
                builder.setItems(toDo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mDummyItem.setSaveDate(new Date().getTime());
                                String tip = "保存失败";
                                switch (DummyItemDb.save(mDummyItem, InfoActivityScrollingActivity.this)) {
                                    case 1:
                                        tip = "保存成功";
                                        if (MainActivity.mainActivity != null) {
                                            MainActivity.mainActivity.updateSaveFragment();
                                        }
                                        break;
                                    case 2:
                                        tip = "已经保存";
                                        break;
                                }
                                Snackbar.make(view, tip, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                break;
                            case 1:
                                startActivity(new Intent(InfoActivityScrollingActivity.this, HelpActivity.class));
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
    }

    private void init() {
        showImg = (ImageView) findViewById(R.id.showImg);
        tag = (TextView) findViewById(R.id.tag);
        resource_download_list = (LinearLayout) findViewById(R.id.resource_download_list);
        resource_download = findViewById(R.id.resource_download);
        resource_download.setVisibility(View.GONE);
        plot_introduction = (TextView) findViewById(R.id.plot_introduction);
        play_bt = (Button) findViewById(R.id.play_bt);
        play_bt.setVisibility(View.GONE);
        adapter = new AddViewAdapter(InfoActivityScrollingActivity.this, resource_download_list.getId());
        String jsonFindItemInfo = getIntent().getStringExtra("jsonFindItemInfo");
        if (TextUtils.isEmpty(jsonFindItemInfo)) {
            finish();
            return;
        }
        mDummyItem = JSON.parseObject(jsonFindItemInfo, DummyItem.class);
        if (mDummyItem == null) {
            finish();
            return;
        }
        if (TextUtils.isEmpty(mDummyItem.getTag())) {
            tag.setVisibility(View.GONE);
        } else {
            tag.setVisibility(View.VISIBLE);
            tag.setText(StringDo.removeNull(mDummyItem.getTag()));
        }

        YOBitmap.getmKJBitmap().display(showImg, StringDo.removeNull(mDummyItem.getImgUrl()));
        httpGetItemInfo();
        if ((boolean) ACacheUtil.getAsObjectDefault(InfoActivityScrollingActivity.this, ACacheUtil.IsShowWifiTip, true)) {
            Snackbar.make(findViewById(R.id.fab), "请用wifi看视频，小心流量超了", Snackbar.LENGTH_INDEFINITE)
                    .setAction("我知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ACacheUtil.put(InfoActivityScrollingActivity.this, ACacheUtil.IsShowWifiTip, false);
                        }
                    }).show();
        }

    }


    public void httpGetItemInfo() {
        String urlStr = mDummyItem.getUrl();
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        //第一个参数是上下文对象，第二个参数是云端逻辑的方法名称，第三个参数是上传到云端逻辑的参数列表（JSONObject cloudCodeParams），第四个参数是回调类
        JSONObject params = new JSONObject();
        try {
            params.put("urlStr", urlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onHttpStart();
        ace.callEndpoint(InfoActivityScrollingActivity.this, HttpUrl.getFilmInfoCloudCodeName, params, new CloudCodeListener() {
            @Override
            public void onSuccess(Object object) {
                try {
                    ViewInject.longToast("请求成功");
                    KJLoger.debug("yoyo 结果:" + mDummyItem.getUrl() + "--" + object.toString());
                    JsonHead<VideoInfo> videoInfoJsonHead = JSON.parseObject(object.toString(), new TypeReference<JsonHead<VideoInfo>>() {
                    });
                    mInfoVideos = new InfoVideos();
                    mInfoVideos.setMovie_title(videoInfoJsonHead.getInfo().getTitle());
                    mInfoVideos.setMovie_jvqing(videoInfoJsonHead.getInfo().getContent());
                    List<Map<String, String>> regularChili = new ArrayList<>();
                    for (DownloadItem downloadItem : videoInfoJsonHead.getInfo().getChildDownload()) {
                        if (downloadItem.getType() == 2) {
                            mInfoVideos.setMovie_payZaixian(downloadItem.getUrl());
                        } else if (downloadItem.getType() == 1) {
                            mInfoVideos.setBaiduPanUrl(downloadItem.getUrl());
                            mInfoVideos.setBaiduPanName(downloadItem.getTitle());
                            mInfoVideos.setBaiduPanUrlMima(downloadItem.getBaiduPsw());
                        } else {
                            Map<String, String> map = new HashMap<>();
                            map.put("url", downloadItem.getUrl());
                            map.put("name", downloadItem.getTitle());
                            regularChili.add(map);
                        }
                    }
                    mInfoVideos.setRegularChili(regularChili);
                    // mInfoVideos = RegularId97.getInfoVideos(t);
                    // mInfoVideos.setMovie_title(mDummyItem.getContent());
                    initInfoVideos();
                    onFinish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                KJLoger.debug("exception:" + msg);
                Snackbar.make(showImg, "网络不给力！！！", Snackbar.LENGTH_INDEFINITE).setAction("刷新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        httpGetItemInfo();
                    }
                }).show();
                onFinish();
            }

            private void onFinish() {
                KJLoger.debug("请求完成，不管成功或者失败都会调用");
            }
        });
    }

    public void onHttpStart() {
        KJLoger.debug("在请求开始之前调用");
    }


    public void initInfoVideos() {
        plot_introduction.setText(mInfoVideos.getMovie_jvqing());
        List<DownloadInfoItem> downloadInfoItemList = new ArrayList<>();
        if (!TextUtils.isEmpty(mInfoVideos.getBaiduPanUrl())) {
            downloadInfoItemList.add(new DownloadInfoItem(mInfoVideos.getBaiduPanName(), mInfoVideos.getBaiduPanUrl(), mInfoVideos.getBaiduPanUrlMima(), 1));
        }
        if (mInfoVideos.getRegularChili() != null) {
            for (Map<String, String> map :
                    mInfoVideos.getRegularChili()) {
                downloadInfoItemList.add(new DownloadInfoItem( map.get("name"),map.get("url"), null, 3));
            }
        }
        adapter.clearmdata();
        adapter.addmdata(downloadInfoItemList);
        adapter.addCommentView();
        resource_download.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(mInfoVideos.getMovie_payZaixian())) {
            play_bt.setVisibility(View.GONE);
        } else {
            play_bt.setVisibility(View.VISIBLE);
        }

    }

    public void onClickDo(View view) {
        switch (view.getId()) {
            case R.id.play_bt:
                String url = getResources().getString(R.string.zai_xian_url).replace("(id)", mDummyItem.getId());
                startActivity(new Intent(InfoActivityScrollingActivity.this, PlayFullscreenActivity.class).putExtra("url", url).putExtra("title", mInfoVideos.getMovie_title()));
                // startActivity(new Intent(InfoActivityScrollingActivity.this, WebViewActivity.class).putExtra("url",mInfoVideos.getBaiduPanUrl()).putExtra("BaiduMima",mInfoVideos.getBaiduPanUrlMima()));
                break;
        }

    }
}
