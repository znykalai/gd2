package com.sygf.test;

import alai.znyk.plc.PLC;

public class Test {
	public static void  main(String[] ss){
		for(int i=0;i<15;i++){
			try{
				System.out.println(PLC.getIntance().line.getCarry(i).get�ؾ����());
			}catch(Exception e){
				System.out.println(i+"=null");
			}
		}
	}

}
