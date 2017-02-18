package com.dylanzjy.hpe.base.param;

import com.dylanzjy.hpe.base.key.HPEPublicKey;
import com.dylanzjy.hpe.util.ElementUtil;
import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEEncryptionParameters {
    private HPEPublicKey publicKey;

    private Element[][] X;

    private Element message;

    public HPEEncryptionParameters(HPEPublicKey publicKey, Element[][] X) {
        this(publicKey, X, null);
    }

    public HPEEncryptionParameters(HPEPublicKey publicKey, Element[][] X, Element message) {
        this.publicKey = publicKey;
        this.X = ElementUtil.cloneImmutable(X);
        this.message = message;
    }

    public HPEPublicKey getPublicKey() {
        return publicKey;
    }

    public Element getXAt(int i, int j) {
        return X[i][j];
    }

    public Element getMessage() {
        return message;
    }

    public int getL() {
        return X.length;
    }
}
