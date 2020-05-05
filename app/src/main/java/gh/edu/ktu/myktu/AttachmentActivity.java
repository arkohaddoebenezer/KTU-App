package gh.edu.ktu.myktu;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AttachmentActivity extends AppCompatActivity {
    private WebView attachmentWebView;
    ProgressBar attachmentWebViewProgressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment);

        attachmentWebView = findViewById(R.id.webViewAttachment);
        attachmentWebViewProgressBar = findViewById(R.id.progressBarAttachment);
        WebSettings attachmentWebSettings = attachmentWebView.getSettings();
        swipeRefreshLayout = findViewById(R.id.refreshLayoutAttachment);


        attachmentWebSettings.setJavaScriptEnabled(true);
        attachmentWebView.loadUrl("https://ilo.ktu.edu.gh/portal-login/");
        attachmentWebSettings.setAppCacheEnabled(true);
        attachmentWebSettings.setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/attach/cache");
        attachmentWebSettings.setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/attach/database");
        attachmentWebSettings.getDatabaseEnabled();

        attachmentWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                attachmentWebViewProgressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view,url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                attachmentWebViewProgressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view,url,favicon);
                swipeRefreshLayout.setRefreshing(true);
            }
        });


        attachmentWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                attachmentWebViewProgressBar.setProgress(newProgress);
            }
        });

        attachmentWebView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (attachmentWebView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }

        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                attachmentWebView.reload();
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (attachmentWebView.canGoBack()) {
            attachmentWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
