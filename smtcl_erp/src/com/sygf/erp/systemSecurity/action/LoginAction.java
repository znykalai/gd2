package com.sygf.erp.systemSecurity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
			response.setCharacterEncoding("utf-8");
			String username = new String(request.getParameter("UserName").getBytes("iso-8859-1"),"utf-8");	//用户名?
			String password = request.getParameter("Password");  	//用户密码  
            if(username!=null&&password!=null
            		&&!username.equals("")&&!password.equals("")){
            	session.setAttribute("username", username);
                return mapping.findForward("index"); 
            }else{
				request.setAttribute("msg", "请确认您输入的帐号和口令是否正确? ");
				return mapping.findForward("failureHttp");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
