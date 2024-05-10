package com.lany192.box.math.repository;

public class MathItem {
    private int type;
    private int left;
    private int right;

    private int result;

    public MathItem(int type, int left, int right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
