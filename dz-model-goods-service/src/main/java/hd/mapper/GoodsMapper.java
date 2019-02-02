package hd.mapper;

import hd.common.serach.SearchConditions;
import hd.entity.Goods;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface GoodsMapper {
	int deleteByPrimaryKey(String id);

	int insert(Goods record);

	int insertSelective(Goods record);

	Goods selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Goods record);

	int updateByPrimaryKey(Goods record);

	List<Goods> list(SearchConditions scs);

	int buckleWithholdAmount(@Param(value = "goodsId") String goodsId, @Param(value = "amount") int amount);
	
	int buckleAmount(@Param(value = "goodsId") String goodsId, @Param(value = "amount") int amount);
}