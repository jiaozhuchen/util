package com.cc.libra.models;

import java.lang.reflect.Method;

/**
 * Created by chenchen39 on 2017/12/25.
 */
public class MethodInfo {
    private String methodName;
    private Method method;
    private String methodId;
    private Object bean;
    private String beanName;
    private boolean isStatic;
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public boolean isStatic() {
		return isStatic;
	}
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
}
