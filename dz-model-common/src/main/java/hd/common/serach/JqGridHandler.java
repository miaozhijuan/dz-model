package hd.common.serach;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;

/**
 * 处理页面的查询条件集合
 * 
 * @author maomao
 *
 */
public class JqGridHandler {

	private HttpServletRequest request;
	/** 页码 */
	private String pageNum;
	/** 每页的行数 */
	private String pageSize;
	/** 排序字段 */
	private String sidx;
	/** 排序规则 */
	private String sord;
	/** 查询条件集合 */
	private List<SearchRule> ruleList;

	public JqGridHandler() {
	}

	public JqGridHandler(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 遍历页面查询条件
	 * 
	 * @param scs
	 */
	public void getWheres(SearchConditions scs) {
		init();
		tranToSql(scs);
	}

	/**
	 * 初始化
	 */
	public void init() {
		if (null != request) {
			// 获取当前页码
			pageNum = request.getParameter("page");
			// 获取行数
			pageSize = request.getParameter("rows");
			// 获取排序字段
			sidx = request.getParameter("sidx");
			// 获取排序规则
			sord = request.getParameter("sord");
			// 获取查询条件集合
			String rules = request.getParameter("rules");
			if (StringUtils.isNotBlank(rules)) {
				ruleList = JSONArray.parseArray(rules, SearchRule.class);
			}
		}
	}

	/**
	 * 设置过滤条件
	 * 
	 * @param scs
	 */
	public void tranToSql(SearchConditions scs) {
		// 设置页数
		if (StringUtils.isNotBlank(pageNum)) {
			scs.getPage().setPageNum(Integer.parseInt(pageNum));
		}
		// 设置页行数
		if (StringUtils.isNotBlank(pageSize)) {
			scs.getPage().setPageSize(Integer.parseInt(pageSize));
		}
		// 排序
		if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)) {
			scs.setOrderBy(sidx + "  " + sord);
		}
		// 设置过滤条件
		if (null != ruleList && !ruleList.isEmpty()) {
			for (SearchRule rule : ruleList) {
				String field = rule.getField(), op = rule.getOp(), data = rule.getData();
				if ("EQUAL".equalsIgnoreCase(op)) {
					// 等于
					scs.addCondition(field, data, SearchCondition.OP_EQUAL);
				} else if ("GREATER".equalsIgnoreCase(op)) {
					// 大于等于
					scs.addCondition(field, data, SearchCondition.OP_GREATER);
				} else if ("SMALLER".equalsIgnoreCase(op)) {
					// 小于等于
					scs.addCondition(field, data, SearchCondition.OP_SMALLER);
				} else if ("LIKE".equalsIgnoreCase(op)) {
					// 模糊
					scs.addCondition(field, data, SearchCondition.OP_LIKE);
				}
			}
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getpageNum() {
		return pageNum;
	}

	public void setpageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public List<SearchRule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<SearchRule> ruleList) {
		this.ruleList = ruleList;
	}
}