package com.dylanzjy.hpe.base.param;

import com.dylanzjy.hpe.base.key.HPEPrivateKey;
import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEKeyGenParameters {
    private HPEPrivateKey privateKey;

    private Element[][] V;

    private int l;

    public HPEKeyGenParameters(HPEPrivateKey privateKey, Element[][] V) {
        this.privateKey = privateKey;
        this.V = V;
        this.l = V.length;
    }

    public HPEPrivateKey getPrivateKey() {
        return privateKey;
    }

    public Element getVAt(int i, int j) {
        return V[i][j];
    }

    public int getL() {
        return l;
    }
}
