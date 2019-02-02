package hd.common.serach;

import java.io.Serializable;

/***
 * 查询规则
 * 
 * @author maomao
 *
 */
public class SearchRule implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 查询字段 */
	private String field;
	/** 查询操作 */
	private String op;
	/** 查询值 */
	private String data;
	/** 字段类型 */
	private String type;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}