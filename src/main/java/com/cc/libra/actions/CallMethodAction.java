package com.cc.libra.actions;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.cc.libra.models.MethodInfo;
import com.cc.libra.utils.JsonFormatUtil;
import org.springframework.stereotype.Service;

@Service
public class CallMethodAction extends Action
{
  public void handle(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String methodId = request.getParameter("methodId");
    String paramJson = request.getParameter("paramJson");
    String beanName = request.getParameter("beanName");
    try {
      response.getWriter().write(getJsonResult(methodId, paramJson, beanName));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  private String getJsonResult(String methodId, String inputJson, String beanName)
    throws Exception
  {
    MethodInfo methodInfo = getMethodInfo(methodId, beanName);
    if (null == methodInfo) {
      return "methodId:[" + methodId + "] not exist!";
    }
    Method method = methodInfo.getMethod();
    if (!(method.isAccessible())) {
      method.setAccessible(true);
    }
    Type[] ts = method.getGenericParameterTypes();
    Object bean = methodInfo.getBean();
    Object resultObj = null;
    if (ts.length == 1) {
      inputJson = inputJson.trim();
      String json = inputJson;
      if (inputJson.startsWith("[")) {
        json = inputJson.substring(1);
      }
      if (json.endsWith("]")) {
        json = json.substring(0, json.length() - 1);
      }
      Object obj = JSON.parseObject(json, ts[0], new Feature[] { Feature.AllowSingleQuotes });
      resultObj = method.invoke(bean, new Object[] { obj });
    } else if (ts.length > 1)
    {
      List<String> list = (List<String>)JSON.parseObject(inputJson, new TypeReference<List<String>>() {  }
      , new Feature[] { Feature.AllowSingleQuotes });

      Object[] param = new Object[ts.length];

      for (int i = 0; i < ts.length; ++i) {
        if (ts[i] == String.class) {
          String para = (String)list.get(i);
          Object paraObj = para;
          if (para.equals("true"))
            paraObj = Boolean.valueOf(true);
          else if (para.equals("false")) {
            paraObj = Boolean.valueOf(false);
          }
          param[i] = paraObj;
        }
        else if (ts[i] == Date.class) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

          param[i] = dateFormat.parse((String)list.get(i));
        } else {
          try {
            param[i] = JSON.parseObject((String)list.get(i), ts[i], new Feature[] { Feature.AllowSingleQuotes });
          } catch (Exception e) {
            param[i] = list.get(i);
          }
        }
      }
      resultObj = method.invoke(bean, param);
    }
    else {
      resultObj = method.invoke(bean, new Object[0]);
    }
    return JsonFormatUtil.getJsonFormatString(resultObj);
  }

  public static void main(String[] args) {
    String inputJson = "[\"aaa\",1]";
    List<String> list = (List<String>)JSON.parseObject(inputJson, new TypeReference<List<String>>() {  }
            , new Feature[] { Feature.AllowSingleQuotes });

    System.out.println(JSON.toJSONString(list));
  }
}
