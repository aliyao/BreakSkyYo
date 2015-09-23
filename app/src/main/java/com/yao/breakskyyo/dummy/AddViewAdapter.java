package com.yao.breakskyyo.dummy;


        import android.app.Activity;
        import android.text.TextUtils;
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
public class AddViewAdapter {
    private List<DownloadInfoItem> mdata;
    private LayoutInflater mInflater;
    LinearLayout ll_list;
    Activity mActivity;

    public AddViewAdapter(Activity mActivity, int rid) {
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
            if(!TextUtils.isEmpty(mdata.get(i).getMima())){
                mima.setText("密码--"+mdata.get(i).getMima());
            }else{
                mima.setText("");
            }

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
