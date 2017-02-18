# Hierarchical Predicate Encryption(HPE)

## Introduction
An implementation of **HPE** proposed in LOSTW10 based on [JPBC](http://gas.dia.unisa.it/projects/jpbc/index.html#.WKfs0hhY6Ho).
The related implementation in JPBC does not support hierarchical encryption.

#### Usage
```
public class HPE {

    public HPEKeyPair setUp(int n) {
        HPEKeyPairGenerator keyPairGenerator = new HPEKeyPairGenerator();
        return keyPairGenerator.generateKeyPair(createParameters(n));
    }

    public CipherText encryptForEvaluation(HPEPublicKey publicKey, Element[][] x) {
        HPEPredicateOnlyService predicate = new HPEPredicateOnlyService();
        return predicate.encrypt(new HPEEncryptionParameters(publicKey, x));
    }

    public boolean evaluate(HPESecretKey secretKey, CipherText cipherText) {
        HPEPredicateOnlyService predicate = new HPEPredicateOnlyService();
        return predicate.evaluate(new HPEDecryptionParameters(secretKey, cipherText));
    }

    public HPESecretKey keyGen(HPEPrivateKey privateKey, Element[][] v) {
        HPEKeyGenerator keyGenerator = new HPEKeyGenerator();
        return keyGenerator.generateSecretKey(new HPEKeyGenParameters(privateKey, v));
    }

    public HPESecretKey delegate(HPESecretKey secretKey, Element[] v) {
        HPEDelegationGenerator generator = new HPEDelegationGenerator();
        return generator.generateDelegation(new HPEDelegationParameters(secretKey, v));
    }

    public CipherText encrypt(HPEPublicKey publicKey, Element[][] x, Element m) {
        HPEEncryptService encryptService = new HPEEncryptService();
        return encryptService.encrypt(new HPEEncryptionParameters(publicKey, x, m));
    }

    public Element decrypt(HPESecretKey secretKey, CipherText cipherText) {
        HPEEncryptService encryptService = new HPEEncryptService();
        return encryptService.decrypt(new HPEDecryptionParameters(secretKey, cipherText));
    }


    public static void main(String[] args) {
        // number of attributes, should be even in this test case
        int n = 4;

        HPE hpe = new HPE();
        HPEKeyPair keyPair = hpe.setUp(n);

        Pairing pairing = HPEParametersHolder.getParameters(CommonConstants.key).getPairing();

        // attribute vector
        Element[][] X = new Element[1][n];
        // predicate vector
        Element[][] V = new Element[1][n];
        // predicate vector for delegation
        Element[] Vl = new Element[n];

        hpe.createOrthogonalVectors(X, V, Vl);

        // test for evaluation
        CipherText ev = hpe.encryptForEvaluation(keyPair.getPublicKey(), X);
        HPESecretKey evKey = hpe.keyGen(keyPair.getPrivateKey(), V);
        evKey = hpe.delegate(evKey, Vl);
        Assert.assertEquals(true, hpe.evaluate(evKey, ev));

        // test for encryption
        Element m = pairing.getGT().newRandomElement();
        CipherText cm = hpe.encrypt(keyPair.getPublicKey(), X, m);
        Assert.assertEquals(true, m.equals(hpe.decrypt(evKey, cm)));
    }

    private HPEParameters createParameters(int n) {
        HPEParameters parameters =  new HPEParameters(
                PairingFactory.getPairingParameters("curves/a.properties"), n);
        HPEParametersHolder.set(CommonConstants.key, parameters);
        return parameters;
    }

    private void createOrthogonalVectors(Element[][] X, Element[][] V, Element[] Vl) {
        Pairing pairing = HPEParametersHolder.getParameters(CommonConstants.key).getPairing();
        Random random = new Random();
        for (int i = 0; i < Vl.length; i += 2) {
            if (random.nextBoolean()) {
                X[0][i] = pairing.getZr().newZeroElement();
                X[0][i + 1] = pairing.getZr().newZeroElement();

                V[0][i] = pairing.getZr().newRandomElement();
                V[0][i + 1] = pairing.getZr().newRandomElement();

                Vl[i] = pairing.getZr().newRandomElement();;
                Vl[i + 1] = pairing.getZr().newRandomElement();;
            } else {
                X[0][i] = pairing.getZr().newOneElement();
                X[0][i + 1] = pairing.getZr().newRandomElement();

                V[0][i] = X[0][i + 1].duplicate().negate();
                V[0][i + 1] = pairing.getZr().newOneElement();

                Vl[i] = X[0][i + 1].duplicate().negate();
                Vl[i + 1] = pairing.getZr().newOneElement();
            }
        }
    }

}
```

## Reference
[LOSTW10] Lewko A, Okamoto T, Sahai A, et al. Fully secure functional encryption: Attribute-based encryption and (hierarchical) inner product encryption[C]//Annual International Conference on the Theory and Applications of Cryptographic Techniques. Springer Berlin Heidelberg, 2010: 62-91.
