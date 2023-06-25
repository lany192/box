package cn.smallplants.client.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smallplants.client.App;

/**
 * 显示html内容
 */
public class HtmlView extends WebView {

    public HtmlView(Context context) {
        super(context);
        init();
    }

    public HtmlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HtmlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        getSettings().setJavaScriptEnabled(true);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // html加载完成之后，添加监听图片的点击js函数
                loadPicClickFun();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }

    @SuppressLint("AddJavascriptInterface")
    public void setHtml(String html) {
        // 添加js交互接口类
        addJavascriptInterface(new HtmlInterface(getImagesWithHtml(html)), "callAppMethod");
        String content = content2html(html);
        loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }

    private String content2html(String content) {
        return "<html>" +
                "<head>" +
                "<style>* " +
                "{font-size:14px}" +
                "{color:#212121;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                content +
                "</body>" +
                "</html>";
    }

    /**
     * 注入js函数监听
     */
    private void loadPicClickFun() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.callAppMethod.clickPic(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    private List<String> getImagesWithHtml(String html) {
        List<String> urls = new ArrayList<>();
        Pattern pattern = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.gif|\\.png|\\.jpe|\\.jpeg|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        String quote;
        String src;
        while (matcher.find()) {
            quote = matcher.group(1);
            src = (quote == null || quote.trim().length() == 0) ? matcher.group(2).split("\\s+")[0] : matcher.group(2);
            urls.add(src);
        }
        return urls;
    }

    /**
     * js通信接口
     */
    class HtmlInterface {
        private final List<String> urls;

        HtmlInterface(List<String> urls) {
            this.urls = urls;
        }

        @JavascriptInterface
        public void clickPic(String picUrl) {
            int position = 0;
            if (urls.contains(picUrl)) {
                position = urls.indexOf(picUrl);
            }
            App.getImage().startPreview(position, urls, 0);
        }
    }
}