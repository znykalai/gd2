package com.sygf.test;

import alai.znyk.plc.PLC;

public class Test {
	public static void  main(String[] ss){
		for(int i=0;i<15;i++){
			try{
				System.out.println(PLC.getIntance().line.getCarry(i).getÔØ¾ßÐòºÅ());
			}catch(Exception e){
				System.out.println(i+"=null");
			}
		}
	}

}
