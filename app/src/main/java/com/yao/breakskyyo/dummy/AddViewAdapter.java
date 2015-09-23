package com.yao.breakskyyo.dummy;


import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yao.breakskyyo.R;

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
                public void onClick(View v) {
                    // if (mdata.get(i).getType()==1||mdata.get(i).getType()==2) {
                    // 分享的intent
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    // 分享的数据类型
                    intent.setType("text/plain");
                    // 分享的主题
                    intent.putExtra(Intent.EXTRA_SUBJECT, mdata.get(itemNum).getName());
                    String text = mdata.get(itemNum).getName() + "---(" + mdata.get(itemNum).getUrl()+") ";
                    if (!TextUtils.isEmpty(mdata.get(itemNum).getMima())) {
                        text=text+"  (密码--"+mdata.get(itemNum).getMima()+")";
                    }
                    // 分享的内容
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    // 允许启动新的Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // 目标应用寻找对话框的标题
                    mActivity.startActivity(Intent.createChooser(intent, mdata.get(itemNum).getName()));
                    // }else{

                    //  }

                }
            });

            ll_list.addView(convertView, i);
        }
    }
}
