package hd.mapper;

import hd.entity.MqMessage;

public interface MqMessageMapper {
	int deleteByPrimaryKey(String id);

    int insert(MqMessage record);

    int insertSelective(MqMessage record);

    MqMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MqMessage record);

    int updateByPrimaryKey(MqMessage record);
}