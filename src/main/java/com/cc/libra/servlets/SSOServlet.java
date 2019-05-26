package com.cc.libra.servlets;

import com.cc.libra.actions.Action;
import com.cc.libra.utils.ExceptionUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by chenchen39 on 2018/9/29.
 */
@WebServlet(name="ssoLogin", value="/ssoLogin")
public class SSOServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        try {
            String act = req.getParameter("act");
            if(StringUtils.isBlank(act) || act.equals("ssoInit")) {
                renderHtml(req, resp);
            }else if(act.equals("static")) {
                renderResource(req, resp, req.getParameter("path"));
            }
            else {
                renderAction(req, resp);
            }
        } catch (Exception e) {
            ExceptionUtil.getExceptionStack(e);
        }
    }

    public void renderResource(HttpServletRequest req, HttpServletResponse resp, String resourcePath)throws IOException {
        InputStream resourceInput = super.getClass().getResourceAsStream(resourcePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(resourceInput));
        StringBuilder sb = new StringBuilder();
        String lineHtml = "";
        while((lineHtml =br.readLine()) != null) {
            sb.append(lineHtml).append("\n");
        }
        String content = sb.toString();
        br.close();
        resp.getWriter().write(content);
        resp.getWriter().close();
    }

    private void renderAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String actionName = req.getParameter("act");
        Action action = Action.getAction(actionName);
        action.handle(req, resp);
    }

    private void renderHtml(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String resourcePath = "/libraPages/ssoIndex.jsp";
        renderResource(req, resp, resourcePath);
    }


}
