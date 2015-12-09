package cn.edu.fudan.daoleme.module;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by rinnko on 2015/12/9.
 */
public class AboutFragment extends Fragment implements View.OnKeyListener {
    private static final String TAG = "AboutFragment";

    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mWebView = new WebView(getActivity());
        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // never jump to browser
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        return mWebView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // http://stackoverflow.com/questions/26706843/adding-an-assets-folder-in-android-studio
        // https://groups.google.com/forum/#!topic/adt-dev/Of03icHH4fs
        mWebView.loadUrl("file:///android_asset/about.html");

    }

    @Override
    // http://stackoverflow.com/questions/6077141/how-to-go-back-to-previous-page-if-back-button-is-pressed-in-webview
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                getActivity().finish();
            }
            return true;
        }
        return false;
    }

}
