package watch.fight.android.fightbrowser.InformationFeeds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by josh on 10/16/15.
 */
public class InformationFeedsWebViewActivity extends AppCompatActivity{
    public static String WEBVIEW_URL = "watch.fight.android.fightbrowser.informationfeeds.webview";
    private WebView webview;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        webview = new WebView(this);
        setContentView(webview);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String url = intent.getStringExtra(WEBVIEW_URL);

        if (!url.isEmpty()) {
            webview.loadUrl(url);
        } else {
            // TODO : HTML HERE for error.
            String error = "<html><body>Apologies - The webpage would not load</body></html>";
            webview.loadData(error, "text/html", null);
        }

        ActionBar ap = getSupportActionBar();
        if (ap != null) {
            ap.hide();
        }
    }

    public static Intent NewInstance(Context activity, String url) {
        Intent intent = new Intent(activity, InformationFeedsWebViewActivity.class);
        intent.putExtra(WEBVIEW_URL, url);
        return intent;
    }

}
