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

public class VirtualLearningEnvironmentActivity extends AppCompatActivity {
    private WebView webViewVLE;
    ProgressBar progressBarVLE;
    SwipeRefreshLayout swipeRefreshLayoutVLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_learning_environment);

        webViewVLE          = findViewById(R.id.webViewVLE);
        progressBarVLE      = findViewById(R.id.progressBarVLE);
        swipeRefreshLayoutVLE   = findViewById(R.id.refreshLayoutVLE);
        WebSettings webSettings = webViewVLE.getSettings();


        webSettings.setJavaScriptEnabled(true);
        webViewVLE.loadUrl("http://vle.ktu.edu.gh/");
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/VLE/cache");
        webSettings.setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/VLE/database");
        webSettings.getDatabaseEnabled();

        webViewVLE.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBarVLE.setVisibility(View.GONE);
                swipeRefreshLayoutVLE.setRefreshing(false);
                super.onPageFinished(view,url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBarVLE.setVisibility(View.VISIBLE);
                super.onPageStarted(view,url,favicon);
               swipeRefreshLayoutVLE.setRefreshing(true);
            }
        });


        webViewVLE.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
               progressBarVLE.setProgress(newProgress);
            }
        });

      webViewVLE.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (webViewVLE.getScrollY() == 0) {
                    swipeRefreshLayoutVLE.setEnabled(true);
                } else {
                    swipeRefreshLayoutVLE.setEnabled(false);
                }
            }

        });

        swipeRefreshLayoutVLE.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webViewVLE.reload();
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (webViewVLE.canGoBack()) {
            webViewVLE.goBack();
        } else {
            super.onBackPressed();
        }

    }
}
