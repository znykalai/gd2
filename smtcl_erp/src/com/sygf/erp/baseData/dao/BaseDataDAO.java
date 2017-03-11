package com.sygf.erp.baseData.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sygf.util.Log;

public class BaseDataDAO extends SqlMapClientDaoSupport {
	//获取所有物料
	public List selectWlList(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("BaseDataDAO.selectWlList",map);
		}catch (Exception e) {
			Log.error("BaseDataDAO.selectWlList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	//获取当前物料是否存在
	public List getWlCode(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("BaseDataDAO.getWlCode",map);
		}catch (Exception e) {
			Log.error("BaseDataDAO.getWlCode方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	//物料保存
	public boolean saveWlBaseData(HashMap map){
		boolean yesNo = false;
		try {
			getSqlMapClientTemplate().insert("BaseDataDAO.saveWlBaseData", map);
			yesNo = true;
		} catch (Exception e) {
			Log.error("BaseDataDAO.saveWlBaseData方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}
	
	//删除物料
	public boolean deleteWl(HashMap code){
		boolean yesNo = false;
		try{
			getSqlMapClientTemplate().delete("BaseDataDAO.deleteWl",code);
			yesNo = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return yesNo;
	}

	//查询当前操作的模组是否存在
	public List getMzYesNo(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("BaseDataDAO.getMzYesNo",map);
		}catch (Exception e) {
			Log.error("BaseDataDAO.getMzYesNo方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	//模组题头保存
	public boolean saveMzHead(HashMap map){
		boolean yesNo = false;
		try {
			getSqlMapClientTemplate().insert("BaseDataDAO.saveMzHead", map);
			yesNo = true;
		} catch (Exception e) {
			Log.error("BaseDataDAO.saveMzHead方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}

	//模组载具行保存
	public boolean saveMzRow(HashMap map){
		boolean yesNo = false;
		try {
			getSqlMapClientTemplate().insert("BaseDataDAO.saveMzRow", map);
			yesNo = true;
		} catch (Exception e) {
			Log.error("BaseDataDAO.saveMzRow方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}

	//查询当前操作的模组是否存在
	public List getMzzjList(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("BaseDataDAO.getMzzjList",map);
		}catch (Exception e) {
			Log.error("BaseDataDAO.getMzzjList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	//查询当前所有模组
	public List getMzHead(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("BaseDataDAO.getMzHead",map);
		}catch (Exception e) {
			Log.error("BaseDataDAO.getMzHead方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	//查询当前模组指令行
	public List getMzzlhList(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("BaseDataDAO.getMzzlhList",map);
		}catch (Exception e) {
			Log.error("BaseDataDAO.getMzzlhList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	//模组指令行保存
	public boolean saveZlhRow(HashMap map){
		boolean yesNo = false;
		try {
			getSqlMapClientTemplate().insert("BaseDataDAO.saveZlhRow", map);
			yesNo = true;
		} catch (Exception e) {
			Log.error("BaseDataDAO.saveZlhRow方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}

	//删除模组
	public boolean mz_delete(HashMap map){
		boolean yesNo = false;
		try {
			getSqlMapClientTemplate().delete("BaseDataDAO.mz_delete", map);
			yesNo = true;
		} catch (Exception e) {
			Log.error("BaseDataDAO.mz_delete方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}
	
	/**
	 * 查询pack题头
	 * @param map
	 * @return
	 */
	public List getHeadList(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("BaseDataDAO.getHeadList",map);
		}catch (Exception e) {
			Log.error("BaseDataDAO.getHeadList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询pack行
	 * @param map
	 * @return
	 */
	public List getPackRowList(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("BaseDataDAO.getPackRowList",map);
		}catch (Exception e) {
			Log.error("BaseDataDAO.getPackRowList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * pack保存
	 * @param map
	 * @return
	 */
	public boolean savePack(HashMap map){
		boolean yesNo = false;
		try {
			getSqlMapClientTemplate().insert("BaseDataDAO.savePack", map);
			yesNo = true;
		} catch (Exception e) {
			Log.error("BaseDataDAO.savePack方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}
	/**
	 * pack删除
	 * @param map
	 * @return
	 */
	public boolean deletePack(HashMap map){
		boolean yesNo = false;
		try {
			getSqlMapClientTemplate().delete("BaseDataDAO.deletePack", map);
			yesNo = true;
		} catch (Exception e) {
			Log.error("BaseDataDAO.deletePack方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}
	
	/**
	 * 模组上调序
	 * @param map
	 * @return
	 */
	public boolean updateXuhaoUp(HashMap map){
		boolean yesNo = false;
		try {
			getSqlMapClientTemplate().update("BaseDataDAO.updateXuhaoUp", map);
			yesNo = true;
		} catch (Exception e) {
			Log.error("BaseDataDAO.updateXuhaoUp方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}
	/**
	 * 获取权限设置-角色的最大ID
	 * @param map
	 * @return
	 */
	public List getMaxId(){
		try{
			return getSqlMapClientTemplate().queryForList("BaseDataDAO.getMaxId",null);
		}catch (Exception e) {
			Log.error("BaseDataDAO.getMaxId方法出现异常！" + e.getMessage());
			e.printStackTrace();
		};
		return null;
	}
	/**
	 * 权限重置
	 * @param map
	 * @return
	 */
	public boolean removeAllJueSe(HashMap map){
		boolean yesNo = false;
		try {
			getSqlMapClientTemplate().delete("BaseDataDAO.removeAllJueSe", map);
			yesNo=true;
		} catch (Exception e) {
			Log.error("BaseDataDAO.removeAllJueSe方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}
	/**
	 * 新加角色权限
	 * @param map
	 */
	public boolean insertJueSe(HashMap map) {
		boolean yesNo = false;
		try {
			getSqlMapClientTemplate().insert("BaseDataDAO.insertJueSe", map);
			yesNo=true;
		} catch (Exception e) {
			Log.error("BaseDataDAO.insertJueSe方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}
}
