package abc.com.win8;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class WebviewActivity extends Activity {
    private final String TAG = "WebviewActivity";

    private WebView myWebView = null;
    private Button myButton = null;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        myWebView = (WebView) findViewById(R.id.myWebView);

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

        myWebView.addJavascriptInterface(new WebAppInterface(this), "myInterfaceName");
        myWebView.loadUrl("file:///android_asset/sample.html");

        // 这里用一个Android按钮按下后调用JS中的代码
        myButton = (Button) findViewById(R.id.button1);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 用Android代码调用JavaScript函数：
                myWebView.loadUrl("javascript:myFunction()");

                // 这里实现的效果和在网页中点击第一个按钮的效果一致

            }
        });

    }

    /**
     * 自定义的Android代码和JavaScript代码之间的桥梁类
     *
     * @author 1
     */
    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
        }
    }

}