package hd.mapper;

import hd.common.serach.SearchConditions;
import hd.entity.ClientUser;

import java.util.List;

public interface ClientUserMapper {
	int deleteByPrimaryKey(String id);

	int insert(ClientUser record);

	int insertSelective(ClientUser record);

	ClientUser selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ClientUser record);

	int updateByPrimaryKey(ClientUser record);

	ClientUser selectByPhone(String phone);

	List<ClientUser> list(SearchConditions scs);
}