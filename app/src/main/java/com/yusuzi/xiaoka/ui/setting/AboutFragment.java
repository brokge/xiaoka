package com.yusuzi.xiaoka.ui.setting;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yusuzi.xiaoka.R;
import com.yusuzi.xiaoka.common.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class AboutFragment extends Fragment {

    protected View    rootView;
    protected WebView mWebview;

    WebSettings               mWebSettings;
    ContentLoadingProgressBar mContentLoadingProgressBar;
    private String url;

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_fragment, container, false);
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initWebView();
        bindData();
    }

    private void initWebView () {
        mWebSettings = mWebview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setAllowFileAccess(true); // 允许访问文件
        mWebSettings.setSupportZoom(true); // 支持缩放
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setDomStorageEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle (WebView view, String title) {
                //System.out.println("标题在这里");
            }

            //获取加载进度
            @Override
            public void onProgressChanged (WebView view, int newProgress) {
                mContentLoadingProgressBar.setProgress(newProgress);
                if(newProgress >= 95) {
                    mContentLoadingProgressBar.hide();
                }
            }
        });

        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted (WebView view, String url, Bitmap favicon) {
                System.out.println("开始加载了");

            }

            //设置结束加载函数
            @Override
            public void onPageFinished (WebView view, String url) {
                //endLoading.setText("结束加载了");
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    private void bindView (View rootView) {
        mWebview = rootView.findViewById(R.id.web_view);
        mContentLoadingProgressBar = rootView.findViewById(R.id.load_progressbar);
    }

    private void bindData () {
        url = getArguments().getString(Constants.KEY_URL);
        mWebview.loadUrl(url);
    }

    //销毁Webview
    @Override
    public void onDestroy () {
        if(mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }

}