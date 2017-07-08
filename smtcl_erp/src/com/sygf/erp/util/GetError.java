package com.sygf.erp.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class GetError implements Serializable {
	/**
	 * 获取异常
	 */
	private static final long serialVersionUID = 1L;
	private static String errHtml;
	private static String date;
	private static GetError err;
	private GetError(){}
	public synchronized static GetError getInstace(){
		if(date==null){date=DateFormat.getDateTimeInstance().format(new Date());};
		if(err==null){err=new GetError(); return err;}else{return err;}
	};
	public String getArm(int con,String title){
		errHtml="";
		if(((con&0b01)==1)){	//堆垛机故障
			errHtml+="<span style='margin:15px;'>"+title+"区异常：堆垛机故障 &nbsp;"+date+"</span></br>";
		};
		if(((con&0b10)==2)){	//输送线故障
			errHtml+="<span style='margin:15px;'>"+title+"区异常：输送线故障 &nbsp;"+date+"</span></br>";
		};
		if(((con&0b100)==4)){	//x小车故障
			errHtml+="<span style='margin:15px;'>"+title+"区异常：x小车故障 &nbsp;"+date+"</span></br>";
		};
		if(((con&0b1000)==8)){	//y小车故障
			errHtml+="<span style='margin:15px;'>"+title+"区异常：y小车故障 &nbsp;"+date+"</span></br>";
		};
		if(((con&0b10000)==16)){};//5
		if(((con&0b100000)==32)){};//6
		if(((con&0b1000000)==64)){};//7
		if(((con&0b10000000)==128)){};//8
		if(((con&0b100000000)==256)){};//9
		if(((con&0b1000000000)==512)){};//10
		if(((con&0b10000000000)==1024)){};//11
		if(((con&0b100000000000)==2048)){};//12
		if(((con&0b1000000000000)==4096)){};//13
		if(((con&0b10000000000000)==8192)){};//14
		if(((con&0b100000000000000)==16384)){};//15
		if(((con&0b1000000000000000)==32768)){};//16
		if("".equals(errHtml)){date=null;};
		return errHtml;
	};
}
