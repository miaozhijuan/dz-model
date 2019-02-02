package hd.mapper;

import hd.entity.GoodsWater;

public interface GoodsWaterMapper {
	int deleteByPrimaryKey(String id);

    int insert(GoodsWater record);

    int insertSelective(GoodsWater record);

    GoodsWater selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GoodsWater record);

    int updateByPrimaryKey(GoodsWater record);
}