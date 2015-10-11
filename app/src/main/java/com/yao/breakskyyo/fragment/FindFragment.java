package com.yao.breakskyyo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
import com.yao.breakskyyo.InfoActivityScrollingActivity;
import com.yao.breakskyyo.MainActivity;
import com.yao.breakskyyo.R;
import com.yao.breakskyyo.db.DummyItemDb;
import com.yao.breakskyyo.dummy.DummyContent;
import com.yao.breakskyyo.dummy.DummyItem;
import com.yao.breakskyyo.dummy.SelectHeadItem;
import com.yao.breakskyyo.net.HttpUrl;
import com.yao.breakskyyo.tools.CommonUtil;
import com.yao.breakskyyo.tools.RegularId97;
import com.yao.breakskyyo.tools.StringDo;
import com.yao.breakskyyo.tools.YOBitmap;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    final String zhengZeItem = "title=\"(.*?)\" target=\"_blank\" href=\"(.*?)\">[\\s\\S]*?<img alt=\".*?\" title=\".*?\" src=\"(.*?)\".*?>([\\s\\S]*?)</a>[\\s\\S]*?<span class=\"otherinfo\"> - (.*?)分</span></div>[\\s\\S]*?<div class=\"otherinfo\">类型：(.*?)</div>";
    final String zhengZeId = "id/(.*?).html";
    final String zhengZeType = "<a.*?class=\"movietype\">(.*?)</a>";
    final String zhengZeTag = "[\\s\\S]*?>(.*?)</";

    List<List<SelectHeadItem>> listSelectHeadItemlist;
    Button bt_select[];
    int selectedNum =-100;

    int pageNum;
    int  positionItemYear=-100;
    int  positionItemRating=-100;
    int  positionItemCountry=-100;
    int  positionItemTags=-100;


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
                title.setText(mDummyItem.getContent());
                tag.setText(StringDo.removeNull(mDummyItem.getTag()));
                score.setText(StringDo.removeNull(mDummyItem.getScore()) + "分");
                String typeStr = "类型：" + mDummyItem.getType();
                if (TextUtils.isEmpty(mDummyItem.getType())) {
                    typeStr = "";
                }
                if (YOBitmap.getmKJBitmap().getCache(StringDo.removeNull(mDummyItem.getImgUrl())).length <= 0) {
                    img.setImageBitmap(null);
                }

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
        bt_select=new Button[4];
        bt_select[0] = (Button) getView().findViewById(R.id.bt_year);
        bt_select[1] = (Button) getView().findViewById(R.id.bt_score_sort);
        bt_select[2] = (Button) getView().findViewById(R.id.bt_national_area);
        bt_select[3] = (Button) getView().findViewById(R.id.bt_film_area);
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                select_list.setVisibility(View.GONE);
                return false;
            }
        });
        for (Button bt:
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
                httpGetFindList(1);
            }
        });
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        select_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (selectedNum){
                    case 0:
                        if (TextUtils.isEmpty(listSelectHeadItemlist.get(selectedNum).get(position).getUrl())){
                            positionItemYear  =-100;
                        }else{
                            positionItemYear  =position;
                        }

                        break;
                    case 1:
                        if (TextUtils.isEmpty(listSelectHeadItemlist.get(selectedNum).get(position).getUrl())){
                            positionItemRating  =-100;
                        }else{
                            positionItemRating  =position;
                        }
                        break;
                    case 2:
                        if (TextUtils.isEmpty(listSelectHeadItemlist.get(selectedNum).get(position).getUrl())){
                            positionItemCountry  =-100;
                        }else{
                            positionItemCountry  =position;
                        }
                        break;
                    case 3:
                        if (TextUtils.isEmpty(listSelectHeadItemlist.get(selectedNum).get(position).getUrl())){
                            positionItemTags  =-100;
                        }else{
                            positionItemTags  =position;
                        }
                        break;
                }
                httpGetFindList(1);
                select_list.setVisibility(View.GONE);
            }
        });
        refreshView.setProgressViewOffset(false, 0, CommonUtil.dip2px(FindFragment.this.getActivity(), 24));
        refreshView.setRefreshing(true);
        httpGetFindList(1);
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(), InfoActivityScrollingActivity.class).putExtra("jsonFindItemInfo", JSON.toJSONString(((ArrayAdapter<DummyItem>) mAdapter).getItem(position))));
    }

    public void httpGetFindList(final int page) {
        KJHttp kjh = new KJHttp();
        String url = HttpUrl.FindList + "page=" + page;
        if(positionItemYear>=0) {
            url = url + HttpUrl.year + listSelectHeadItemlist.get(0).get(positionItemYear).getUrl();
            bt_select[0].setText(listSelectHeadItemlist.get(0).get(positionItemYear).getText());
        }else{
            bt_select[0].setText("年份");
        }
        if(positionItemRating>=0) {
            url = url + HttpUrl.rating + listSelectHeadItemlist.get(1).get(positionItemRating).getUrl();
            bt_select[1].setText(listSelectHeadItemlist.get(1).get(positionItemRating).getText());
        }else{
            bt_select[1].setText("评分");
        }
        if(positionItemCountry>=0) {
            url = url + HttpUrl.country + listSelectHeadItemlist.get(2).get(positionItemCountry).getUrl();
            bt_select[2].setText(listSelectHeadItemlist.get(2).get(positionItemCountry).getText());
        }else{
            bt_select[2].setText("地区");
        }
        if(positionItemTags>=0) {
            url = url + HttpUrl.tags + listSelectHeadItemlist.get(3).get(positionItemTags).getUrl();
            bt_select[3].setText(listSelectHeadItemlist.get(3).get(positionItemTags).getText());
        }else{
            bt_select[3].setText("类型");
        }
        Log.e("url", "url:" + url);

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
                Matcher m = p.matcher(t.toString());
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
                                        // List<String> typeList = new ArrayList<String>();
                                        String typeStr = "";
                                        while (mType.find()) { //循环查找匹配字串
                                            MatchResult mrType = mType.toMatchResult();
                                            for (int groupTypeItem = 1; groupTypeItem <= mrType.groupCount(); groupTypeItem++) {
                                                if (mrType != null && mrType.group(groupTypeItem) != null) {
                                                    if (TextUtils.isEmpty(typeStr)) {
                                                        typeStr = mrType.group(groupTypeItem);
                                                    } else {
                                                        typeStr = typeStr + "|" + mrType.group(groupTypeItem);
                                                    }

                                                }
                                            }
                                        }
                                        map.put("type", typeStr);//找到后group(1)是表达式第一个括号的内容
                                    }

                                    break;

                            }
                        }
                    }
                    result.add(map);
                }
                //adapter = new ArrayAdapter(this,R.layout.view,R.id.textview1,list1);
                if (page == 1) {
                    ((ArrayAdapter) mAdapter).clear();
                    DummyContent.setData(result);
                    pageNum = 1;
                } else {
                    DummyContent.addData(result);
                    pageNum = page;
                }
                ((ArrayAdapter) mAdapter).notifyDataSetChanged();
                if(positionItemYear<0&&positionItemRating<0&&positionItemCountry<0&&positionItemTags<0) {
                    listSelectHeadItemlist = RegularId97.getSelectHeadItem(t.toString());
                }
                ll_head_select.setVisibility(View.VISIBLE);


            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                KJLoger.debug("exception:" + strMsg);
                if (page == 1) {
                    ((ArrayAdapter) mAdapter).clear();
                    pageNum = 1;
                    ((ArrayAdapter) mAdapter).notifyDataSetChanged();
                }
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
    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
        Snackbar.make(view, "是否保存", Snackbar.LENGTH_LONG)
                .setAction("保存", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DummyItem dummyItem = ((ArrayAdapter<DummyItem>) mAdapter).getItem(position);
                        dummyItem.setSaveDate(new Date().getTime());
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
                if (selectedNum ==0&&select_list.getVisibility() == View.VISIBLE) {
                    select_list.setVisibility(View.GONE);
                } else {
                    selectedNum =0;
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

                if (selectedNum ==1&&select_list.getVisibility() == View.VISIBLE) {
                    select_list.setVisibility(View.GONE);
                } else {
                    selectedNum =1;
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

                if (selectedNum ==2&&select_list.getVisibility() == View.VISIBLE) {
                    select_list.setVisibility(View.GONE);
                } else {
                    selectedNum =2;
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

                if (selectedNum ==3&&select_list.getVisibility() == View.VISIBLE) {
                    select_list.setVisibility(View.GONE);
                } else {
                    selectedNum  =3;
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

        }

    }
}
