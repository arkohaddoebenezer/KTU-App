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

public class UniversityLibraryActivity extends AppCompatActivity {
    private WebView UniversityLibraryWebView;
    ProgressBar progressBarULA;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_library);

        UniversityLibraryWebView = findViewById(R.id.webViewUniversityLibrary);
        WebSettings UniversityLibraryWebSettings = UniversityLibraryWebView.getSettings();
        progressBarULA  = findViewById(R.id.progressBarULA);
        swipeRefreshLayout  = findViewById(R.id.refreshLayoutULA);


        UniversityLibraryWebSettings.setJavaScriptEnabled(true);
        UniversityLibraryWebView.loadUrl("https://library.ktu.edu.gh/");
        UniversityLibraryWebSettings.setAppCacheEnabled(true);
        UniversityLibraryWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                swipeRefreshLayout.setRefreshing(true);
                progressBarULA.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBarULA.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        UniversityLibraryWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarULA.setProgress(newProgress);
            }
        });

            UniversityLibraryWebView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (UniversityLibraryWebView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }

        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                UniversityLibraryWebView.reload();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (UniversityLibraryWebView.canGoBack()){
            UniversityLibraryWebView.goBack();
        }else{
            super.onBackPressed();
        }
    }

}
