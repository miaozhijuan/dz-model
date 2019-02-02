package hd.mapper;

import hd.common.base.ZtreeNode;
import hd.common.serach.SearchConditions;
import hd.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {
	int deleteByPrimaryKey(String id);

	int insert(SysRole record);

	int insertSelective(SysRole record);

	SysRole selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SysRole record);

	int updateByPrimaryKey(SysRole record);

	List<SysRole> list(SearchConditions scs);

	List<ZtreeNode> allotRole(String userId);
}