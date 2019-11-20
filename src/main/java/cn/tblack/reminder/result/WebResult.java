package cn.tblack.reminder.result;

/**
 * @该对象包含了对于前台请求的一个结果。
 * 
 * @author TD唐登
 * @Date:2019年10月31日
 * @Version: 1.0(测试版)
 */
public class WebResult {

	private boolean success;		//是否成功
	private String msg;	//存放处理结果消息

	public WebResult(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public WebResult() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "WebResult [success=" + success + ", msg=" + msg + "]";
	}

}
