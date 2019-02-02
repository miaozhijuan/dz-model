package hd.common.serach;

import java.io.Serializable;

/***
 * 查询条件定义
 * 
 * @author maomao
 *
 */
public class SearchCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 等于 */
	public static final String OP_EQUAL = "EQUAL";
	/** 不等于 */
	public static final String OP_NOTEQUAL = "NOTEQUAL";
	/** 大于等于 */
	public static final String OP_GREATER = "GREATER";
	/** 小于等于 */
	public static final String OP_SMALLER = "SMALLER";
	/** 大于 */
	public static final String OP_JUSTGREATER = "JUSTGREATER";
	/** 小于 */
	public static final String OP_JUSTSMALLER = "JUSTSMALLER";
	/** 模糊 */
	public static final String OP_LIKE = "LIKE";
	/** 左关联 */
	public static final String OP_LMATCH = "LMATCH";
	/** 右关联 */
	public static final String OP_RMATCH = "RMATCH";
	/** 包含 */
	public static final String OP_IN = "IN";
	/** 不包含 */
	public static final String OP_NOTIN = "NOTIN";
	/** 接sql串 */
	public static final String OP_STRING = "STRING";
	/** 属性 */
	private String field;
	/** 比较符号 */
	private String operator;
	/** 属性值 */
	private Object value;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}