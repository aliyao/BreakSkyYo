package com.yao.breakskyyo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yao.breakskyyo.tools.ACacheUtil;
import com.yao.breakskyyo.tools.AppInfoUtil;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
    }
    private void init(){

        TextView app_version=(TextView)findViewById(R.id.app_version);
        Button bt_update=(Button)findViewById(R.id.bt_update);
        Object latestVersionCode= ACacheUtil.getAsObject(AboutActivity.this, ACacheUtil.LatestVersionCode);
        Object appVersionCode= AppInfoUtil.getVersionCode(AboutActivity.this);
        if(appVersionCode==null||latestVersionCode==null||appVersionCode.equals(latestVersionCode)){
            bt_update.setVisibility(View.GONE);
        }else{
            bt_update.setVisibility(View.VISIBLE);
            bt_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        String versionName= AppInfoUtil.getVersionName(AboutActivity.this);
        if(TextUtils.isEmpty(versionName)){
            app_version.setVisibility(View.GONE);
        }else{
            app_version.setText(versionName);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
