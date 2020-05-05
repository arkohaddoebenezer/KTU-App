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

public class FeeStructureActivity extends AppCompatActivity {
    private WebView feeStructureWebView;
    ProgressBar progressBarFeeStructure;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_structure);

        feeStructureWebView = findViewById(R.id.webViewFeeStructure);
        WebSettings feeStructureWebSettings = feeStructureWebView.getSettings();
        progressBarFeeStructure = findViewById(R.id.progressBarFeeStructure);
        swipeRefreshLayout  = findViewById(R.id.refreshLayoutFeeStructure);


        feeStructureWebSettings.setJavaScriptEnabled(true);
        feeStructureWebView.loadUrl("https://www.ktu.edu.gh/en/category/fees-structure/");
        feeStructureWebSettings.setAppCacheEnabled(true);
        feeStructureWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBarFeeStructure.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBarFeeStructure.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        feeStructureWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarFeeStructure.setProgress(newProgress);
            }
        });
        feeStructureWebView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (feeStructureWebView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }

        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                feeStructureWebView.reload();
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (feeStructureWebView.canGoBack()){
            feeStructureWebView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
