package com.example.netposa.mobileMap;

import android.app.AlertDialog;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.netposa.mobileMap.Common.Util;

/**
 * SimpleJavaJSWebChromeClient
 */

public class SimpleJavaJSWebChromeClient extends WebChromeClient {
    private NetPosaMap map;

    public SimpleJavaJSWebChromeClient(NetPosaMap map) {
        this.map = map;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle("对话框")
                .setMessage(message)
                .setPositiveButton("确定", null);
        // 禁止响应按back键的事件
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
        return true;
        // return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Util.Info("onJsPrompt", message);
        if (map.parseJsonFromJs(message)) {
            /*必须得有这行代码，否则会阻塞当前h5页面*/
            result.cancel();
            return true;
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin,
                                                   GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }
}
