package com.dylanzjy.hpe.base.param;

import com.dylanzjy.hpe.base.key.HPESecretKey;
import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEDelegationParameters {
    private Element[] V;

    private HPESecretKey cap;

    private int l;

    public HPEDelegationParameters(HPESecretKey cap, Element[] V) {
        this.V = V;
        this.cap = cap;
        this.l = cap.getKRan().length - 1;
    }

    public Element getVAt(int i) {
        return V[i];
    }

    public HPESecretKey getCap() {
        return cap;
    }

    public int getL() {
        return l;
    }
}
