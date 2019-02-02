package hd.common.base;

import java.io.Serializable;

/**
 * zTree实体
 * 
 * @author mao
 *
 */
public class ZtreeNode implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 权限ID
	 */
	private String id;

	/**
	 * 权限父ID
	 */
	private String pId;

	/**
	 * 权限名称
	 */
	private String name;

	/**
	 * 是否默认打开
	 */
	private boolean open;

	/**
	 * 是否默认选上
	 */
	private boolean checked;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}