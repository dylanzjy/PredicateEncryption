package com.dylanzjy.hpe.base.param;

import com.dylanzjy.hpe.base.entity.CipherText;
import com.dylanzjy.hpe.base.key.HPESecretKey;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEDecryptionParameters {
    private HPESecretKey secretKey;

    private CipherText ct;

    public HPEDecryptionParameters(HPESecretKey secretKey, CipherText ct) {
        this.secretKey = secretKey;
        this.ct = ct;
    }

    public HPESecretKey getSecretKey() {
        return secretKey;
    }

    public CipherText getCt() {
        return ct;
    }
}
