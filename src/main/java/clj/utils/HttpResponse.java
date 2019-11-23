package clj.utils;

/**
 * 公共的返回数据结构
 * @author ppd-03020342
 *
 * @param <T>
 */
public class HttpResponse<T> {

	/**
	 * 成功
	 */
	private static final int ZERO = 0;
	private static final String  SUCCESS= "调用成功";
	/**
	 * 失败
	 */
	private static final int ONE = 1;

	/**
	 * 传递message，表示失败
	 * 
	 * @param message
	 */
	public HttpResponse(String message) {
		this.date = System.currentTimeMillis();
		this.status = ONE;
		this.message = message;
		this.result = null;
	}

	/**
	 * 传递message，表示失败
	 * 
	 * @param message
	 */
	public HttpResponse(T result) {
		this.date = System.currentTimeMillis();
		this.status = ZERO;
		this.message = SUCCESS;
		this.result = result;
	}

	private int status;

	private String message;

	private T result;

	private Long date;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

}
