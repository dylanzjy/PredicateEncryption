package com.dylanzjy.hpe.util;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.util.ElementUtils;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class ElementUtil extends ElementUtils {
    public static Element[][] cloneImmutable(Element[][] source) {
        Element[][] target = new Element[source.length][source[0].length];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[i].length; j++) {
                if (source[i][j] != null && !source[i][j].isImmutable()) {
                    target[i][j] = source[i][j].getImmutable();
                } else {
                    target[i][j] = source[i][j];
                }
            }
        }
        return target;
    }
}
