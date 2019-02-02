package hd.mapper;

import hd.entity.SysRolePermissionKey;

import java.util.List;

public interface SysRolePermissionMapper {
	int deleteByPrimaryKey(SysRolePermissionKey key);

	int insert(SysRolePermissionKey record);

	int insertSelective(SysRolePermissionKey record);

	void insertList(List<SysRolePermissionKey> list);
}