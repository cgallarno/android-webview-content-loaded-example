package com.self.webviews;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String htmlContent = "<html><head></head><body><h1>Android <span id=colorMePink>WebView</span> onContentLoaded Event</h1><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam elit risus, mollis et libero at, imperdiet congue purus. Aliquam erat volutpat. Phasellus sed sodales dui. Duis nibh lectus, venenatis in elit vel, lacinia pretium erat. Donec vitae sem tempus, fermentum ligula sed, placerat ipsum. Nam hendrerit leo mauris, ut ornare sapien venenatis a. Pellentesque eleifend, arcu a congue euismod, neque eros pharetra nibh, id pretium massa velit malesuada turpis. Proin sed cursus massa. Nulla pellentesque et leo at auctor. Pellentesque pretium mauris at posuere consequat.</p><script src=https://cdnjs.cloudflare.com/ajax/libs/zepto/1.1.6/zepto.js></script><script>function checkState(){console.log(document.readyState),\"complete\"==document.readyState?notifyAndroid(\"event=DomLoaded\"):setTimeout(checkState,200)}function applyStyles(){$(\"#colorMePink\").css({color:\"pink\"})}function notifyAndroid(o){location.replace(\"myapp://notifications?\"+o)}document.addEventListener(\"DOMContentLoaded\",function(o){applyStyles(),checkState()});</script></body></html>";
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webView);
        setupWebview();
        mWebView.loadData(htmlContent, "text/html; charset=UTF-8", "utf-8");
    }

    void setupWebview() {
        mWebView.setVisibility(View.INVISIBLE); //Comment out this line and you'll be able to see the webview render
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, url);

                if (url.startsWith("myapp://notifications")) {
                    Uri uri = Uri.parse(url);
                    if (uri.getQueryParameter("event").equals("DomLoaded")) {
                        handleWebViewOnLoad();
                    }

                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
        });
    }

    void handleWebViewOnLoad() {
        Log.d(TAG, "handleOnLoad");
        Toast.makeText(this, "The Webview has Finised Loading", Toast.LENGTH_LONG).show();
        mWebView.setVisibility(View.VISIBLE);
    }
}
