/*
    Cole Howell, Manoj Bompada
    MovieWebViewActivity.java
    ITCS 4180
 */

package example.com.imdbapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MovieWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_web_view);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        setTitle("Movie Webview");

        WebView webView = (WebView) findViewById(R.id.movieWebView);
        TextView pageURL = (TextView) findViewById(R.id.pageURL);

        Intent intent = getIntent();
        String link = "http://m.imdb.com/title/" + intent.getStringExtra("imdbID");

        pageURL.setText(link);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(link);
    }
}
