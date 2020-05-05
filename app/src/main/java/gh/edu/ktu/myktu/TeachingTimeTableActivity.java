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

public class TeachingTimeTableActivity extends AppCompatActivity {
    private WebView teachingTimeTableWebView;
    ProgressBar progressBarTTT;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_time_table);


        teachingTimeTableWebView = findViewById(R.id.webViewTeachTimeTable);
        progressBarTTT  = findViewById(R.id.progressBarTTT);
        refreshLayout   = findViewById(R.id.refreshLayoutTTT);
        WebSettings teachingTimeTableWebSettings = teachingTimeTableWebView.getSettings();



        teachingTimeTableWebSettings.setJavaScriptEnabled(true);
        teachingTimeTableWebView.loadUrl("https://www.ktu.edu.gh/en/category/teaching-timetable/");
        teachingTimeTableWebSettings.setAppCacheEnabled(true);
        teachingTimeTableWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBarTTT.setVisibility(View.VISIBLE);
                refreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBarTTT.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
            }
        });
        teachingTimeTableWebView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (teachingTimeTableWebView.getScrollY() == 0) {
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false);
                }
            }

        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                teachingTimeTableWebView.reload();
            }
        });

        teachingTimeTableWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarTTT.setProgress(newProgress);
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (teachingTimeTableWebView.canGoBack()) {
            teachingTimeTableWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
