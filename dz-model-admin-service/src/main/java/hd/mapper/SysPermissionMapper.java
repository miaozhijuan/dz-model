package hd.mapper;

import hd.common.base.ZtreeNode;
import hd.common.serach.SearchConditions;
import hd.entity.SysPermission;

import java.util.List;

public interface SysPermissionMapper {
	int deleteByPrimaryKey(String id);

	int insert(SysPermission record);

	int insertSelective(SysPermission record);

	SysPermission selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SysPermission record);

	int updateByPrimaryKey(SysPermission record);

	List<SysPermission> list(SearchConditions scs);

	List<SysPermission> menuList(SearchConditions scs);

	List<ZtreeNode> allotPermission(String roleId);

	List<ZtreeNode> zTreeNodeList();
}