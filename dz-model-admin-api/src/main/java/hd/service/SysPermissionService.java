package hd.service;

import hd.common.base.BaseService;
import hd.common.base.ZtreeNode;
import hd.common.serach.SearchConditions;
import hd.entity.SysPermission;

import java.util.List;

/**
 * 系统权限服务层接口
 * 
 * @author mao
 *
 */
public interface SysPermissionService extends BaseService<SysPermission> {
	/**
	 * 获取页面菜单路径
	 * 
	 * @param scs
	 * @return
	 */
	List<SysPermission> menuList(SearchConditions scs);

	/**
	 * 根据角色获取权限
	 * 
	 * @param roleId
	 * @return
	 */
	List<ZtreeNode> allotPermission(String roleId);

	/**
	 * 角色分配权限
	 * 
	 * @param roleId
	 * @param permissionIds
	 */
	void savePermission(String roleId, String permissionIds);
	
	/**
	 * 权限集合
	 * @return
	 */
	List<ZtreeNode> zTreeNodeList();
}
