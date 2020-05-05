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

public class AlumniActivity extends AppCompatActivity {
    private WebView AlumniWebView;
    ProgressBar AlumniWebViewProgressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni);
        AlumniWebView = findViewById(R.id.webViewAlumni);
        AlumniWebViewProgressBar = findViewById(R.id.progressBarAlumni);
        WebSettings AlumniWebSettings = AlumniWebView.getSettings();
        swipeRefreshLayout = findViewById(R.id.refreshLayoutAlumni);


        AlumniWebSettings.setJavaScriptEnabled(true);
        AlumniWebView.loadUrl("https://alumni.ktu.edu.gh/");
        AlumniWebSettings.setAppCacheEnabled(true);
        AlumniWebSettings.setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/Alumni/cache");
        AlumniWebSettings.setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/Alumni/database");
        AlumniWebSettings.getDatabaseEnabled();

        AlumniWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                AlumniWebViewProgressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view,url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                AlumniWebViewProgressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view,url,favicon);
                swipeRefreshLayout.setRefreshing(true);
            }
        });


        AlumniWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                AlumniWebViewProgressBar.setProgress(newProgress);
            }
        });

        AlumniWebView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (AlumniWebView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }

        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AlumniWebView.reload();
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (AlumniWebView.canGoBack()) {
            AlumniWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
