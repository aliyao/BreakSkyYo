package com.yao.breakskyyo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yao.breakskyyo.dummy.DummyItem;
import com.yao.breakskyyo.dummy.HelpItem;
import com.yao.breakskyyo.net.HttpUrl;
import com.yao.breakskyyo.tools.StringDo;
import com.yao.breakskyyo.tools.YOBitmap;
import com.yao.breakskyyo.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listview=(ListView)findViewById(R.id.list);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        List<HelpItem> listHelpItem=new ArrayList<>();
        listHelpItem.add(new HelpItem(R.string.zx_title_text, R.string.zx_instruction_text,1));
        String []a_zx_array= getResources().getStringArray(R.array.a_zx_array);
        int []a_zx_img= {R.drawable.a_zx1,R.drawable.a_zx2,R.drawable.a_zx3};
        for (int i=0; i<a_zx_array.length;i++) {
            listHelpItem.add(new HelpItem(a_zx_img[i],a_zx_array[i]));
        }

        listHelpItem.add(new HelpItem(R.string.baidudisk_title_text, R.string.baidudisk_instruction_text,2));
        String []b_baidudisk_array= getResources().getStringArray(R.array.b_baidudisk_array);

        int []b_baidudisk_img= {R.drawable.b_baidudisk1,R.drawable.b_baidudisk2,R.drawable.b_baidudisk3,R.drawable.b_baidudisk4,R.drawable.b_baidudisk5,R.drawable.b_baidudisk6,R.drawable.b_baidudisk7,R.drawable.b_baidudisk8,R.drawable.b_baidudisk9};
        for (int i=0; i<b_baidudisk_array.length;i++) {
            listHelpItem.add(new HelpItem(b_baidudisk_img[i],b_baidudisk_array[i]));
        }

        listHelpItem.add(new HelpItem(R.string.xunlei_title_text,R.string.xunlei_instruction_text,3));
        String []c_xunlei_array= getResources().getStringArray(R.array.c_xunlei_array);
        int []c_xunlei_img= {R.drawable.c_xunlei1,R.drawable.c_xunlei2,R.drawable.c_xunlei3,R.drawable.c_xunlei4,R.drawable.c_xunlei5,R.drawable.c_xunlei6,R.drawable.c_xunlei7,R.drawable.c_xunlei8};
        for (int i=0; i<c_xunlei_array.length;i++) {
            listHelpItem.add(new HelpItem(c_xunlei_img[i],c_xunlei_array[i]));
        }
        listHelpItem.add(new HelpItem(R.string.computer_title_text,R.string.computer_instruction_text,4));
        listHelpItem.add(new HelpItem(R.string.other_title_text,R.string.other_instruction_text,5));

        ArrayAdapter adapter = new ArrayAdapter<HelpItem>(this,R.layout.help_list_item,listHelpItem){
            @Override
            public HelpItem getItem(int position) {
                return super.getItem(position);
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.help_list_item, parent, false);
                } else {
                    view = convertView;
                }
                HelpItem mHelpItem=getItem( position);
                TextView titleTv=(TextView)view.findViewById(R.id.title);
                TextView contentTv=(TextView)view.findViewById(R.id.content);
                ImageView help_img=(ImageView)view.findViewById(R.id.help_img);
                TextView instruction=(TextView)view.findViewById(R.id.instruction);
                Button toWebBt=(Button)view.findViewById(R.id.toWebBt);
                if (mHelpItem.getTitle()<=0){
                    titleTv.setVisibility(View.GONE);
                }else{
                    titleTv.setVisibility(View.VISIBLE);
                    titleTv.setText(mHelpItem.getTitle());
                }

                if(mHelpItem.getInstruction()<=0){
                    instruction.setVisibility(View.GONE);
                }else{
                    instruction.setVisibility(View.VISIBLE);
                    instruction.setText(mHelpItem.getInstruction());
                }

                if(mHelpItem.getImageResourceId()<=0){
                    help_img.setVisibility(View.GONE);
                }else{
                    help_img.setVisibility(View.VISIBLE);
                    help_img.setImageResource(mHelpItem.getImageResourceId());
                }

                if(mHelpItem.getPlayType()!=4){
                    toWebBt.setOnClickListener(null);
                    toWebBt.setVisibility(View.GONE);
                }else{
                    toWebBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("url", HttpUrl.HellpUrl));
                        }
                    });
                    toWebBt.setVisibility(View.VISIBLE);
                }

                if(TextUtils.isEmpty(mHelpItem.getContent())){
                    contentTv.setVisibility(View.GONE);
                }else{
                    contentTv.setVisibility(View.VISIBLE);
                    contentTv.setText(mHelpItem.getContent());
                }
                return view;
            }
        };
        listview.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help, menu);
        return true;
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_zaixian) {
            listview.setSelection(0);
        } else  if (id == R.id.nav_baidudisk) {
            listview.setSelection(4);
        } else if (id == R.id.nav_xunlei) {
            listview.setSelection(14);
        } else if (id == R.id.nav_computer) {
            listview.setSelection(23);
        } else if (id == R.id.nav_other) {
            listview.setSelection(24);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
