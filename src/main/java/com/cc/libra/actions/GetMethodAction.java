package com.cc.libra.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cc.libra.models.MethodInfo;
import com.cc.libra.models.MethodSimpleInfo;
import com.cc.libra.utils.ToolsSpringContextUtil;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GetMethodAction extends Action {

	public static String ACTION_NAME = "getMethod";

	  public void handle(HttpServletRequest request, HttpServletResponse response)
	    throws IOException
	  {
	    String beanName = request.getParameter("beanName");
	    String isStatic = request.getParameter("isStatic");
	    if (isStatic == null) {
	      isStatic = "0";
	    }
	    String className = request.getParameter("className");

	    PrintWriter writer = response.getWriter();
	    try {
	      JSONObject jsonObject = new JSONObject();
	      List allMethods = null;
	      if (isStatic.equals("0")) {
	        Object bean = ToolsSpringContextUtil.getBean(beanName);
	        if (bean == null) {
	          jsonObject.put("code", Integer.valueOf(1));
	          jsonObject.put("msg", "bean not exist!");
	          writer.write(jsonObject.toJSONString());
	          return;
	        } else {
	          allMethods = getAllMethodInfoList(bean);
	        }
	      } else {
	        allMethods = getAllStaticMethodInfoList(className);
	      }
	      jsonObject.put("code", Integer.valueOf(0));
	      jsonObject.put("methodList", getMethodSimpleInfoList(allMethods));
	      writer.write(jsonObject.toJSONString());
	    }
	    catch (NoSuchBeanDefinitionException e)
	    {
	      JSONObject jsonObject = new JSONObject();
	      jsonObject.put("code", Integer.valueOf(1));
	      jsonObject.put("msg", "bean not exist!");
	      writer.write(jsonObject.toJSONString());
	    }
	  }

	  private List<MethodSimpleInfo> getMethodSimpleInfoList(List<MethodInfo> methodInfoList) {
	    List resultList = new ArrayList();
	    for (MethodInfo methodInfo : methodInfoList) {
	      if (methodInfo.getMethodName().indexOf("CGLIB$") >= 0) {
	        continue;
	      }
	      MethodSimpleInfo simpleInfo = new MethodSimpleInfo();
	      simpleInfo.setMethodId(methodInfo.getMethodId());
	      simpleInfo.setMethodName(methodInfo.getMethodName());
	      resultList.add(simpleInfo);
	    }
	    return resultList;
	  }
}
