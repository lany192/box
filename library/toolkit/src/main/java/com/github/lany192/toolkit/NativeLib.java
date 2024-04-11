package com.github.lany192.toolkit;

public class NativeLib {

    // Used to load the 'toolkit' library on application startup.
    static {
        System.loadLibrary("toolkit");
    }

    /**
     * A native method that is implemented by the 'toolkit' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}