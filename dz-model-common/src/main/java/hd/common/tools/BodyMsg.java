package hd.common.tools;

import java.io.Serializable;

/**
 * 消息处理类
 * 
 * @author maomao
 *
 */
public class BodyMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	public Integer code = new Integer(0);

	public String msg = new String();

	public Object data = "";

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void isSuccess() {
		this.setCode(0);
		this.setMsg("操作成功!");
	}

	public void isSuccess(String msg) {
		this.setCode(0);
		this.setMsg(msg);
	}

	public void isFail() {
		this.setCode(1);
		this.setMsg("操作失败!");
	}

	public void isFail(String msg) {
		this.setCode(1);
		this.setMsg(msg);
	}

	public void isFail(int code, String msg) {
		this.setCode(code);
		this.setMsg(msg);
	}

	public void putData(Object obj) {
		this.setData(obj);
	}
}