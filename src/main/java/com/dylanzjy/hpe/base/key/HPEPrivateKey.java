package com.dylanzjy.hpe.base.key;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEPrivateKey {
    private Element[] BStar;

    public HPEPrivateKey(Element[] BStar) {
        this.BStar = BStar;
    }

    public Element getBStarAt(int index) {
        return BStar[index];
    }
}
