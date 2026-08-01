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
        setBackgroundColor(0); // Transparent
    }

    public void setText(String text) {
        this.text = text;
        String html = "<html><head>" +
                "<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/katex@0.16.9/dist/katex.min.css'>" +
                "<script src='https://cdn.jsdelivr.net/npm/katex@0.16.9/dist/katex.min.js'></script>" +
                "<script src='https://cdn.jsdelivr.net/npm/katex@0.16.9/dist/contrib/auto-render.min.js'></script>" +
                "<style>body { font-size: 16px; color: #1E293B; margin: 0; padding: 0; }</style>" +
                "</head><body>" +
                text +
                "<script>renderMathInElement(document.body, { delimiters: [" +
                "{left: '$$', right: '$$', display: true}," +
                "{left: '$', right: '$', display: false}," +
                "{left: '\\\\(', right: '\\\\)', display: false}," +
                "{left: '\\\\[', right: '\\\\]', display: true}" +
                "] });</script>" +
                "</body></html>";
        loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

    public String getText() {
        return text;
    }
}
