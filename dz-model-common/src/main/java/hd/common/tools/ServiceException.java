package hd.common.tools;

/**
 * 自定义异常
 * 
 * @author maomao
 *
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(Throwable ex) {
		super(ex);
	}

	public ServiceException(String msg, Throwable ex) {
		super(msg, ex);
	}
}