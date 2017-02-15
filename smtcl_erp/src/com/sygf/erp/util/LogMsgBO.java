package com.sygf.erp.util;

import com.sygf.util.Log;

public class LogMsgBO {
	public static void setErrorLog(String errorMsg,Exception e) {
		Log.error(errorMsg + "出现异常?");
		Log.error(e.getClass().toString() + " " + e.getMessage());
		for(int i=0;i<e.getStackTrace().length&&i<6;i++){
			Log.error(e.getStackTrace()[i]);
		}
		Log.error("\r\n");
	}
}
