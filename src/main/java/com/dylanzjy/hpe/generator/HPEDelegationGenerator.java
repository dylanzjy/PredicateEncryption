package com.dylanzjy.hpe.generator;

import com.dylanzjy.hpe.base.entity.CommonConstants;
import com.dylanzjy.hpe.base.key.HPESecretKey;
import com.dylanzjy.hpe.base.param.HPEDelegationParameters;
import com.dylanzjy.hpe.base.param.HPEParameters;
import com.dylanzjy.hpe.util.HPEParametersHolder;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEDelegationGenerator {
    /**
     * generate trapdoor
     */
    public HPESecretKey generateDelegation(HPEDelegationParameters parameters) {
        return new HPESecretKey(
                generateDelDecryption(parameters),
                generateDelRandom(parameters),
                generateDelDelegation(parameters)
        );
    }

    /**
     * generate kDec(l + 1)
     * @return
     */
    private Element generateDelDecryption(HPEDelegationParameters parameters) {
        HPEParameters hpeParameters = HPEParametersHolder
                .getParameters(CommonConstants.key);

        Pairing pairing = hpeParameters.getPairing();
        Element[] klRan = parameters.getCap().getKRan();
        Element[] klDel = parameters.getCap().getKDel();

        Element kDec = parameters.getCap().getKDec().duplicate();
        int l = parameters.getL() + 1;
        Element alphaDec;
        for (int i = 0; i < l; i++) {
            alphaDec = pairing.getZr().newRandomElement();
            kDec.add(klRan[i].duplicate().mulZn(alphaDec));
        }

        Element sigmaDec = pairing.getZr().newRandomElement();
        Element kTemp = klDel[0].duplicate().powZn(parameters.getVAt(0));
        for (int i = 1; i < hpeParameters.getN(); i++) {
            kTemp.add(klDel[i].duplicate().powZn(parameters.getVAt(i)));
        }
        kDec.add(kTemp.mulZn(sigmaDec));
        return kDec;
    }

    /**
     * generate kRan(l + 1)
     * @return
     */
    private Element[] generateDelRandom(HPEDelegationParameters parameters) {
        HPEParameters hpeParameters = HPEParametersHolder
                .getParameters(CommonConstants.key);

        Pairing pairing = hpeParameters.getPairing();
        Element[] klRan = parameters.getCap().getKRan();
        Element[] klDel = parameters.getCap().getKDel();
        int l = parameters.getL();

        Element[] kRan = new Element[l + 1];
        Element alphaRan;
        Element sigmaRan;
        for (int i = 0; i <= l; i++) {
            alphaRan = pairing.getZr().newRandomElement();
            kRan[i] = klRan[0].duplicate().mulZn(alphaRan);
            for (int j = 1; j < l; j++) {
                alphaRan = pairing.getZr().newRandomElement();
                kRan[i].add(klRan[j].duplicate().mulZn(alphaRan));
            }

            sigmaRan = pairing.getZr().newRandomElement();
            Element kTemp = klDel[0].duplicate().powZn(parameters.getVAt(0));
            for (int j = 1; j < hpeParameters.getN(); j++) {
                kTemp.add(klDel[j].duplicate().powZn(parameters.getVAt(j)));
            }
            kRan[i].add(kTemp.mulZn(sigmaRan));
        }
        return kRan;
    }

    /**
     * generate kDel(l + 1)
     * @return
     */
    private Element[] generateDelDelegation(HPEDelegationParameters parameters) {
        HPEParameters hpeParameters = HPEParametersHolder
                .getParameters(CommonConstants.key);

        int n = hpeParameters.getN();
        Pairing pairing = hpeParameters.getPairing();
        Element[] klRan = parameters.getCap().getKRan();
        Element[] klDel = parameters.getCap().getKDel();
        int l = parameters.getL();

        Element[] kDel = new Element[n];
        Element alphaDel;
        Element sigmaDel;
        Element phiDel;
        for (int i = 0; i < n; i++) {
            alphaDel = pairing.getZr().newRandomElement();
            kDel[i] = klRan[0].duplicate().mulZn(alphaDel);
            for (int j = 1; j < l; j++) {
                alphaDel = pairing.getZr().newRandomElement();
                kDel[i].add(klRan[j].duplicate().mulZn(alphaDel));
            }

            sigmaDel = pairing.getZr().newRandomElement();
            Element kTemp = klDel[0].duplicate().powZn(parameters.getVAt(0));
            for (int j = 1; j < n; j++) {
                kTemp.add(klDel[j].duplicate().powZn(parameters.getVAt(j)));
            }
            kDel[i].add(kTemp.mulZn(sigmaDel));

            phiDel = pairing.getZr().newRandomElement();
            kDel[i].add(klDel[i].duplicate().mulZn(phiDel));
        }
        return kDel;
    }
}
