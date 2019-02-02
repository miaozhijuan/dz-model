package hd.utils;

import java.io.Serializable;
import java.util.List;

/**
 * PC端登陆用户信息
 * 
 * @author maomao
 *
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private String id;

	/**
	 * 用户真实姓名
	 */
	private String realName;

	/**
	 * 登陆时间戳
	 */
	private Long loginTime;

	/**
	 * 用户权限路径集合
	 */
	private List<String> uList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public List<String> getuList() {
		return uList;
	}

	public void setuList(List<String> uList) {
		this.uList = uList;
	}

}