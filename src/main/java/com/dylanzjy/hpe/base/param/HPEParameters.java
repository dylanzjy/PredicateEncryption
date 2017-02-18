package com.dylanzjy.hpe.base.param;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.product.ProductPairing;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEParameters {
    private PairingParameters parameters;

    private int n;

    private Pairing pairing;

    private Element g;

    private ProductPairing productPairing;

    public HPEParameters(PairingParameters parameters, int n) {
        this.parameters = parameters;
        this.n = n;
        this.pairing = PairingFactory.getPairing(this.parameters);
        this.g = pairing.getG1().newRandomElement();
        this.productPairing = new ProductPairing(null, pairing, 2 * n + 3);
    }

    public PairingParameters getParameters() {
        return parameters;
    }

    public int getN() {
        return n;
    }

    public Pairing getPairing() {
        return pairing;
    }

    public Element getG() {
        return g;
    }

    public ProductPairing getProductPairing() {
        return productPairing;
    }
}
