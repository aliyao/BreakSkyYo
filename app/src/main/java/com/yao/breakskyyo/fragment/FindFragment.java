package com.yao.breakskyyo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yao.breakskyyo.InfoActivityScrollingActivity;
import com.yao.breakskyyo.MainActivity;
import com.yao.breakskyyo.R;
import com.yao.breakskyyo.db.DummyItemDb;
import com.yao.breakskyyo.dummy.DummyContent;
import com.yao.breakskyyo.dummy.DummyItem;
import com.yao.breakskyyo.dummy.SelectHeadItem;
import com.yao.breakskyyo.entity.FindItem;
import com.yao.breakskyyo.entity.InfoFind;
import com.yao.breakskyyo.entity.JsonHead;
import com.yao.breakskyyo.net.HttpUrl;
import com.yao.breakskyyo.tools.CommonUtil;
import com.yao.breakskyyo.tools.StringDo;
import com.yao.breakskyyo.tools.YOBitmap;

import org.json.JSONObject;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.listener.CloudCodeListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindFragment extends Fragment implements View.OnClickListener, AbsListView.OnItemClickListener, AbsListView.OnItemLongClickListener {
    private OnFragmentInteractionListener mListener;
    private AbsListView mListView, select_list;
    View ll_head_select;
    SwipeRefreshLayout refreshView;
    private ArrayAdapter mSelectAdapter;
    private ListAdapter mAdapter;
    List<List<SelectHeadItem>> listSelectHeadItemlist = new ArrayList<>();
    Button bt_select[];
    int selectedNum = -100;

    int pageNum;
    int positionItemYear = -100;
    int positionItemRating = -100;
    int positionItemCountry = -100;
    int positionItemTags = -100;

    TextView tv_bottom_text;

    int refreshState = 0;//0没有刷新，   1正在刷新


    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
       /* Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.finditem_grid, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    public void init() {

        mAdapter = new ArrayAdapter<DummyItem>(getActivity(),
                R.layout.finditem_list_item, DummyContent.ITEMS) {
            @Override
            public DummyItem getItem(int position) {
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
                DummyItem mDummyItem = getItem(position);
                ImageView img = (ImageView) view.findViewById(R.id.image);
                TextView title = (TextView) view.findViewById(R.id.title);
                TextView type = (TextView) view.findViewById(R.id.type);
                TextView tag = (TextView) view.findViewById(R.id.tag);
                TextView score = (TextView) view.findViewById(R.id.score);
                img.setImageBitmap(null);

                title.setText(mDummyItem.getContent());
                tag.setText(StringDo.removeNull(mDummyItem.getTag()));
                score.setText(StringDo.removeNull(mDummyItem.getScore()) + "分");
                String typeStr = "类型：" + mDummyItem.getType();
                type.setText(StringDo.removeNull(typeStr));
                YOBitmap.getmKJBitmap().display(img, StringDo.removeNull(mDummyItem.getImgUrl()));
                return view;
            }
        };
        mSelectAdapter = new ArrayAdapter(getActivity(), R.layout.finditem_head_select_item, R.id.tv_select_item_lable);
        mListView = (AbsListView) getView().findViewById(android.R.id.list);
        select_list = (AbsListView) getView().findViewById(R.id.select_list);
        ll_head_select = getView().findViewById(R.id.ll_head_select);
        refreshView = (SwipeRefreshLayout) getView().findViewById(R.id.refreshView);
        bt_select = new Button[4];
        bt_select[0] = (Button) getView().findViewById(R.id.bt_year);
        bt_select[1] = (Button) getView().findViewById(R.id.bt_score_sort);
        bt_select[2] = (Button) getView().findViewById(R.id.bt_national_area);
        bt_select[3] = (Button) getView().findViewById(R.id.bt_film_area);
        tv_bottom_text = (TextView) getView().findViewById(R.id.tv_bottom_text);
        tv_bottom_text.setVisibility(View.INVISIBLE);
        tv_bottom_text.setEnabled(false);
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                select_list.setVisibility(View.GONE);
                return false;
            }
        });
        for (Button bt :
                bt_select) {
            bt.setOnClickListener(this);
        }
        mListView.setAdapter(mAdapter);
        select_list.setAdapter(mSelectAdapter);
        select_list.setVisibility(View.GONE);
        ll_head_select.setVisibility(View.GONE);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpGetFindList(1, null);
            }
        });
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        select_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (refreshState == 1) {
                    return;
                }
                String paramsStr = null;
                switch (selectedNum) {
                    case 0:
                        if (TextUtils.isEmpty(listSelectHeadItemlist.get(selectedNum).get(position).getUrl())) {
                            positionItemYear = -100;
                        } else {
                            positionItemYear = position;
                        }
                        paramsStr = listSelectHeadItemlist.get(selectedNum).get(position).getUrl();
                        break;
                    case 1:
                        if (TextUtils.isEmpty(listSelectHeadItemlist.get(selectedNum).get(position).getUrl())) {
                            positionItemRating = -100;
                        } else {
                            positionItemRating = position;
                        }
                        paramsStr = listSelectHeadItemlist.get(selectedNum).get(position).getUrl();
                        break;
                    case 2:
                        if (TextUtils.isEmpty(listSelectHeadItemlist.get(selectedNum).get(position).getUrl())) {
                            positionItemCountry = -100;
                        } else {
                            positionItemCountry = position;
                        }
                        paramsStr = listSelectHeadItemlist.get(selectedNum).get(position).getUrl();
                        break;
                    case 3:
                        if (TextUtils.isEmpty(listSelectHeadItemlist.get(selectedNum).get(position).getUrl())) {
                            positionItemTags = -100;
                        } else {
                            positionItemTags = position;
                        }
                        paramsStr = listSelectHeadItemlist.get(selectedNum).get(position).getUrl();
                        break;
                }

                httpGetFindList(1, paramsStr);
                select_list.setVisibility(View.GONE);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                            if (tv_bottom_text.getVisibility() != View.VISIBLE) {
                                httpGetFindList(pageNum + 1, null);
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        refreshView.setProgressViewOffset(false, 0, CommonUtil.dip2px(getActivity(), 24));
        refreshView.setRefreshing(true);
        httpGetFindList(1, null);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(), InfoActivityScrollingActivity.class).putExtra("jsonFindItemInfo", JSON.toJSONString(((ArrayAdapter<DummyItem>) mAdapter).getItem(position))));
    }

    public void httpGetFindList(final int page, String paramsStr) {
        if (refreshState == 1) {
            if (refreshView.isRefreshing()) {
                refreshView.setRefreshing(false);
            } else {
                tv_bottom_text.setVisibility(View.INVISIBLE);
            }
            return;
        }

        if (positionItemYear >= 0) {
            if (TextUtils.isEmpty(paramsStr)) {
                paramsStr = listSelectHeadItemlist.get(0).get(positionItemYear).getUrl();
            }
            bt_select[0].setText(listSelectHeadItemlist.get(0).get(positionItemYear).getText());
        } else {
            bt_select[0].setText("年份");
        }
        if (positionItemRating >= 0) {
            if (TextUtils.isEmpty(paramsStr)) {
                paramsStr = listSelectHeadItemlist.get(1).get(positionItemRating).getUrl();
            }
            bt_select[1].setText(listSelectHeadItemlist.get(1).get(positionItemRating).getText());
        } else {
            bt_select[1].setText("评分");
        }
        if (positionItemCountry >= 0) {
            if (TextUtils.isEmpty(paramsStr)) {
                String countryStr = listSelectHeadItemlist.get(2).get(positionItemCountry).getUrl();
              /*  try {
                    countryStr = URLEncoder.encode(countryStr, "utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                paramsStr = countryStr;
            }
            bt_select[2].setText(listSelectHeadItemlist.get(2).get(positionItemCountry).getText());
        } else {
            bt_select[2].setText("地区");
        }
        if (positionItemTags >= 0) {
            if (TextUtils.isEmpty(paramsStr)) {
                String tagsStr = listSelectHeadItemlist.get(3).get(positionItemTags).getUrl();
            /*try {
                tagsStr = URLEncoder.encode(tagsStr, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }*/
                paramsStr = tagsStr;
            }
            bt_select[3].setText(listSelectHeadItemlist.get(3).get(positionItemTags).getText());
        } else {
            bt_select[3].setText("类型");
        }
        if (!TextUtils.isEmpty(paramsStr)) {
            paramsStr = paramsStr + "&" + HttpUrl.page + page;
        } else {
            paramsStr = HttpUrl.page + page;
        }
        Log.e("paramsStr", "paramsStr:" + paramsStr);

        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        //第一个参数是上下文对象，第二个参数是云端逻辑的方法名称，第三个参数是上传到云端逻辑的参数列表（JSONObject cloudCodeParams），第四个参数是回调类
        JSONObject params = new JSONObject();
        try {
            params.put("paramsStr", paramsStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        onHttpStart(page);
        ace.callEndpoint(FindFragment.this.getActivity(), HttpUrl.getFindInfoCloudCodeName, params, new CloudCodeListener() {
            @Override
            public void onSuccess(Object object) {
                try {
                    ViewInject.longToast("请求成功");
                    KJLoger.debug("log:" + object.toString());
                    JsonHead<InfoFind> listJsonHead = JSON.parseObject(object.toString(), new TypeReference<JsonHead<InfoFind>>() {
                    });
                    List<Map<String, Object>> result = new ArrayList<>();
                    for (FindItem mFindItem : listJsonHead.getInfo().getFindList()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("title", mFindItem.getTitle());//找到后group(1)是表达式第一个括号的内容
                        map.put("url", mFindItem.getHrefStr());//group(2)是表达式第二个括号的内容
                        map.put("id", mFindItem.getId());//找到后group(1)是表达式第一个括号的内容
                        map.put("imgurl", mFindItem.getImgUrl());//group(2)是表达式第三个括号的内容
                        map.put("tag", mFindItem.getTag());//找到后group(1)是表达式第一个括号的内容
                        map.put("score", mFindItem.getScore());
                        map.put("type", mFindItem.getType());//"|" 找到后group(1)是表达式第一个括号的内容
                        result.add(map);
                    }
                    if (page == 1) {
                        ((ArrayAdapter) mAdapter).clear();
                        DummyContent.setData(result);
                        pageNum = 1;
                    } else {
                        DummyContent.addData(result);
                        pageNum = page;
                        tv_bottom_text.setVisibility(View.INVISIBLE);
                    }
                    ((ArrayAdapter) mAdapter).notifyDataSetChanged();
                    listSelectHeadItemlist.clear();
                    listSelectHeadItemlist.add(listJsonHead.getInfo().getYearInfo().getTypeList());
                    listSelectHeadItemlist.add(listJsonHead.getInfo().getScoreInfo().getTypeList());
                    listSelectHeadItemlist.add(listJsonHead.getInfo().getCountryInfo().getTypeList());
                    listSelectHeadItemlist.add(listJsonHead.getInfo().getFilmTypeInfo().getTypeList());
                    ll_head_select.setVisibility(View.VISIBLE);
                    onFinish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                KJLoger.debug("exception:" + msg);
                if (page == 1) {
                    ((ArrayAdapter) mAdapter).clear();
                    pageNum = 1;
                    ((ArrayAdapter) mAdapter).notifyDataSetChanged();
                } else {
                    tv_bottom_text.setText("网络不给力！");
                    tv_bottom_text.setEnabled(true);
                }
                onFinish();
            }

            private void onFinish() {
                if (refreshView.isRefreshing()) {
                    refreshView.setRefreshing(false);
                }
                refreshState = 0;
                KJLoger.debug("请求完成，不管成功或者失败都会调用");
            }
        });
    }

    public void onHttpStart(int page) {
        refreshState = 1;
        KJLoger.debug("在请求开始之前调用");
        if (page > 1) {
            tv_bottom_text.setText("正在刷新");
            tv_bottom_text.setVisibility(View.VISIBLE);
        } else {
            if (!refreshView.isRefreshing()) {
                refreshView.setRefreshing(true);
            }
            tv_bottom_text.setVisibility(View.INVISIBLE);
        }
        tv_bottom_text.setEnabled(false);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
        Snackbar.make(view, "是否保存", Snackbar.LENGTH_LONG)
                .setAction("保存", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DummyItem dummyItem = ((ArrayAdapter<DummyItem>) mAdapter).getItem(position);
                        String tip = "保存失败";
                        switch (DummyItemDb.save(dummyItem, FindFragment.this.getActivity())) {
                            case 1:
                                tip = "保存成功";
                                ((MainActivity) getActivity()).updateSaveFragment();
                                break;
                            case 2:
                                tip = "已经保存";
                                break;
                        }
                        Snackbar.make(view, tip, Snackbar.LENGTH_LONG).show();
                    }
                }).show();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_year:
                if (selectedNum == 0 && select_list.getVisibility() == View.VISIBLE) {
                    select_list.setVisibility(View.GONE);
                } else {
                    selectedNum = 0;
                    select_list.setVisibility(View.VISIBLE);
                    mSelectAdapter.clear();
                    List<String> selectHeadItemlistYear = new ArrayList<>();
                    for (SelectHeadItem mSelectHeadItem :
                            listSelectHeadItemlist.get(selectedNum)) {
                        selectHeadItemlistYear.add(mSelectHeadItem.getText());
                    }
                    mSelectAdapter.addAll(selectHeadItemlistYear);
                }
                break;
            case R.id.bt_score_sort:
                if (selectedNum == 1 && select_list.getVisibility() == View.VISIBLE) {
                    select_list.setVisibility(View.GONE);
                } else {
                    selectedNum = 1;
                    select_list.setVisibility(View.VISIBLE);
                    mSelectAdapter.clear();
                    List<String> listSelectHeadItemRating = new ArrayList<>();
                    for (SelectHeadItem mSelectHeadItem :
                            listSelectHeadItemlist.get(selectedNum)) {
                        listSelectHeadItemRating.add(mSelectHeadItem.getText());
                    }
                    mSelectAdapter.addAll(listSelectHeadItemRating);
                }
                break;
            case R.id.bt_national_area:
                if (selectedNum == 2 && select_list.getVisibility() == View.VISIBLE) {
                    select_list.setVisibility(View.GONE);
                } else {
                    selectedNum = 2;
                    select_list.setVisibility(View.VISIBLE);
                    mSelectAdapter.clear();
                    List<String> listSelectHeadItemCountry = new ArrayList<>();
                    for (SelectHeadItem mSelectHeadItem :
                            listSelectHeadItemlist.get(selectedNum)) {
                        listSelectHeadItemCountry.add(mSelectHeadItem.getText());
                    }
                    mSelectAdapter.addAll(listSelectHeadItemCountry);
                }
                break;
            case R.id.bt_film_area:
                if (selectedNum == 3 && select_list.getVisibility() == View.VISIBLE) {
                    select_list.setVisibility(View.GONE);
                } else {
                    selectedNum = 3;
                    select_list.setVisibility(View.VISIBLE);
                    mSelectAdapter.clear();
                    List<String> listSelectHeadItemTags = new ArrayList<>();
                    for (SelectHeadItem mSelectHeadItem :
                            listSelectHeadItemlist.get(selectedNum)) {
                        listSelectHeadItemTags.add(mSelectHeadItem.getText());
                    }
                    mSelectAdapter.addAll(listSelectHeadItemTags);
                }
                break;
            case R.id.tv_bottom_text:
                httpGetFindList(pageNum + 1, null);
                break;
        }
    }
}
