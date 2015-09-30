package com.yao.breakskyyo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yao.breakskyyo.fragment.FindFragment;
import com.yao.breakskyyo.fragment.SaveFragment;
import com.yao.breakskyyo.net.HttpDo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , FindFragment.OnFragmentInteractionListener, SaveFragment.OnFragmentInteractionListener {
    Fragment fragments[];
    boolean isFinish;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_slideshow).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        init();

    }
    private void init(){
        toolbar.setSubtitle(R.string.title_section1);
        fragments=new Fragment[2];
        fragments[0]=FindFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        if(!fragments[0].isAdded()){
            fragmentTransaction.replace(R.id.showLayout, fragments[0]);
        }
        fragmentTransaction.show(fragments[0]);
        if(fragments[1]!=null&&fragments[1].isAdded()){
            fragmentTransaction.hide(fragments[1]);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        HttpDo.updateApp(this, null);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            isFinish=true;
            finish();
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_slideshow) {
            showPage(0);
        } else if (id == R.id.nav_save) {
            showPage(1);
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this,AboutActivity.class));
        } /*else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showPage(int page){
        switch (page){
            case 1:
                if(fragments[page]==null){
                    fragments[page]=SaveFragment.newInstance();
                }
                toolbar.setSubtitle(R.string.title_section2);
                break;
            case 0:
                toolbar.setSubtitle(R.string.title_section1);
                break;

        }
        FragmentTransaction trx = getFragmentManager().beginTransaction();

        for (int index=0;index<fragments.length;index++) {
            if (fragments[index]!=null&&!fragments[index].isAdded()) {
                trx.add(R.id.showLayout, fragments[index]);
            }
            if(page==index){
                trx.show(fragments[index]);
            }else{
                if (fragments[index]!=null&&fragments[index].isAdded()) {
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
        if(isFinish){
            super.finish();
        }else{
            Snackbar.make(findViewById(R.id.fab), "是否退出", Snackbar.LENGTH_LONG)
                    .setAction("退出", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isFinish=true;
                            finish();
                        }
                    }).show();
        }

    }
    public void updateSaveFragment(){
        if(fragments[1]!=null&&fragments[1].isAdded()){
            ((SaveFragment)fragments[1]).update();
        }
    }




}
