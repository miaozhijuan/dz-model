package hd.config;

import java.util.List;

import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;

/**
 * 业务层基类
 * 
 * @author maomao
 *
 */
public interface BaseService<T> {

	/**
	 * 新增
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(T record);

	/**
	 * 根据主键删除
	 * 
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 修改
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(T record);

	/**
	 * 根据主键返回对象
	 * 
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(String id);

	/**
	 * 查询集合
	 * 
	 * @param scs
	 * @return
	 */
	SearchResult<T> getSearchResult(SearchConditions scs);

	/**
	 * 查询集合
	 * 
	 * @param scs
	 * @return
	 */
	List<T> list(SearchConditions scs);
}