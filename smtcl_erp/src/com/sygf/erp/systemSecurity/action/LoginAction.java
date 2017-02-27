package com.sygf.erp.systemSecurity.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;

import com.sygf.erp.baseData.dao.HomeActionDAO;
import com.sygf.erp.systemSecurity.dao.LoginDAO;
import com.sygf.erp.util.GetApplicationContext;
import com.sygf.util.security.MD5;

public class LoginAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String operationType = request.getParameter("operationType");
		if (operationType.equals("login")){
			return executeLogin(mapping, form, request, response);
		}
		return null;
	}
	private ActionForward executeLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8"); 	//用户密码  
			ApplicationContext context = GetApplicationContext.getContext(request);
			LoginDAO dao = (LoginDAO)context.getBean("loginDAO");
			if(request.getParameter("UserName")==null){
				request.setAttribute("msg", "用户名或密码不正确，请重新输入。");
				return mapping.findForward("failureHttp");
			}
			String username = new String(request.getParameter("UserName").getBytes("iso-8859-1"),"utf-8");	//用户名?
			String password = request.getParameter("Password"); 
			MD5 md5 = new MD5();
			HashMap map = new HashMap();
			map.put("userName", username);
			map.put("password", password);
			List list = dao.getUserPassword(map);
			if(list!=null&&list.size() > 0){
            	session.setAttribute("username", username);
            	session.setAttribute("juese", ((HashMap)list.get(0)).get("角色"));
                return mapping.findForward("index"); 
            }else{
				request.setAttribute("msg", "用户名或密码不正确，请重新输入。");
				return mapping.findForward("failureHttp");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}