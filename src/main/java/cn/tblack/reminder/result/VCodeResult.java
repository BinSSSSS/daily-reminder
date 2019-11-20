package cn.tblack.reminder.result;

/**
 * @发送验证码时返回的结果类型
 * @author TD唐登
 * @Date:2019年11月1日
 * @Version: 1.0(测试版)
 */
public class VCodeResult extends WebResult {

	private int seconds; // 表示需要多久才能再次发送验证码

	public VCodeResult() {
		super();
	}

	public VCodeResult(boolean success, String msg) {
		super(success, msg);
	}

	public VCodeResult(boolean success, String msg, int seconds) {
		super(success, msg);
		this.seconds = seconds;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	@Override
	public String toString() {
		return "VCodeResult [seconds=" + seconds + "]";
	}

}
