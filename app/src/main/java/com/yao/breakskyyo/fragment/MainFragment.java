package com.yao.breakskyyo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yao.breakskyyo.InfoActivityScrollingActivity;
import com.yao.breakskyyo.MainActivity;
import com.yao.breakskyyo.R;
import com.yao.breakskyyo.db.DummyItemDb;
import com.yao.breakskyyo.dummy.DummyItem;
import com.yao.breakskyyo.net.HttpUrl;
import com.yao.breakskyyo.tools.RegularId97;
import com.yao.breakskyyo.tools.StringDo;
import com.yao.breakskyyo.tools.YOBitmap;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment implements  AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    SwipeRefreshLayout refreshView;
    private ArrayAdapter mAdapter;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    public void init() {
        mAdapter = new ArrayAdapter<List<DummyItem>>(getActivity(),
                R.layout.main_list_item) {
            @Override
            public List<DummyItem> getItem(int position) {
                return super.getItem(position);
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.main_list_item, parent, false);
                } else {
                    view = convertView;
                }
                List<DummyItem> dummyItemList = getItem(position);
                ImageView img = (ImageView) view.findViewById(R.id.image);
                TextView title = (TextView) view.findViewById(R.id.title);
                TextView browseNum = (TextView) view.findViewById(R.id.browseNum);
                ImageView img2 = (ImageView) view.findViewById(R.id.image2);
                TextView title2 = (TextView) view.findViewById(R.id.title2);
                TextView browseNum2 = (TextView) view.findViewById(R.id.browseNum2);
                View rl_item2=view.findViewById(R.id.browseNum2);

                title.setText(dummyItemList.get(0).getContent());
                if (YOBitmap.getmKJBitmap().getCache(StringDo.removeNull(dummyItemList.get(0).getImgUrl())).length <= 0) {
                    img.setImageBitmap(null);
                }
                browseNum.setText(StringDo.removeNull(dummyItemList.get(0).getBrowseNum()));
                YOBitmap.getmKJBitmap().display(img, StringDo.removeNull(dummyItemList.get(0).getImgUrl()));

                if(dummyItemList.size()>0&&dummyItemList.get(1)!=null){
                    title2.setText(dummyItemList.get(1).getContent());
                    if (YOBitmap.getmKJBitmap().getCache(StringDo.removeNull(dummyItemList.get(1).getImgUrl())).length <= 0) {
                        img2.setImageBitmap(null);
                    }
                    browseNum2.setText(StringDo.removeNull(dummyItemList.get(1).getBrowseNum()));
                    YOBitmap.getmKJBitmap().display(img2, StringDo.removeNull(dummyItemList.get(1).getImgUrl()));
                    rl_item2.setVisibility(View.VISIBLE);
                }else{
                    rl_item2.setVisibility(View.INVISIBLE);
                }

                return view;
            }
        };
        mListView = (ListView) getView().findViewById(R.id.lv_main);
        refreshView = (SwipeRefreshLayout) getView().findViewById(R.id.refreshView);
        mListView.setAdapter(mAdapter);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpGet();
            }
        });
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        httpGet();
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(), InfoActivityScrollingActivity.class).putExtra("jsonFindItemInfo", JSON.toJSONString(((ArrayAdapter<DummyItem>) mAdapter).getItem(position))));
    }

    public void httpGet() {
        KJHttp kjh = new KJHttp();
        kjh.get(HttpUrl.MainList, new HttpCallBack() {
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
                List<List<DummyItem>>  dummyItemListList=RegularId97.getHotNewItem(t.toString());
                if(dummyItemListList!=null&&dummyItemListList.size()==2&&dummyItemListList.get(0)!=null){
                    if(dummyItemListList.get(0).size()%2!=0){
                        dummyItemListList.get(0).add(null);
                    }
                    if(dummyItemListList.get(1).size()%2!=0){
                        dummyItemListList.get(1).add(null);
                    }
                    List<DummyItem>  dummyItemList=new ArrayList<DummyItem>();
                    dummyItemList.addAll(dummyItemListList.get(0));
                    dummyItemList.addAll(dummyItemListList.get(1));
                    List<List<DummyItem>>  dummyItemListListResult= new ArrayList<List<DummyItem>>();
                    for (int num=1; num< dummyItemList.size();num=num+2) {
                        List<DummyItem> dummyItemListResult=new ArrayList<DummyItem>();
                        dummyItemListResult.add(dummyItemList.get(num-1));
                        dummyItemListResult.add(dummyItemList.get(num));
                        dummyItemListListResult.add(dummyItemListResult);
                    }
                    mAdapter.clear();
                    mAdapter.addAll(dummyItemListListResult);
                }

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                KJLoger.debug("exception:" + strMsg);
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
        Snackbar.make(view, "是否保存", Snackbar.LENGTH_LONG)
                .setAction("保存", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DummyItem dummyItem = ((ArrayAdapter<DummyItem>) mAdapter).getItem(position);
                        dummyItem.setSaveDate(new Date().getTime());
                        String tip = "保存失败";
                        switch (DummyItemDb.save(dummyItem, MainFragment.this.getActivity())) {
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
}