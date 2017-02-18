package com.dylanzjy.hpe.generator;

import com.dylanzjy.hpe.base.entity.CommonConstants;
import com.dylanzjy.hpe.base.key.HPEPrivateKey;
import com.dylanzjy.hpe.base.key.HPESecretKey;
import com.dylanzjy.hpe.base.param.HPEKeyGenParameters;
import com.dylanzjy.hpe.base.param.HPEParameters;
import com.dylanzjy.hpe.util.HPEParametersHolder;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEKeyGenerator {

    /**
     * generate key
     * @return
     */
    public HPESecretKey generateSecretKey(HPEKeyGenParameters parameters) {
        return new HPESecretKey(
                generateKeyDecrypt(parameters),
                generateKeyRandom(parameters),
                generateKeyDelegation(parameters)
        );
    }

    /**
     * generate l level decryption key
     * @return
     */
    private Element generateKeyDecrypt(HPEKeyGenParameters parameters) {
        HPEPrivateKey privateKey = parameters.getPrivateKey();
        HPEParameters hpeParameters = HPEParametersHolder
                .getParameters(CommonConstants.key);

        Pairing pairing = hpeParameters.getPairing();
        int n = hpeParameters.getN();

        Element sigma = pairing.getZr().newRandomElement();
        Element eta = pairing.getZr().newRandomElement();
        Element k = privateKey.getBStarAt(0).duplicate().powZn(parameters.getVAt(0, 0));
        for (int i = 1; i < n; i++) {
            k.add(privateKey.getBStarAt(i).duplicate().powZn(parameters.getVAt(0, i)));
        }
        k.mulZn(sigma);
        Element tmp;
        for (int t = 1; t < parameters.getL(); t++) {
            sigma = pairing.getZr().newRandomElement();
            tmp = privateKey.getBStarAt(0).duplicate().powZn(parameters.getVAt(t, 0));
            for (int i = 1; i < n; i++) {
                tmp.add(privateKey.getBStarAt(i).duplicate().powZn(parameters.getVAt(t, i)));
            }
            tmp.mulZn(sigma);
            k.add(tmp);
        }

        k.add(privateKey.getBStarAt(n))
                .add(privateKey.getBStarAt(n + 1).duplicate().powZn(eta));
        return k;
    }

    /**
     * generate l level random key
     * @return
     */
    private Element[] generateKeyRandom(HPEKeyGenParameters parameters) {
        HPEPrivateKey privateKey = parameters.getPrivateKey();
        HPEParameters hpeParameters = HPEParametersHolder
                .getParameters(CommonConstants.key);

        Pairing pairing = hpeParameters.getPairing();
        int n = hpeParameters.getN();

        Element sigma;
        Element eta;
        Element tmp;
        Element[] k = new Element[parameters.getL() + 1];
        for (int i = 0; i < parameters.getL() + 1; i++) {
            sigma = pairing.getZr().newRandomElement();
            eta = pairing.getZr().newRandomElement();
            k[i] = privateKey.getBStarAt(0).duplicate().powZn(parameters.getVAt(0, 0));
            for (int j = 1; j < n; j++) {
                k[i].add(privateKey.getBStarAt(j).duplicate().powZn(parameters.getVAt(0, j)));
            }
            k[i].mulZn(sigma);
            for (int t = 1; t < parameters.getL(); t++) {
                sigma = pairing.getZr().newRandomElement();
                tmp = privateKey.getBStarAt(0).duplicate().powZn(parameters.getVAt(t, 0));
                for (int j = 1; j < n; j++) {
                    tmp.add(privateKey.getBStarAt(j).duplicate().powZn(parameters.getVAt(t, j)));
                }
                tmp.mulZn(sigma);
                k[i].add(tmp);
            }
            k[i].add(privateKey.getBStarAt(n + 1).duplicate().powZn(eta));
        }
        return k;
    }

    /**
     * generate l level delegate key
     * @return
     */
    private Element[] generateKeyDelegation(HPEKeyGenParameters parameters) {
        HPEPrivateKey privateKey = parameters.getPrivateKey();
        HPEParameters hpeParameters = HPEParametersHolder
                .getParameters(CommonConstants.key);

        Pairing pairing = hpeParameters.getPairing();
        int n = hpeParameters.getN();

        Element sigma;
        Element phi = pairing.getZr().newRandomElement();;
        Element eta;
        Element tmp;
        Element[] k = new Element[n];
        for (int i = 0; i < n; i++) {
            sigma = pairing.getZr().newRandomElement();
            eta = pairing.getZr().newRandomElement();
            k[i] = privateKey.getBStarAt(0).duplicate().powZn(parameters.getVAt(0, 0));
            for (int j = 1; j < n; j++) {
                k[i].add(privateKey.getBStarAt(j).duplicate().powZn(parameters.getVAt(0, j)));
            }
            k[i].mulZn(sigma);
            for (int t = 1; t < parameters.getL(); t++) {
                sigma = pairing.getZr().newRandomElement();
                tmp = privateKey.getBStarAt(0).duplicate().powZn(parameters.getVAt(t, 0));
                for (int j = 1; j < n; j++) {
                    tmp.add(privateKey.getBStarAt(j).duplicate().powZn(parameters.getVAt(t, j)));
                }
                tmp.mulZn(sigma);
                k[i].add(tmp);
            }
            k[i].add(privateKey.getBStarAt(i).duplicate().powZn(phi))
                    .add(privateKey.getBStarAt(n + 1).duplicate().powZn(eta));
        }
        return k;
    }
}
