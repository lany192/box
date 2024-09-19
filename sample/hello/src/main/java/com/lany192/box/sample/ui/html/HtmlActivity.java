package com.lany192.box.sample.ui.html;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.hello.R;

import net.nightwhistler.htmlspanner.HtmlSpanner;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/html")
public class HtmlActivity extends AppCompatActivity {
    HtmlSpanner htmlSpanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_html);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        htmlSpanner = new HtmlSpanner();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final Spannable spannable = htmlSpanner.fromHtml(html);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv.setText(spannable);
//                        tv.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));
//                    }
//                });
//            }
//        }).start();
    }
}