package hd.mapper;

import java.util.List;

import hd.common.serach.SearchConditions;
import hd.entity.Order;

public interface OrderMapper {
	int deleteByPrimaryKey(String id);

	int insert(Order record);

	int insertSelective(Order record);

	Order selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Order record);

	int updateByPrimaryKey(Order record);

	List<Order> list(SearchConditions scs);
}