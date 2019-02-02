package hd.common.finals;

/**
 * 后台管理端静态常量工具类
 * 
 * @author mao
 *
 */
public class UserFinalUtil {

	/**
	 * 用户操作失效时间(1小时)(后台管理用户)
	 **/
	public final static Long INVALID_TIME = 1000 * 60 * 60L;

	/**
	 * 当前用户(后台管理用户)
	 */
	public static final String CURRENT_USER = "user";

	/**
	 * 登陆验证码(后台管理用户)
	 */
	public static final String VERIFICATION_CODE = "code";

	/**
	 * 超级管理员ID(后台管理用户)
	 */
	public static final String ADMIN_ID = "01";

	/**
	 * 最高权限ID(后台管理用户)
	 */
	public static final String PERMISSION_ID = "0";
	
	/**
	 * 用户token(PC用户)
	 */
	public static final String TOKEN = "TOKEN";
	
	
	/**
	 * 用户操作失效时间(15天)(后台管理用户)
	 **/
	public final static Long TOKEN_TIME = 15*24*1000 * 60 * 60L;

	/**
	 * 用户信息(PC用户)
	 */
	public static final String PCUSER = "PcUser";
}
