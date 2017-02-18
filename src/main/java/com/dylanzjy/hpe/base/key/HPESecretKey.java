package com.dylanzjy.hpe.base.key;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPESecretKey {
    private Element kDec;

    private Element[] kRan;

    private Element[] kDel;

    public HPESecretKey(Element kDec, Element[] kRan, Element[] kDel) {
        this.kDec = kDec;
        this.kRan = kRan;
        this.kDel = kDel;
    }

    public Element getKDec() {
        return kDec;
    }

    public Element[] getKRan() {
        return kRan;
    }

    public Element[] getKDel() {
        return kDel;
    }
}
