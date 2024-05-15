package com.github.lany192.blackbox;

public class BlackBox {

    static {
        System.loadLibrary("blackbox");
    }

    /**
     * AES加密, CBC, PKCS5Padding
     */
    public static native String method01(String str);

    /**
     * AES解密, CBC, PKCS5Padding
     */
    public static native String method02(String str);
}