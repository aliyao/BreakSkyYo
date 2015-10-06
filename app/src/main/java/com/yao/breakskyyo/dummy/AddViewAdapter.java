package com.yao.breakskyyo.dummy;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yao.breakskyyo.InfoActivityScrollingActivity;
import com.yao.breakskyyo.R;
import com.yao.breakskyyo.tools.AppInfoUtil;
import com.yao.breakskyyo.tools.ClipboardManagerDo;
import com.yao.breakskyyo.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoyo on 2015/6/19.
 */
public class AddViewAdapter {
    private List<DownloadInfoItem> mdata;
    private LayoutInflater mInflater;
    LinearLayout ll_list;
    Activity mActivity;

    public AddViewAdapter(Activity mActivity, int rid) {
        this.mActivity = mActivity;
        this.mInflater = LayoutInflater.from(mActivity);
        ll_list = (LinearLayout) mActivity.findViewById(rid);
    }

    public DownloadInfoItem getItem(int position) {
        return mdata.get(position);
    }

    public void addmdata(List<DownloadInfoItem> data) {
        if (mdata == null) {
            mdata = new ArrayList<>();
        }
        mdata.addAll(data);
    }

    public void clearmdata() {
        mdata = new ArrayList<>();
    }

    public void addCommentView() {
        ll_list.removeAllViews();
        if (mdata == null || mdata.size() == 0) {
            return;
        }
        for (int i = 0; i < mdata.size(); i++) {
            View convertView = mInflater.inflate(R.layout.download_info_item, null);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView mima = (TextView) convertView.findViewById(R.id.mima);
            title.setText(mdata.get(i).getName());
            if (!TextUtils.isEmpty(mdata.get(i).getMima())) {
                mima.setText("密码--" + mdata.get(i).getMima());
            } else {
                mima.setText("");
            }

            final int itemNum = i;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                /*    builder.setIcon(R.mipmap.ic_launcher);*/
                   /* builder.setTitle("操作");*/
                    //    指定下拉列表的显示数据
                    String[] toDo;
                    if(mdata.get(itemNum).getType()==3){
                         String[] dos = {"分享", "复制"};
                        toDo=dos;
                    }else{
                         String[] dos = {"分享", "复制", "打开"};
                        toDo=dos;
                    }

                    //    设置一个下拉的列表选择项
                    builder.setItems(toDo, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    share(itemNum);
                                    break;
                                case 1:
                                    copy(itemNum,v);
                                    break;
                                case 2:
                                    open(itemNum);
                                    break;

                            }


                        }
                    });
                    builder.show();


                }
            });

            ll_list.addView(convertView, i);
        }
    }

    private void copy(int itemNum,final View v) {
        String text="";
        switch (mdata.get(itemNum).getType()){
            case 1:
                text = mdata.get(itemNum).getName() + "---(" + mdata.get(itemNum).getUrl() + ") ";
                if (!TextUtils.isEmpty(mdata.get(itemNum).getMima())) {
                    text = text + "  (密码--" + mdata.get(itemNum).getMima() + ")";
                }
                break;
            case 2:
                text = mdata.get(itemNum).getUrl();
                break;
            case 3:
                text = mdata.get(itemNum).getUrl();
                break;
            default:
                return;

        }
        ClipboardManagerDo.copy(text, mActivity);
        Snackbar.make(v, "复制成功", Snackbar.LENGTH_LONG).show();
    }
    private void share(int itemNum) {
        // 分享的intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        // 分享的数据类型
        intent.setType("text/plain");
        // 分享的主题
        intent.putExtra(Intent.EXTRA_SUBJECT, mdata.get(itemNum).getName());
        String text = mdata.get(itemNum).getName() + "---(" + mdata.get(itemNum).getUrl() + ") ";
        if (!TextUtils.isEmpty(mdata.get(itemNum).getMima())) {
            text = text + "  (密码--" + mdata.get(itemNum).getMima() + ")";
        }
        // 分享的内容
        intent.putExtra(Intent.EXTRA_TEXT, text);
        // 允许启动新的Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 目标应用寻找对话框的标题
        mActivity.startActivity(Intent.createChooser(intent, mdata.get(itemNum).getName()));
    }
    private void open(int itemNum){
        boolean isOpenBaiduDiskApp=false;
        if(!AppInfoUtil.isRunningBaiduDisk(mActivity)) {
            try {
                PackageManager packageManager = mActivity.getPackageManager();
                Intent intent=new Intent();
                intent = packageManager.getLaunchIntentForPackage(AppInfoUtil.BaiduDiskPackageName);
               /* intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");*/
                mActivity.startActivity(intent);
                isOpenBaiduDiskApp=true;
                ((InfoActivityScrollingActivity)mActivity).isOpenBaiduDiskApp=true;
                mActivity. moveTaskToBack(true);
                Log.e("isOpenBaiduDiskApp", "yoyo Thread is running."+isOpenBaiduDiskApp);

            } catch (Exception e) {
                e.printStackTrace();
                Snackbar.make(mActivity.findViewById(R.id.fab), "你还没有安装百度云，为了保存和在线观看视频，现在安装百度云！", Snackbar.LENGTH_LONG)
                        .setAction("安装", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent viewIntent = new
                                        Intent("android.intent.action.VIEW", Uri.parse("http://www.baidu.com/s?wd=百度云"));
                                mActivity.startActivity(viewIntent);
                            }
                        }).show();

                return;
            }
        }


        Intent mIntent= new Intent(mActivity, WebViewActivity.class);
        mIntent.putExtra("url", mdata.get(itemNum).getUrl());
        mIntent.putExtra("title", mdata.get(itemNum).getName());
        mIntent.putExtra("isOpenBaiduDiskApp", isOpenBaiduDiskApp);
        if (mdata.get(itemNum).getType()==1){
            mIntent .putExtra("mima", mdata.get(itemNum).getMima());
        }
        mActivity.startActivity(mIntent);
    }
}
