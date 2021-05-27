package com.github.lany192.box.utils;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Locale;

/**
 * @author Administrator
 */
public class SafeUtils {
    /**
     * 防止动态调试及so注入
     */
    public static void safeCheck(Application application, boolean debug) {
        //非Debug 编译，反调试检测
        if (!debug) {
            if (isDebuggable(application)) {
                System.exit(0);
            }
            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(100);
                        if (Debug.isDebuggerConnected()) {
                            System.exit(0);
                        }
                        if (isUnderTraced()) {
                            System.exit(0);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "SafeGuardThread");
            t.start();
        }
        if (isUnderTraced()) {
            System.exit(0);
        }
    }

    private static boolean isUnderTraced() {
        String processStatusFilePath = String.format(Locale.US, "/proc/%d/status", android.os.Process.myPid());
        File procInfoFile = new File(processStatusFilePath);
        try {
            BufferedReader b = new BufferedReader(new FileReader(procInfoFile));
            String readLine;
            while ((readLine = b.readLine()) != null) {
                if (readLine.contains("TracerPid")) {
                    String[] arrays = readLine.split(":");
                    if (arrays.length == 2) {
                        int tracerPid = Integer.parseInt(arrays[1].trim());
                        if (tracerPid != 0) {
                            return true;
                        }
                    }
                }
            }
            b.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isDebuggable(Application application) {
        return 0 != (application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE);
    }
}
