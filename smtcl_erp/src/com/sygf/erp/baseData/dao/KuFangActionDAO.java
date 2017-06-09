package com.sygf.erp.baseData.dao;

import java.util.HashMap;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.sygf.util.Log;

public class KuFangActionDAO extends SqlMapClientDaoSupport {

	/**
	 * 立体库动作指令
	 * @return
	 */
	public List getActionCommand() {
		try{
			return getSqlMapClientTemplate().queryForList("KuFangActionDAO.getActionCommand",null);
		}catch (Exception e) {
			Log.error("KuFangActionDAO.getActionCommand方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取库存
	 * @param map
	 * @return
	 */
	public List getKuCunList(){
		try{
			return getSqlMapClientTemplate().queryForList("KuFangActionDAO.getKuCunList",null);
		}catch (Exception e) {
			Log.error("KuFangActionDAO.getKuCunList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取托盘号、方向
	 * @param map
	 * @return
	 */
	public List getTpMachineID(HashMap map) {
		try{
			return getSqlMapClientTemplate().queryForList("KuFangActionDAO.getTpMachineID",map);
		}catch (Exception e) {
			Log.error("KuFangActionDAO.getTpMachineID方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除事件
	 * @param map
	 * @return
	 */
	public String removeAll(HashMap map){
		String result="删除失败！";
		try{
			getSqlMapClientTemplate().delete("KuFangActionDAO.removeAll",map);
			return result="删除成功！";
		}catch (Exception e) {
			Log.error("KuFangActionDAO.removeAll方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取RFID、物料
	 * @param map
	 * @return
	 */
	public List getRfidWl(HashMap map) {
		try{
			return getSqlMapClientTemplate().queryForList("KuFangActionDAO.getRfidWl",map);
		}catch (Exception e) {
			Log.error("KuFangActionDAO.getRfidWl方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
