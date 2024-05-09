package com.lany192.box.math.repository;

public class MathItem {
    private int type;
    private int a;
    private int b;

    public MathItem(int type, int a, int b) {
        this.type = type;
        this.a = a;
        this.b = b;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
