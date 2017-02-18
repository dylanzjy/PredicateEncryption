package com.dylanzjy.hpe.util;

import com.dylanzjy.hpe.base.param.HPEParameters;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangjiayi on 2017/2/17.
 */
public class HPEParametersHolder {
    private static ConcurrentHashMap<String, HPEParameters> map = new ConcurrentHashMap<>();

    public static void set(String key, HPEParameters parameters) {
        map.put(key, parameters);
    }

    public static HPEParameters getParameters(String key) {
        return map.get(key);
    }
}
