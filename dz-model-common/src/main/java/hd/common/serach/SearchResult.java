package hd.common.serach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索实体类
 * 
 * @author maomao
 *
 */
public class SearchResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 查询结果列表 */
	private List<T> rows = new ArrayList<T>();

	/** 总记录数 */
	private long records;

	/** 当前页数 */
	private int page;

	/** 总页数 */
	private long total;

	/** 每页大小 */
	private int pageSize;

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}