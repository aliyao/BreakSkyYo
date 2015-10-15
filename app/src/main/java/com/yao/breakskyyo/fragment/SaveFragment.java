package com.yao.breakskyyo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yao.breakskyyo.InfoActivityScrollingActivity;
import com.yao.breakskyyo.R;
import com.yao.breakskyyo.db.DummyItemDb;
import com.yao.breakskyyo.dummy.DummyItem;
import com.yao.breakskyyo.tools.StringDo;
import com.yao.breakskyyo.tools.YOBitmap;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveFragment extends Fragment implements AbsListView.OnItemClickListener, AbsListView.OnItemLongClickListener{
    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;
    SwipeRefreshLayout refreshView;
    private ListAdapter mAdapter;
    TextView tv_bottom_text;

    public static SaveFragment newInstance() {
        SaveFragment fragment = new SaveFragment();
        return fragment;
    }

    public SaveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void init(){

        mAdapter = new ArrayAdapter<DummyItem>(getActivity(),
                R.layout.finditem_list_item) {
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


                title.setText( mDummyItem.getContent());
                tag.setText(StringDo.removeNull(mDummyItem.getTag()));
                score.setText(StringDo.removeNull(mDummyItem.getScore())+"分");
                String typeStr="类型："+mDummyItem.getType();
                if(TextUtils.isEmpty(mDummyItem.getType())){
                    typeStr="";
                }
                type.setText(StringDo.removeNull(typeStr));
                YOBitmap.getmKJBitmap().display(img,StringDo.removeNull(mDummyItem.getImgUrl()));
                return view;
            }
        };
        mListView = (AbsListView) getView().findViewById(android.R.id.list);
        refreshView=(SwipeRefreshLayout) getView().findViewById(R.id.refreshView);
        tv_bottom_text= (TextView) getView().findViewById(R.id.tv_bottom_text);
        tv_bottom_text.setVisibility(View.INVISIBLE);
        getView().findViewById(R.id.ll_head_select).setVisibility(View.GONE);
        getView().findViewById(R.id.select_list).setVisibility(View.GONE);
        mListView.setAdapter(mAdapter);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListByPage(1);
            }
        });
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        getListByPage(1);
    }
    public void getListByPage(int page){
       List<DummyItem> list= DummyItemDb.findList(getActivity());
        if (page == 1) {
            ((ArrayAdapter) mAdapter).clear();
            if(list==null||list.size()<1){
                FloatingActionButton fab = (FloatingActionButton) this.getActivity().findViewById(R.id.fab);
                Snackbar.make(fab, "没有记录", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else {
                ((ArrayAdapter) mAdapter).addAll(list);
            }
        } else {
            //((ArrayAdapter) mAdapter).addAll();
        }
        ((ArrayAdapter) mAdapter).notifyDataSetChanged();
        refreshView.setRefreshing(false);
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent,final View view, final int position, long id) {
        Snackbar.make(view, "是否删除保存", Snackbar.LENGTH_LONG)
                .setAction("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isDbDoTrue = DummyItemDb.delete(((ArrayAdapter<DummyItem>) mAdapter).getItem(position).getId(), SaveFragment.this.getActivity());
                        String tip = "删除成功！";
                        if (!isDbDoTrue) {
                            tip = "删除失败！";
                        } else {
                            getListByPage(1);
                        }
                        Snackbar.make(view, tip, Snackbar.LENGTH_LONG).show();
                    }
                }).show();
        return true;
    }
    public void update(){
        getListByPage(1);
    }
}
