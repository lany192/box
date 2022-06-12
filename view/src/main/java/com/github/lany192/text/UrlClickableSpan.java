//package com.gamekipo.play.view;
//
//import android.net.Uri;
//import android.os.Build;
//import android.text.Html;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.text.TextUtils;
//import android.text.style.ClickableSpan;
//import android.text.style.URLSpan;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class UrlClickableSpan extends ClickableSpan {
//
//    private final String url;
//
//    UrlClickableSpan(String url) {
//        this.url = url;
//    }
//
//    public static SpannableStringBuilder setText(CharSequence text) {
//        if (!TextUtils.isEmpty(text)) {
//
//
//        //添加html样式
//        CharSequence spanned = getHtml(text.toString());
//        SpannableStringBuilder builder = new SpannableStringBuilder(spanned);
//        URLSpan[] spans = builder.getSpans(0, spanned.length(), URLSpan.class);
//
//        for (URLSpan span : spans) {
//            int startIndex = builder.getSpanStart(span);
//            int endIndex = builder.getSpanEnd(span);
//            builder.setSpan(new UrlClickableSpan(span.getURL()), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            builder.removeSpan(span);
//        }
//
//        return builder;
//    }
//    /**
//     * 转成html格式
//     */
//    public CharSequence getHtml(String text) {
//        if (TextUtils.isEmpty(text)) {
//            return "";
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
//        } else {
//            return Html.fromHtml(text);
//        }
//    }
//}
