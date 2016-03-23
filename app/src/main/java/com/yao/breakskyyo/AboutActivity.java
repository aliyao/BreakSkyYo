package com.yao.breakskyyo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yao.breakskyyo.dummy.InfoUpdateApk;
import com.yao.breakskyyo.entity.UpdateApp;
import com.yao.breakskyyo.net.HttpUrl;
import com.yao.breakskyyo.tools.ACacheUtil;
import com.yao.breakskyyo.tools.AppInfoUtil;
import com.yao.breakskyyo.tools.DownloadManagerDo;
import com.yao.breakskyyo.webview.WebViewActivity;

import org.kymjs.kjframe.ui.ViewInject;

public class AboutActivity extends AppCompatActivity {
    Button bt_update;
    UpdateApp updateApkInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
    }
    private void init(){

        TextView app_version=(TextView)findViewById(R.id.app_version);
         bt_update=(Button)findViewById(R.id.bt_update);
        refresh();

        String versionName= AppInfoUtil.getVersionName(AboutActivity.this);
        if(TextUtils.isEmpty(versionName)){
            app_version.setVisibility(View.GONE);
        }else{
            app_version.setText(versionName);
        }
        //HttpDo.updateApp(AboutActivity.this, updateHandle);
    }
    private void refresh(){
        String updateJson= (String)ACacheUtil.getAsObject(AboutActivity.this, ACacheUtil.UpdateAppJson);
        updateApkInfo= JSON.parseObject(updateJson, UpdateApp.class);
        Object appVersionCode= AppInfoUtil.getVersionCode(AboutActivity.this);
        if(updateJson==null||updateApkInfo==null||updateApkInfo.getVersionCode()==0||appVersionCode==null||appVersionCode.equals(updateApkInfo.getVersionCode())){
            bt_update.setVisibility(View.GONE);
        }else{
            bt_update.setVisibility(View.VISIBLE);
            bt_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DownloadManagerDo.download(AboutActivity.this,updateApkInfo.getUrl());
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.update_app) {
           // HttpDo.updateApp(AboutActivity.this, updateHandle);
            startActivity(new Intent(this, WebViewActivity.class).putExtra("url", HttpUrl.UpdateAppWeb));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    Handler updateHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    ViewInject.longToast("网络不给力");
                    break;
                case 1:
                    refresh();
                    break;
            }
        }
    };
}
