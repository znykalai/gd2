package com.sygf.erp.baseData.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sygf.erp.util.GetHtml;
import com.sygf.erp.util.GetParam;
import com.sygf.erp.util.exchange;

public class GetHtmlAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String operType = request.getParameter("operType");
			if (operType.equals("getHtml")){
				return getHtml(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取html页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getHtml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap mapPara = GetParam.GetParamValue(request, "utf-8", "utf-8");
			GetHtml html = new GetHtml();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(exchange.toHtml(html.getBody(mapPara.get("url").toString())));
			response.getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
