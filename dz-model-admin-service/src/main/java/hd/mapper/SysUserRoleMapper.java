package hd.mapper;

import hd.entity.SysUserRoleKey;

import java.util.List;

public interface SysUserRoleMapper {
	int deleteByPrimaryKey(SysUserRoleKey key);

	int insert(SysUserRoleKey record);

	int insertSelective(SysUserRoleKey record);
	
	void insertList(List<SysUserRoleKey> list);
}