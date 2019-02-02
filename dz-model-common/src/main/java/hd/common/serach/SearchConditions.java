package hd.common.serach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 条件处理类
 * 
 * @author maomao
 *
 */
public class SearchConditions implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 查询条件集合 */
	private Collection<SearchCondition> conditions = new ArrayList<SearchCondition>();
	/** 分页 */
	private SearchPage page = new SearchPage();;
	/** 排序 */
	private String orderBy;
	/** 分组 */
	private String groupBy;

	/**
	 * 加一个条件集合
	 * 
	 * @param field
	 * @param list
	 * @param comp
	 */
	public void addConditionList(String field, List<Object> list, String comp) {
		SearchCondition condition = new SearchCondition();
		if (list.size() == 1) {
			condition.setField(field);
			if (comp.equals(SearchCondition.OP_IN)) {
				condition.setOperator(SearchCondition.OP_EQUAL);
			} else {
				condition.setOperator(SearchCondition.OP_NOTEQUAL);
			}
			condition.setValue(list.get(0));
		} else {
			condition.setField(field);
			condition.setOperator(comp);
			condition.setValue(list);
		}
		conditions.add(condition);
	}

	/**
	 * 加一个条件
	 * 
	 * @param field
	 * @param value
	 * @param comp
	 */
	public void addCondition(String field, Object value, String comp) {
		if (value != null) {
			SearchCondition condition = new SearchCondition();
			condition.setField(field);
			condition.setOperator(comp);
			condition.setValue(value);
			conditions.add(condition);
		}
	}

	/**
	 * 清除单个条件
	 * 
	 * @param field
	 */
	public void removeCondition(String field) {
		for (Iterator<SearchCondition> it = conditions.iterator(); it.hasNext();) {
			if (it.next().getField().equals(field)) {
				it.remove();
			}
		}
	}

	/**
	 * 清除所有条件
	 */
	public void clearCondition() {
		this.orderBy = null;
		this.groupBy = null;
		conditions.clear();
	}

	public Collection<SearchCondition> getConditions() {
		return conditions;
	}

	public void setConditions(Collection<SearchCondition> conditions) {
		this.conditions = conditions;
	}

	public SearchPage getPage() {
		return page;
	}

	public void setPage(SearchPage page) {
		this.page = page;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

}