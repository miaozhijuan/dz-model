package hd.common.serach;

import java.io.Serializable;

/**
 * 分页
 * 
 * @author mao
 *
 */
public class SearchPage implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 页码默认为1 */
	private int pageNum = 1;
	/** 每页的行数默认为10 */
	private int pageSize = 10;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
