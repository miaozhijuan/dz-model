package hd.service;

import hd.common.base.BaseService;
import hd.common.base.ZtreeNode;
import hd.entity.SysRole;

import java.util.List;

/**
 * 系统角色服务层接口
 * 
 * @author mao
 *
 */
public interface SysRoleService extends BaseService<SysRole> {
	/**
	 * 获取权限信息
	 * 
	 * @param userId
	 * @return
	 */
	List<ZtreeNode> allotRole(String userId);

	/**
	 * 保存分配的角色
	 * 
	 * @param userId
	 * @param roleIds
	 */
	void saveRole(String userId, String roleIds);
}
