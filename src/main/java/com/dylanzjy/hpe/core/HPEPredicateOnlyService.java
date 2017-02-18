package com.dylanzjy.hpe.core;

import com.dylanzjy.hpe.base.entity.CipherText;
import com.dylanzjy.hpe.base.entity.CommonConstants;
import com.dylanzjy.hpe.base.param.HPEDecryptionParameters;
import com.dylanzjy.hpe.base.param.HPEEncryptionParameters;
import com.dylanzjy.hpe.base.key.HPEPublicKey;
import com.dylanzjy.hpe.util.HPEParametersHolder;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.product.ProductPairing;

/**
 * Created by zhangjiayi on 2017/2/17.
 * hpe predicate only service
 * can be used to generate index of PEKS
 */
public class HPEPredicateOnlyService {

    // generate index
    public CipherText encrypt(HPEEncryptionParameters parameters) {
        HPEPublicKey publicKey = parameters.getPublicKey();
        Pairing pairing = HPEParametersHolder.getParameters(CommonConstants.key).getPairing();

        int n = HPEParametersHolder.getParameters(CommonConstants.key).getN();
        int l = parameters.getL();
        Element delta1 = pairing.getZr().newRandomElement();
        Element delta2 = pairing.getZr().newRandomElement();

        Element c1 = publicKey.getBAt(0).duplicate().powZn(parameters.getXAt(0, 0));
        for (int j = 1; j < n; j++) {
            c1.add(publicKey.getBAt(j).duplicate().powZn(parameters.getXAt(0, j)));
        }
        c1.mulZn(delta1);
        for (int i = 1; i < l; i++) {
            delta1 = pairing.getZr().newRandomElement();
            Element tmp = publicKey.getBAt(0).duplicate().powZn(parameters.getXAt(i, 0));
            for (int j = 1; j < n; j++) {
                tmp.add(publicKey.getBAt(j).duplicate().powZn(parameters.getXAt(i, j)));
            }
            tmp.mulZn(delta1);
            c1.add(tmp);
        }
        c1.add(publicKey.getBAt(n + 1).duplicate().powZn(delta2));

        return new CipherText(c1.toBytes());

    }

    // evaluate whether the trapdoor matches index
    public boolean evaluate(HPEDecryptionParameters parameters) {
        ProductPairing productPairing = HPEParametersHolder
                .getParameters(CommonConstants.key).getProductPairing();

        Element c1 = productPairing.getG1().newElement();
        c1.setFromBytes(parameters.getCt().getC1());

        Element result = productPairing.pairing(c1, parameters.getSecretKey().getKDec());
        return result.isOne();
    }
}
