package hd.common.base;

import java.io.Serializable;
import java.util.Date;

/**
 * pc端用户信息
 * 
 * @author mao
 *
 */
public class PcUserVo implements Serializable {
	private String id;

	private String phone;

	private Byte status;

	private Date createTime;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}