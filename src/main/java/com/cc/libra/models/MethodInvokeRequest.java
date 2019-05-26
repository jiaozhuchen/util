package com.cc.libra.models;


public class MethodInvokeRequest {

	private String beanId;
	private String method;
	private String request;

	private boolean isStatic = false;

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public boolean getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(boolean aStatic) {
		isStatic = aStatic;
	}
}
