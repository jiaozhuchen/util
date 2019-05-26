package com.cc.libra.utils;

import com.alibaba.fastjson.JSONObject;
import com.cc.libra.models.MethodInvokeRequest;

/**
 * Created by chenchen39 on 2018/8/15.
 */
public class MethodInvokeUtil {

    /**
     * methodInvokeRequest.setBeanId("productService");
     * methodInvokeRequest.setMethod("queryFeimaShopListHasStock4Local");
     * methodInvokeRequest.setRequest("[788150L, 17L, 1381L, \"012,001\"]");
     * @param beanId
     * @param method
     * @param request
     */
    public static MethodInvokeRequest buildInvokeRequest(String beanId, String method, String request) {
        MethodInvokeRequest methodInvokeRequest = new MethodInvokeRequest();
        methodInvokeRequest.setBeanId(beanId);
        methodInvokeRequest.setMethod(method);
        methodInvokeRequest.setRequest(request);
        System.out.println(JSONObject.toJSON(methodInvokeRequest));
        return methodInvokeRequest;
    }

}
