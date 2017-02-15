package alai.znyk.test;

import alai.znyk.server.SqlTool;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String back=SqlTool.add动作指令("1", "60001", "501", "上货", 0, "1");
		//String back=SqlTool.add动作指令("1", "1", "60002", "下货", 0, "1");
		//String sql="update 库存托盘  set 货位号=NULL,方向=NULL where 托盘编号="+"'"+1+"'";
		 
		//String back=SqlTool.insert(new String[]{sql});
		//System.out.println(back);
		
		//String s="501=1|502=1|503=1|504=0|505=1|506=0|507=1|508=0|509=1|510=0|511=1|512=0|513=1|514=0";
		//String sm[]=s.split("=");
		//for(int i=0;i<sm.length;i++)
		//System.out.println(sm[i]);
		
		String st="1";
		System.out.println(st.equals(1+""));

	}

}
