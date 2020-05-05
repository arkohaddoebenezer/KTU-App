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

public class ExamTimeTableActivity extends AppCompatActivity {
    private WebView examTimeTableWebView;
    ProgressBar progressBarExamTimeTable;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_time_table);


        swipeRefreshLayout = findViewById(R.id.refreshLayoutExamTimeTable);
        examTimeTableWebView = findViewById(R.id.webViewExamTimeTable);
        WebSettings examTimeTableWebSettings = examTimeTableWebView.getSettings();
        progressBarExamTimeTable = findViewById(R.id.progressBarExamTimeTable);



        examTimeTableWebSettings.setJavaScriptEnabled(true);
        examTimeTableWebView.loadUrl("https://www.ktu.edu.gh/en/category/examination-timetable/");
        examTimeTableWebSettings.setAppCacheEnabled(true);
        examTimeTableWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBarExamTimeTable.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBarExamTimeTable.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }
        });


        examTimeTableWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarExamTimeTable.setProgress(newProgress);
            }
        });
        examTimeTableWebView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (examTimeTableWebView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }

        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                examTimeTableWebView.reload();
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (examTimeTableWebView.canGoBack()){
            examTimeTableWebView.goBack();
        }else{
            super.onBackPressed();
        }
    }

}
