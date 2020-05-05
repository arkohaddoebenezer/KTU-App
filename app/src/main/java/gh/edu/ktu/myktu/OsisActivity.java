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

public class OsisActivity extends AppCompatActivity {
    private WebView osisWebView;
    ProgressBar progressBarOsis;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osis);

        osisWebView = findViewById(R.id.webViewOsis);
        WebSettings osisWebSettings = osisWebView.getSettings();
        progressBarOsis = findViewById(R.id.progressBarOsis);
        swipeRefreshLayout = findViewById(R.id.refreshLayoutOsis);


        osisWebSettings.setJavaScriptEnabled(true);
        osisWebView.loadUrl("http:sip.ktu.edu.gh/public/login");
        osisWebSettings.setAppCacheEnabled(true);
        osisWebSettings.setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/OSIS/cache");
        osisWebSettings.setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/OSIS/database");
        osisWebSettings.getDatabaseEnabled();


        osisWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBarOsis.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBarOsis.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }

        });
         osisWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarOsis.setProgress(newProgress);

            }
        });

        osisWebView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (osisWebView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }

        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                osisWebView.reload();
            }
         });

     }

     @Override
     public void onBackPressed() {
        if (osisWebView.canGoBack()){
            osisWebView.goBack();
        }else{
        super.onBackPressed();
        }
    }
}
