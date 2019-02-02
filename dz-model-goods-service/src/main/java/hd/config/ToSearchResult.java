package hd.config;

import hd.common.serach.SearchResult;

import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * 分页数据适配器
 * 
 * @author mao
 *
 */
public class ToSearchResult<T> {
	/**
	 * 返回适配数据
	 * 
	 * @param pageInfo
	 * @return
	 */
	public SearchResult<T> getSearchResult(List<T> list) {
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		SearchResult<T> searchResult = new SearchResult<T>();
		searchResult.setPage(pageInfo.getPageNum());
		searchResult.setPageSize(pageInfo.getPageSize());
		searchResult.setRecords(pageInfo.getTotal());
		searchResult.setRows(pageInfo.getList());
		searchResult.setTotal(pageInfo.getPages());
		return searchResult;
	}
}
