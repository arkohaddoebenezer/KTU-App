package gh.edu.ktu.myktu;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MailActivity extends AppCompatActivity {
    private WebView webViewCheckMail;
    ProgressBar progressBarCheckMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);


        webViewCheckMail = findViewById(R.id.webViewCheckMail);
        progressBarCheckMail = findViewById(R.id.progressBarCheckMail);
        WebSettings webSettings = webViewCheckMail.getSettings();


        webSettings.setJavaScriptEnabled(true);
        webViewCheckMail.loadUrl("http://mymail.ktu.edu.gh/");
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/mail/cache");
        webSettings.setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/mail/database");
        webSettings.getDatabaseEnabled();

        webViewCheckMail.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBarCheckMail.setVisibility(View.GONE);
                super.onPageFinished(view,url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBarCheckMail.setVisibility(View.VISIBLE);
                super.onPageStarted(view,url,favicon);
            }
        });


        webViewCheckMail.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarCheckMail.setProgress(newProgress);
            }
        });



    }


    @Override
    public void onBackPressed() {
        if (webViewCheckMail.canGoBack()) {
            webViewCheckMail.goBack();
        } else {
            super.onBackPressed();
        }

    }

}
