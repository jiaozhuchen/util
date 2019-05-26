package com.cc.libra.actions;

import com.cc.libra.models.Constants;
import com.cc.libra.models.UserInfo;
import com.cc.libra.utils.ToolsSpringContextUtil;
import com.cc.libra.service.CacheService;
import com.cc.libra.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service("ssoAction")
public class SSOAction extends Action{

	public static String ACTION_NAME = "sso";

	private String serviceName = "cacheService";

	public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("password");
		String returnUrl = request.getParameter(Constants.RETURN_URL);
		if(Constants.USER_NAME.equals(userName) && Constants.PASSWORD.equals(passWord)) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(userName);
			String ticketName = MD5Util.encodeByMD5(userName);
			//放入缓存
			CacheService cacheService = ToolsSpringContextUtil.getBean(serviceName);
			cacheService.putCacheUser(ticketName, userInfo);
			Cookie cookie = new Cookie(Constants.SSO_COOKIE, ticketName);
			response.addCookie(cookie);
			response.sendRedirect(returnUrl);
		}
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
