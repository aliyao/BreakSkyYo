package com.yao.breakskyyo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.yao.breakskyyo.fragment.FindFragment;
import com.yao.breakskyyo.fragment.MainFragment;
import com.yao.breakskyyo.fragment.SaveFragment;
import com.yao.breakskyyo.net.HttpDo;
import com.yao.breakskyyo.net.HttpUrl;
import com.yao.breakskyyo.tools.ACacheUtil;
import com.yao.breakskyyo.tools.AppInfoUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FindFragment.OnFragmentInteractionListener, SaveFragment.OnFragmentInteractionListener,MainFragment.OnFragmentInteractionListener {
    Fragment fragments[];
    boolean isFinish;
    Toolbar toolbar;
    static MainActivity mainActivity;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity=this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_main).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        init();

    }

    private void init() {
        toolbar.setSubtitle(R.string.title_section0);
        fragments = new Fragment[3];
        fragments[0] = MainFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        if (!fragments[0].isAdded()) {
            fragmentTransaction.replace(R.id.showLayout, fragments[0]);
        }
        fragmentTransaction.show(fragments[0]);
        if (fragments[1] != null && fragments[1].isAdded()) {
            fragmentTransaction.hide(fragments[1]);
        }
        if (fragments[2] != null && fragments[2].isAdded()) {
            fragmentTransaction.hide(fragments[2]);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        HttpDo.updateApp(this, null);
        phoneInfo();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        phoneInfo();
    }

    private void phoneInfo() {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        ACacheUtil.put(MainActivity.this, ACacheUtil.ScreenHeight, screenHeight);
        ACacheUtil.put(MainActivity.this, ACacheUtil.ScreenWidth, screenWidth);
        int contentTop = screenHeight - findViewById(R.id.showLayout).getHeight();
        ACacheUtil.put(MainActivity.this, ACacheUtil.ContentTop, contentTop);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
       /* MenuItem actionOpenBaidudisk = menu.findItem(R.id.action_open_baidudisk);
        MenuItem actionOpenXunlei = menu.findItem(R.id.action_open_xunlei);
        if () {
            actionOpenBaidudisk.setVisible(true);
        } else {
            actionOpenBaidudisk.setVisible(false);
        }
        if () {
            actionOpenXunlei.setVisible(true);
        } else {
            actionOpenXunlei.setVisible(false);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                return true;
            case R.id.action_settings:
                isFinish = true;
                finish();
                System.exit(0);
                return true;
            case R.id.action_open_baidudisk:
                try {
                    PackageManager packageManager = getPackageManager();
                    Intent intent= packageManager.getLaunchIntentForPackage(AppInfoUtil.BaiduDiskPackageName);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.fab), "你还没有安装百度云，为了保存和在线观看视频，现在安装百度云！", Snackbar.LENGTH_LONG)
                            .setAction("安装", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent viewIntent = new
                                            Intent(Intent.ACTION_VIEW, Uri.parse(HttpUrl.SearchBaiduUrl+"百度云"));
                                   startActivity(viewIntent);
                                }
                            }).show();
                }
                return true;
            case R.id.action_open_xunlei:
                try {
                    PackageManager packageManager = getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage(AppInfoUtil.XunleiPackageName);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.fab), "你还没有安装迅雷，为了下载和在线观看视频，现在安装迅雷！", Snackbar.LENGTH_LONG)
                            .setAction("安装", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent viewIntent = new
                                            Intent(Intent.ACTION_VIEW, Uri.parse(HttpUrl.SearchBaiduUrl+"android迅雷"));
                                    startActivity(viewIntent);
                                }
                            }).show();
                }
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            showPage(0);
        } else if (id == R.id.nav_slideshow) {
            showPage(1);
        } else if (id == R.id.nav_save) {
            showPage(2);
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } /*else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void toFindItemFragment(){
        navigationView.getMenu().findItem(R.id.nav_slideshow).setChecked(true);
        showPage(1);
    }

    private void showPage(int page) {
        switch (page) {
            case 2:
                if (fragments[page] == null) {
                    fragments[page] = SaveFragment.newInstance();
                }
                toolbar.setSubtitle(R.string.title_section2);
                break;
            case 1:
                if (fragments[page] == null) {
                    fragments[page] = FindFragment.newInstance();
                }
                toolbar.setSubtitle(R.string.title_section1);
                break;
            case 0:
                toolbar.setSubtitle(R.string.title_section0);
                break;

        }
        FragmentTransaction trx = getFragmentManager().beginTransaction();

        for (int index = 0; index < fragments.length; index++) {
            if (fragments[index] != null && !fragments[index].isAdded()) {
                trx.add(R.id.showLayout, fragments[index]);
            }
            if (page == index) {
                trx.show(fragments[index]);
            } else {
                if (fragments[index] != null && fragments[index].isAdded()) {
                    trx.hide(fragments[index]);
                }
            }

        }
        trx.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void finish() {
        if (isFinish) {
            super.finish();
        } else {
            Snackbar.make(findViewById(R.id.fab), "是否退出", Snackbar.LENGTH_LONG)
                    .setAction("退出", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isFinish = true;
                            finish();
                        }
                    }).show();
        }

    }

    public void updateSaveFragment() {
        if (fragments[2] != null && fragments[2].isAdded()) {
            ((SaveFragment) fragments[2]).update();
        }
    }


}
