package com.dylanzjy.hpe.base.entity;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class CipherText {
    private byte[] c1;

    // not necessary for evaluation
    private byte[] c2;

    public CipherText(byte[] c1) {
        this(c1, null);
    }

    public CipherText(byte[] c1, byte[] c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public byte[] getC1() {
        return c1;
    }

    public byte[] getC2() {
        return c2;
    }
}
