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

public class FinancialSupportActivity extends AppCompatActivity {
    private WebView FinancialSupportWebView;
    ProgressBar FinancialSupportWebViewProgressBar;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finacial_support);
        FinancialSupportWebView = findViewById(R.id.webViewFinacialSupport);
        FinancialSupportWebViewProgressBar = findViewById(R.id.progressBarFinacialSupport);
        WebSettings FinacialSupportWebSettings = FinancialSupportWebView.getSettings();
        swipeRefreshLayout = findViewById(R.id.refreshLayoutFinacialSupport);


        FinacialSupportWebSettings.setJavaScriptEnabled(true);
        FinancialSupportWebView.loadUrl("https://www.ktu.edu.gh/en/academics/financial-aidscholarship/");
        FinacialSupportWebSettings.setAppCacheEnabled(true);
        FinacialSupportWebSettings.setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/FinacialSupport/cache");
        FinacialSupportWebSettings.setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/FinacialSupport/database");
        FinacialSupportWebSettings.getDatabaseEnabled();

        FinancialSupportWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                FinancialSupportWebViewProgressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view,url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                FinancialSupportWebViewProgressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view,url,favicon);
                swipeRefreshLayout.setRefreshing(true);
            }
        });


        FinancialSupportWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                FinancialSupportWebViewProgressBar.setProgress(newProgress);
            }
        });

        FinancialSupportWebView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (FinancialSupportWebView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }

        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FinancialSupportWebView.reload();
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (FinancialSupportWebView.canGoBack()) {
            FinancialSupportWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

