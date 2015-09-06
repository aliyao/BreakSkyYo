package com.yao.breakskyyo.adapter;

/**
 * 项目名称：BreakSkyYo
 * 类描述：
 * 创建人：yoyo
 * 创建时间：2015/9/22 22:41
 * 修改人：yoyo
 * 修改时间：2015/9/22 22:41
 * 修改备注：
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yao.breakskyyo.R;
import com.yao.breakskyyo.dummy.DownloadInfoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoyo on 2015/6/19.
 */
public class LinearLayoutAddViewAdapter {
    private List<DownloadInfoItem> mdata;
    private LayoutInflater mInflater;
    LinearLayout ll_list;
    Activity mActivity;

    public LinearLayoutAddViewAdapter(Activity mActivity, int rid) {
        this.mActivity=mActivity;
        this.mInflater = LayoutInflater.from(mActivity);
        ll_list = (LinearLayout) mActivity.findViewById(rid);
    }

    public DownloadInfoItem getItem(int position) {
        return mdata.get(position);
    }

    public void addmdata(List<DownloadInfoItem> data) {
        if (mdata == null) {
            mdata=new ArrayList<>();
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
            mima.setText(mdata.get(i).getMima());
            final int itemNum =i;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            ll_list.addView(convertView, i);
        }
    }
}
