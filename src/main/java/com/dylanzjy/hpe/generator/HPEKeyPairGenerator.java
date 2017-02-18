package com.dylanzjy.hpe.generator;

import com.dylanzjy.hpe.base.key.HPEKeyPair;
import com.dylanzjy.hpe.base.param.HPEParameters;
import com.dylanzjy.hpe.base.key.HPEPublicKey;
import com.dylanzjy.hpe.base.key.HPEPrivateKey;
import it.unisa.dia.gas.crypto.jpbc.dpvs.DPVS;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.Vector;

import java.security.SecureRandom;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEKeyPairGenerator {

    public HPEKeyPair generateKeyPair(HPEParameters parameters) {
        Pairing pairing = parameters.getPairing();

        Element g = parameters.getG();
        int n = parameters.getN();
        int N = 2 * n + 3;

        Element gT = pairing.pairing(g, g);

        Element[][] dualOrthonormalBases = DPVS
                .sampleRandomDualOrthonormalBases(new SecureRandom(), pairing, g, N);

        // B
        Element[] B = new Vector[n + 2];
        System.arraycopy(dualOrthonormalBases[0], 0, B, 0, n);
        B[n] = dualOrthonormalBases[0][N-3];
        B[n + 1] = dualOrthonormalBases[0][N-1];

        // BStar
        Element[] BStar = new Vector[n + 2];
        System.arraycopy(dualOrthonormalBases[1], 0, BStar, 0, n);
        BStar[n] = dualOrthonormalBases[1][N-3];
        BStar[n + 1] = dualOrthonormalBases[1][N-2];

        return new HPEKeyPair(new HPEPublicKey(gT, B), new HPEPrivateKey(BStar));
    }
}
