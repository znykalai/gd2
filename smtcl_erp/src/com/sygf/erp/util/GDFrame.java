package com.sygf.erp.util;

import alai.znyk.test.PLCFrame;

public class GDFrame {
	static PLCFrame frame;
	public static synchronized void showFrame(){
		if(frame==null){
			frame=new PLCFrame();
			frame.setVisible(true);
			
		}else{
			frame.setVisible(true);
		}
	}
}
