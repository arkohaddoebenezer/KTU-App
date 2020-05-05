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

public class AdmissionActivity extends AppCompatActivity {
    private WebView AdmissionWebView;
    ProgressBar progressBarAdmission;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);

        AdmissionWebView = findViewById(R.id.webViewAdmission);
        WebSettings AdmissionWebSettings = AdmissionWebView.getSettings();
        progressBarAdmission = findViewById(R.id.progressBarAdmission);
        swipeRefreshLayout = findViewById(R.id.refreshLayoutAdmission);


        AdmissionWebSettings.setJavaScriptEnabled(true);
        AdmissionWebView.loadUrl("https://admissions.ktu.edu.gh/");
        AdmissionWebSettings.setAppCacheEnabled(true);
        AdmissionWebSettings.setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/Admission/cache");
        AdmissionWebSettings.setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/Admission/database");
        AdmissionWebSettings.getDatabaseEnabled();


        AdmissionWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBarAdmission.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBarAdmission.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }

        });
        AdmissionWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarAdmission.setProgress(newProgress);

            }
        });

        AdmissionWebView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (AdmissionWebView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }

        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AdmissionWebView.reload();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (AdmissionWebView.canGoBack()){
            AdmissionWebView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
