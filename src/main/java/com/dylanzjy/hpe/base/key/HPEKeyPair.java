package com.dylanzjy.hpe.base.key;

/**
 * Created by zhangjiayi on 2017/2/18.
 */
public class HPEKeyPair {
    private HPEPublicKey publicKey;

    private HPEPrivateKey privateKey;

    public HPEKeyPair(HPEPublicKey publicKey, HPEPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public HPEPublicKey getPublicKey() {
        return publicKey;
    }

    public HPEPrivateKey getPrivateKey() {
        return privateKey;
    }
}
