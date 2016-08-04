package com.yao.breakskyyo.webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yao.breakskyyo.R;
import com.yao.breakskyyo.tools.ClipboardManagerDo;

public class WebViewActivity extends AppCompatActivity {
    public static String URL_TYPE;
    public static int URL_TYPE_BAIDUYUN = 1;
    WebView webView;
    //FloatingActionButton fab;
    ProgressBar progressBar;
    Snackbar snackbarMima;
    boolean isRunJsTextInputPwdAndClick = true;//只运行一次
    boolean isRunJsSaveBaiduYunClick = true;//只运行一次
    String jsInputPwdUrl = "pan.baidu.com/wap/init?";
    String jsTextInputPwdAndClick = "(function() {\n" +
            "var isHasIdAccessCode=false;\n" +
            "if(document.getElementById(\"accessCode\")!=undefined){\n" +
            "document.getElementById(\"accessCode\").value = \"%1$s\";\n" +
            "isHasIdAccessCode=true;\n" +
            "}\n" +
            "if(isHasIdAccessCode&&document.getElementById(\"getfileBtn\")!=undefined){\n" +
            "document.getElementById(\"getfileBtn\").click();\n" +
            "}\n" +
            "})();";

    String jsSaveBaiduYunUrl1 = "pan.baidu.com/wap/link?";
    String jsSaveBaiduYunUrl2 = "pan.baidu.com/s/";
    String jsSaveBaiduYunClick = "\n" +
            "(function() {\n" +
            "if(document.getElementById(\"saveAll\")!=undefined){\n" +
            "document.getElementById(\"saveAll\").click();\n" +
            "}})();";
    int urlType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fab = (FloatingActionButton) findViewById(R.id.fab);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        urlType = getIntent().getIntExtra(URL_TYPE, 0);
        if (urlType == URL_TYPE_BAIDUYUN) {
            String pwd = getIntent().getStringExtra("mima");
            if (!TextUtils.isEmpty(pwd)) {
                jsTextInputPwdAndClick = jsTextInputPwdAndClick.replace("%1$s", pwd);//替换成密码
            }
        }

        init();
    }

    private void init() {
        // 实例化WebView
        webView = (WebView) this.findViewById(R.id.webView);
        // 设置加载进来的页面自适应手机屏幕
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        String url = getIntent().getStringExtra("url");

        if (TextUtils.isEmpty(url)) {
            finish();
        }
        webView.loadUrl(url);

        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                //  下面这一行保留的时候，原网页仍报错，新网页正常.所以注释掉后，也就没问题了
                //          view.loadUrl(url);.
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webView.canGoBack() && snackbarMima != null) {
                    snackbarMima.dismiss();
                }
                if (urlType == URL_TYPE_BAIDUYUN) {
                    if (isRunJsTextInputPwdAndClick && url.contains(jsInputPwdUrl)) {
                        isRunJsTextInputPwdAndClick = false;
                        webView.loadUrl("javascript:" + jsTextInputPwdAndClick);
                    } else if (isRunJsSaveBaiduYunClick && (url.contains(jsSaveBaiduYunUrl1) || url.contains(jsSaveBaiduYunUrl2))) {
                        isRunJsSaveBaiduYunClick = false;
                        webView.loadUrl("javascript:" + jsSaveBaiduYunClick);
                    }
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
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
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
               WebViewActivity.this.setTitle(title);
            }
        });
        if (urlType == URL_TYPE_BAIDUYUN) {
            if (!TextUtils.isEmpty(getIntent().getStringExtra("mima"))) {
                snackbarMima = Snackbar.make(webView, "密码：" + getIntent().getStringExtra("mima"), Snackbar.LENGTH_INDEFINITE);
                snackbarMima.setAction("隐藏", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbarMima.dismiss();
                    }
                });
                snackbarMima.show();
            }
        }
    }

    private void copy() {
        String text = getIntent().getStringExtra("title") + "---(" + getIntent().getStringExtra("url") + ") ";
        if (!TextUtils.isEmpty(getIntent().getStringExtra("mima"))) {
            text = text + "  (密码--" + getIntent().getStringExtra("mima") + ")";
        }
        ClipboardManagerDo.copy(text, WebViewActivity.this);
        Snackbar.make(webView, "复制成功", Snackbar.LENGTH_LONG).show();
    }

    private void share() {
        // 分享的intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        // 分享的数据类型
        intent.setType("text/plain");
        // 分享的主题
        intent.putExtra(Intent.EXTRA_SUBJECT, getIntent().getStringExtra("title"));
        String text = getIntent().getStringExtra("title") + "---(" + getIntent().getStringExtra("url") + ") ";
        if (!TextUtils.isEmpty(getIntent().getStringExtra("mima"))) {
            text = text + "  (密码--" + getIntent().getStringExtra("mima") + ")";
        }
        // 分享的内容
        intent.putExtra(Intent.EXTRA_TEXT, text);
        // 允许启动新的Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 目标应用寻找对话框的标题
        startActivity(Intent.createChooser(intent, getIntent().getStringExtra("title")));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(urlType==URL_TYPE_BAIDUYUN){
            getMenuInflater().inflate(R.menu.menu_webview_baiduyun, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_webview, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.web_open_browser_text:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(getIntent().getStringExtra("url"));
                intent.setData(content_url);
                startActivity(intent);
                return true;
            case R.id.web_share_text:
                share();
                return true;
            case R.id.web_copy_text:
                copy();
                return true;
            case R.id.web_refresh_text:
                webView.reload();
                return true;
            case R.id.web_back_text:
                finish();

                return true;
            case R.id.web_show_pwd_text:
                if (urlType == URL_TYPE_BAIDUYUN && !TextUtils.isEmpty(getIntent().getStringExtra("mima"))){
                    snackbarMima = Snackbar.make(webView, "密码：" + getIntent().getStringExtra("mima"), Snackbar.LENGTH_INDEFINITE);
                    snackbarMima.setAction("隐藏", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbarMima.dismiss();
                        }
                    });
                    snackbarMima.show();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        webView.pauseTimers();
        super.onPause();
    }

    @Override
    public void onResume() {
        webView.resumeTimers();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
