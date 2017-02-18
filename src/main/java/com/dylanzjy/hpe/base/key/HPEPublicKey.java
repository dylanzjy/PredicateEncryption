package com.dylanzjy.hpe.base.key;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEPublicKey {
    private Element gT;

    private Element[] B;

    public HPEPublicKey(Element gT, Element[] B) {
        this.gT = gT;
        this.B = B;
    }

    public Element getgT() {
        return gT;
    }

    public Element getBAt(int index) {
        return B[index];
    }
}
