package com.bonanhtai.aistudymentor.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MathView extends WebView {

    private String text;

    public MathView(Context context) {
        super(context);
        init();
    }

    public MathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        setBackgroundColor(0); // Transparent
        // Removed setLayerType(WebView.LAYER_TYPE_SOFTWARE, null) - Hardware acceleration is better for API 30+
    }

    public void setText(String text) {
        this.text = text;
        if (text == null) return;

        String html = "<html><head>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no'>" +
                "<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/katex@0.16.9/dist/katex.min.css'>" +
                "<script src='https://cdn.jsdelivr.net/npm/katex@0.16.9/dist/katex.min.js'></script>" +
                "<script src='https://cdn.jsdelivr.net/npm/katex@0.16.9/dist/contrib/auto-render.min.js'></script>" +
                "<style>" +
                "body { font-size: 15px; color: #1E293B; margin: 0; padding: 2px; line-height: 1.5; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; }" +
                ".katex { font-size: 1.1em !important; }" +
                "</style>" +
                "</head><body>" +
                "<div id='content'>" + text + "</div>" +
                "<script>" +
                "document.addEventListener('DOMContentLoaded', function() {" +
                "  renderMathInElement(document.getElementById('content'), {" +
                "    delimiters: [" +
                "      {left: '$$', right: '$$', display: true}," +
                "      {left: '$', right: '$', display: false}," +
                "      {left: '\\\\(', right: '\\\\)', display: false}," +
                "      {left: '\\\\[', right: '\\\\]', display: true}" +
                "    ]," +
                "    throwOnError: false" +
                "  });" +
                "});" +
                "</script>" +
                "</body></html>";
        
        loadDataWithBaseURL("https://katex.render", html, "text/html", "UTF-8", null);
    }

    public String getText() {
        return text;
    }
}
