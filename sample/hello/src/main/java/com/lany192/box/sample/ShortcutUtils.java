package com.lany192.box.sample;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;

import com.lany192.box.sample.ui.settings.about.AboutActivity;

import java.util.ArrayList;
import java.util.List;

public class ShortcutUtils {

    public static void init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            List<ShortcutInfo> shortcuts = new ArrayList<>();
            Intent intent = new Intent(context, AboutActivity.class);
            intent.setAction(Intent.ACTION_VIEW);

            shortcuts.add(new ShortcutInfo.Builder(context, "id" + 0)
                    .setShortLabel("搜索")
                    .setLongLabel("拿返利找优惠券")
                    .setIcon(Icon.createWithResource(context, R.drawable.android))
                    .setIntent(intent)
                    .build());

            shortcuts.add(new ShortcutInfo.Builder(context, "id" + 1)
                    .setShortLabel("搜索2")
                    .setLongLabel("拿返利找优惠券2")
                    .setIcon(Icon.createWithResource(context, R.drawable.android))
                    .setIntent(intent)
                    .build());

            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            if (shortcutManager != null) {
                shortcutManager.setDynamicShortcuts(shortcuts);
            }
        }
    }
}
