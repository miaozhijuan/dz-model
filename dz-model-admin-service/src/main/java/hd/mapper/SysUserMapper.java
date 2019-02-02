package hd.mapper;

import hd.common.serach.SearchConditions;
import hd.entity.SysUser;

import java.util.List;

public interface SysUserMapper {
	int deleteByPrimaryKey(String id);

	int insert(SysUser record);

	int insertSelective(SysUser record);

	SysUser selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SysUser record);

	int updateByPrimaryKey(SysUser record);

	List<SysUser> list(SearchConditions scs);
}