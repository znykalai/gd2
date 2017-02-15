//package com.sygf.erp.util;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
///**
// * 物料调度
// * @author guofeng
// *
// */
//public class MaterialScheduling {
//	
//	
//	public String diaoDuQingQiu(){
//		String data = "aa,00";
//		ApplicationContext context = new ClassPathXmlApplicationContext("file:\\tomcat6.0\\webapps\\smtcl_erp\\WEB-INF\\applicationContext.xml");
//		MaterialSchedulingDAO dao = (MaterialSchedulingDAO)context.getBean("materialSchedulingDAO");
//		//订单PACK
//		List listDD = dao.getListDingDan();
//		//还有多少剩余货位
//		int huoWei = dao.getGoodsPos().size();
//		huoWei = 2;
//		//累计所有物料需要补充的数量
//		LinkedHashMap mapA = new LinkedHashMap();
//		//物料容积率
//		LinkedHashMap mapB = new LinkedHashMap();
//		int tp = 0;
//		//标识没有货位时，退出调度
//		boolean hwType = false;
//		
//		if(listDD!=null&&listDD.size()>0){
//			for(int i=0;i<listDD.size();i++){
//				HashMap map = new HashMap();
//				//查询条件，订单号
//				map.put("order_code", ((HashMap)listDD.get(i)).get("order_code"));
//				List listMZ = dao.getList(map);
//				//剩余现有量
//				LinkedHashMap prepare_num = new LinkedHashMap();
//				tp = mapA.size();//初始托盘
//				for(int j=0;j<listMZ.size();j++){
//					String material_code = ((HashMap)listMZ.get(j)).get("material_code").toString();//物料编码
//					int plan_num = Integer.parseInt(((HashMap)listMZ.get(j)).get("plan_num").toString());//所需数量
//					int prepareNum = Integer.parseInt(((HashMap)listMZ.get(j)).get("prepare_num").toString());//现有量
//					//判断物料是否存在prepare_num 中
//					if(prepare_num.get(material_code)==null){
//						int s = prepareNum-plan_num;	//现有量-所需数量 = 剩余库存;
//						if(mapA.size() > 0 && mapA.get(material_code)!=null){
//							if(Integer.parseInt(mapA.get(material_code).toString()) < 0){
//								s = 0 - plan_num;	//为了累计所有物料的需求数
//							}
//						}
//						prepare_num.put(material_code, s);	//将剩余库存统计出来放到临时MAP中；
//					}else{
//						prepare_num.put(material_code, Integer.parseInt(prepare_num.get(material_code).toString()) - plan_num);
//					}
//					mapB.put(material_code, ((HashMap)listMZ.get(j)).get("ratio"));//容积率
//				}
//				System.out.println("订单所缺物料集合="+map.get("order_code")+""+prepare_num);
//				
//				Object s[] = prepare_num.keySet().toArray();
//				for(int j = 0; j < prepare_num.size(); j++) {
//					int c = Integer.parseInt(prepare_num.get(s[j]).toString());
//					if(c >= 0){	//如果现有量满足 则无需调度；
//						continue;
//					}
//					if(mapA.get(s[j])!=null){
//						c = c + Integer.parseInt(mapA.get(s[j]).toString());
//					}
//					int a = Integer.parseInt((c + "").replace("-", ""));
//					boolean dd = false;
//					//需要多少个货位
//					while(a > Integer.parseInt(mapB.get(s[j]).toString())){
//						a = a - Integer.parseInt(mapB.get(s[j]).toString());
//						if(dd){
//							c++;
//						}
//						dd = true;
//						tp++;
//					}
//					if(dd && a > 0 && a <= Integer.parseInt(mapB.get(s[j]).toString())){
//						tp++;
//						c++;
//					}
//					//货位不足
//					if(tp > huoWei){
//						hwType = true;
//						tp--;
//					}else if(tp == huoWei){//如果没有空余货位直接退出
//						hwType = true;
//						break;
//					}else{
//						tp++;
//					}
//					mapA.put(s[j],c);
//					if(hwType){
//						break;
//					}
//				}
//				//跳出循环匹配物料
//				if(hwType){
//					System.out.println("货位不足!");
//					break;
//				}
//			}
//			System.out.println("货位中还能放置"+huoWei+"个托盘");
//			System.out.println("需要的托盘数量="+tp);
//			System.out.println("需要的物料数量="+mapA);
//		}
//		return data;
//	}
//	
//	public static void main(String[] args){
//		MaterialScheduling a = new MaterialScheduling();
//		a.diaoDuQingQiu();
//	}
//}