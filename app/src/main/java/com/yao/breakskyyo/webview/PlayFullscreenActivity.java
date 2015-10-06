package com.yao.breakskyyo.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.yao.breakskyyo.R;
import com.yao.breakskyyo.net.HttpUrl;
import com.yao.breakskyyo.tools.ACacheUtil;
import com.yao.breakskyyo.tools.ClipboardManagerDo;
import com.yao.breakskyyo.webview.util.SystemUiHider;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class PlayFullscreenActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    WebView webView;
    FloatingActionButton fab;
    ProgressBar progressBar;
    private FrameLayout frameLayout = null;
    private WebChromeClient chromeClient = null;
    private View myView = null;
    private WebChromeClient.CustomViewCallback myCallBack = null;


    public PlayFullscreenActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_fullscreen);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayFullscreenActivity.this);
                //    指定下拉列表的显示数据
                String[] toDo = {"打开浏览器", "分享", "复制", "帮助", "刷新", "退出"};
                //    设置一个下拉的列表选择项
                builder.setItems(toDo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                Uri content_url = Uri.parse(getIntent().getStringExtra("url"));
                                intent.setData(content_url);
                                startActivity(intent);
                                break;
                            case 1:
                                share();
                                break;
                            case 2:
                                copy();
                                break;
                            case 3:
                               /* Intent intentHellp = new Intent();
                                intentHellp.setAction(Intent.ACTION_VIEW);
                                Uri hellp_url = Uri.parse(HttpUrl.HellpUrl);
                                intentHellp.setData(hellp_url);
                                startActivity(intentHellp);*/
                                webView.loadUrl(HttpUrl.HellpUrl);
                                break;
                            case 4:
                                webView.reload();
                                break;
                            case 5:
                                finish();
                                break;
                        }


                    }
                });
                builder.show();

            }
        });
        frameLayout = (FrameLayout)findViewById(R.id.framelayout);
        // 实例化WebView
        webView = (WebView) this.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        webView.setWebViewClient(new MyWebviewCient());

        chromeClient = new MyChromeClient();

        webView.setWebChromeClient(chromeClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);

        final String USER_AGENT_STRING = webView.getSettings().getUserAgentString() + " Rong/2.0";
        webView.getSettings().setUserAgentString(USER_AGENT_STRING);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setLoadWithOverviewMode(true);

        if(savedInstanceState != null){
            webView.restoreState(savedInstanceState);
        }
        init();
    }


    private void init() {
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, webView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = fab.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            fab.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            fab.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // 设置加载进来的页面自适应手机屏幕
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setTextSize(WebSettings.TextSize.SMALLEST);
        String url =getIntent().getStringExtra("url");

        if(TextUtils.isEmpty(url)){
            finish();
        }

        webView.setBackgroundColor(0); // 设置背景色
        webView.getSettings().setDefaultFontSize(18);
        //webView.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
        //webView.loadUrl(url);


         httpGetHtml();
    }

    private void copy() {
        String text = getIntent().getStringExtra("url");
        ClipboardManagerDo.copy(text, PlayFullscreenActivity.this);
        Snackbar.make(webView, "复制成功", Snackbar.LENGTH_LONG).show();
    }
    private void share() {
        // 分享的intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        // 分享的数据类型
        intent.setType("text/plain");
        // 分享的主题
        intent.putExtra(Intent.EXTRA_SUBJECT,getIntent().getStringExtra("title"));
        String text = getIntent().getStringExtra("title") + "---(" + getIntent().getStringExtra("url") + ") ";

        // 分享的内容
        intent.putExtra(Intent.EXTRA_TEXT, text);
        // 允许启动新的Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 目标应用寻找对话框的标题
        startActivity(Intent.createChooser(intent, getIntent().getStringExtra("title")));
    }




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public class myWebChromeClient extends WebChromeClient {
        //private View xprogressvideo;

        // 播放网络视频时全屏会被调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            webView.setVisibility(View.INVISIBLE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
           /* if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
           // video_fullView.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;*/
           // video_fullView.setVisibility(View.VISIBLE);
        }

        /*// 视频播放退出全屏会被调用的
        @Override
        public void onHideCustomView() {
            if (xCustomView == null)// 不是全屏播放状态
                return;

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);
            video_fullView.removeView(xCustomView);
            xCustomView = null;
            video_fullView.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();
            webView.setVisibility(View.VISIBLE);
        }
*/
        // 视频加载时进程loading
      /*  @Override
        public View getVideoLoadingProgressView() {
            if (xprogressvideo == null) {
                LayoutInflater inflater = LayoutInflater
                        .from(PlayFullscreenActivity.this);
                xprogressvideo = inflater.inflate(
                        R.layout.video_loading_progress, null);
            }
            return xprogressvideo;
        }*/
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
  /*  public boolean inCustomView() {
        return (xCustomView != null);
    }*/

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
       // xwebchromeclient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        webView.onResume();
        webView.resumeTimers();

        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.onDestroy();
        webView.loadUrl("about:blank");
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.destroy();
        webView = null;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
         /*//   if (inCustomView()) {
                // webViewDetails.loadUrl("about:blank");
                hideCustomView();
                return true;
            } else {*/
                webView.loadUrl("about:blank");
              finish();
         //   }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(myView == null){
            super.onBackPressed();
        }
        else{
            chromeClient.onHideCustomView();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        webView.saveState(outState);
    }

    public void addJavaScriptMap(Object obj, String objName){
        webView.addJavascriptInterface(obj, objName);
    }

    public class MyWebviewCient extends WebViewClient{
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view,
                                                          String url) {
            WebResourceResponse response = null;
            response = super.shouldInterceptRequest(view, url);
            return response;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

          //  webView.loadUrl(("javascript:" + QuanpingJS));
        }

    }
    //final String QuanpingJS="   <script type=\"text/javascript\"> document.write(\"yoyo\");</script>";
    final String QuanpingJS="   <script type=\"text/javascript\">  document.getElementById(\"video_1\").style.height='(px)';</script>";

    public class MyChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                if (progressBar.getVisibility() != View.VISIBLE) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            /*if(myView != null){
                callback.onCustomViewHidden();
                return;
            }
            frameLayout.removeView(webView);
            frameLayout.addView(view);
            myView = view;
            myCallBack = callback;*/
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null ;
                return;
            }

            long id = Thread.currentThread().getId();
           // WrtLog. v("WidgetChromeClient", "rong debug in showCustomView Ex: " + id);

            ViewGroup parent = (ViewGroup) webView.getParent();
            String s = parent.getClass().getName();
            //WrtLog. v("WidgetChromeClient", "rong debug Ex: " + s);
            parent.removeView( webView);
            parent.addView(view);
            myView = view;
            myCallback = callback;
            chromeClient = this ;
        }
        private View myView = null;
        private CustomViewCallback myCallback = null;

        @Override
        public void onHideCustomView() {
            /*if(myView == null){
                return;
            }
            frameLayout.removeView(myView);
            myView = null;
            frameLayout.addView(webView);
            myCallBack.onCustomViewHidden();*/
            long id = Thread.currentThread().getId();
            // WrtLog. v("WidgetChromeClient", "rong debug in hideCustom Ex: " + id);


            if (myView != null) {

                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                }

                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);
                parent.addView(webView);
                myView = null;

            }
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            // TODO Auto-generated method stub
            Log.d("ZR", consoleMessage.message() + " at " + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber());
            return super.onConsoleMessage(consoleMessage);
        }


    }
   public void httpGetHtml() {
        KJHttp kjh = new KJHttp();
        String url =getIntent().getStringExtra("url");
        kjh.get(url, new HttpCallBack() {
            @Override
            public void onPreStart() {
                super.onPreStart();
                KJLoger.debug("在请求开始之前调用");
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                int screenHeight=(int) ACacheUtil.getAsObject(PlayFullscreenActivity.this, ACacheUtil.ScreenWidth)-(int)ACacheUtil.getAsObject(PlayFullscreenActivity.this, ACacheUtil.ContentTop)-10;
                String htmlstr=t;
                if(screenHeight>100){
                     htmlstr=t.replace("465px",screenHeight+"px");
                }
                webView.loadDataWithBaseURL("http://m.acfun.tv.id97.com", htmlstr, "text/html", "utf-8", null);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                KJLoger.debug("exception:" + strMsg);
                webView.loadUrl(getIntent().getStringExtra("url"));
            }


            @Override
            public void onFinish() {
                super.onFinish();
                KJLoger.debug("请求完成，不管成功或者失败都会调用");
            }


        });

    }
}
