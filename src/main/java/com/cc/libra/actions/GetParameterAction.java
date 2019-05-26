package com.cc.libra.actions;

import com.cc.libra.models.MethodInfo;
import com.cc.libra.utils.JsonFormatUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class GetParameterAction extends Action{

	public static String ACTION_NAME = "getParameter";

	  public void handle(HttpServletRequest request, HttpServletResponse response)
	    throws IOException
	  {
	    String methodId = request.getParameter("methodId");
	    String beanName = request.getParameter("beanName");
	    MethodInfo methodInfo = getMethodInfo(methodId, beanName);
	    if (null == methodInfo) {
	      return;
	    }
	    Type[] types = methodInfo.getMethod().getGenericParameterTypes();
	    List list = new ArrayList();
	    for (Type type : types) {
	      Object sinType = null;
	      try {
	        sinType = createInstance(type);
	      } catch (Exception e) {
	        e.printStackTrace();
	        return;
	      }
	      list.add(sinType);
	    }
	    response.getWriter().write(JsonFormatUtil.getJsonFormatString(list));
	  }
	  
}
