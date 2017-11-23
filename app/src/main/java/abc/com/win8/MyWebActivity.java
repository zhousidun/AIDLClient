package abc.com.win8;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebActivity extends AppCompatActivity {
    private static String TAG = "MyWebActivity";
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web);

        myWebView = (WebView) findViewById(R.id.my_web);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("GBK");

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message,JsResult result) {
                Log.i(TAG, "onJsAlert url=" + url + " message=" + message);
                return super.onJsAlert(view, url, message, result);
            }
        });

        myWebView.addJavascriptInterface(new ABCWebAppInterface(getApplicationContext()), "ABCZFT");

        myWebView.loadUrl("file:///android_asset/my.html");

        myWebView.loadUrl("file:///android_asset/my.html");
    }

    class ABCWebAppInterface{
        Context mContext;

        public ABCWebAppInterface(Context context){
            mContext = context;
        }

        @JavascriptInterface
        public String showToast(String info){
            Toast.makeText(mContext, info, Toast.LENGTH_LONG).show();
            return "Get info:"+info;
        }
    }
}


