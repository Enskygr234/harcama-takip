package com.enskygr234.harcamatakip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.webkit.WebViewAssetLoader;

/**
 * Harcama Takip'i (assets/index.html) tam ekran bir WebView içinde açar.
 * Sayfa https://appassets.androidplatform.net adresinden sunulur; böylece
 * kayıtlar (localStorage) kalıcı olarak telefonda saklanır.
 */
public class MainActivity extends Activity {

    private static final String HOST = "appassets.androidplatform.net";
    private static final String START = "https://" + HOST + "/assets/index.html";

    // Geri tuşu: önce açık pencereyi kapat, sonra proje sayfasından ana ekrana dön,
    // ana ekrandaysa uygulamadan çık.
    private static final String BACK_JS =
            "(function(){"
            + "var d=Array.prototype.slice.call(document.querySelectorAll('dialog[open]')).pop();"
            + "if(d){if(d.id==='meslekDlg')return 'stay';"
            + "if(d.id==='kilit'&&d.dataset.mode==='unlock')return 'exit';"
            + "d.close();return 'closed';}"
            + "if(history.state&&(history.state.p||history.state.f)){history.back();return 'back';}"
            + "if(typeof view!=='undefined'&&view!=='home'&&typeof show==='function'){show('home');return 'home';}"
            + "return 'exit';})()";

    private WebView web;
    private WebViewAssetLoader loader;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Son uygulamalar ekranında gelir-gider görünmesin (Android 13+).
        if (Build.VERSION.SDK_INT >= 33) {
            setRecentsScreenshotEnabled(false);
        }

        loader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();

        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(false);
        s.setAllowContentAccess(false);
        s.setTextZoom(100);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return loader.shouldInterceptRequest(request.getUrl());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri url = request.getUrl();
                if (HOST.equals(url.getHost())) {
                    return false;
                }
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, url));
                } catch (Exception ignored) {
                    // Bağlantıyı açacak uygulama yoksa hiçbir şey yapma.
                }
                return true;
            }
        });

        setContentView(web);
        if (savedInstanceState == null || web.restoreState(savedInstanceState) == null) {
            web.loadUrl(START);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        web.saveState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        web.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        web.onResume();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {
        web.evaluateJavascript(BACK_JS, value -> {
            if ("\"exit\"".equals(value) || value == null || "null".equals(value)) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (web != null) {
            web.destroy();
        }
        super.onDestroy();
    }
}
