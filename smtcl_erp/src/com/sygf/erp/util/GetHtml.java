package com.sygf.erp.util;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class GetHtml {
	/**
	 * 获取html内容
	 * @param url
	 * @return
	 */
	public String getBody(String url){
		try {
			Parser parser = new Parser (url);
			NodeList list = parser.parse (null);
			Node node = list.elementAt(0);
			NodeList sublist = node.getChildren();
			if(sublist!=null){
				String body = sublist.toHtml().split("<body>")[1];
				body = body.substring(0,body.length()-9);
				return body;
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args){
		GetHtml a = new GetHtml();
		System.out.println(a.getBody("D:/workspace/smtcl_erp/WebRoot/html/test.html"));
	}
}
