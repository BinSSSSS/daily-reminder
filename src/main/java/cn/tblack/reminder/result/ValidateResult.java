package cn.tblack.reminder.result;

import java.util.HashMap;

/**
 * @表示使用BootstrapValidator进行验证的时候返回的结果
 * @author TD唐登
 * @Date:2019年11月1日
 * @Version: 1.0(测试版)
 */
public class ValidateResult extends HashMap<String, Boolean> {

	private static final long serialVersionUID = 1L;

	private boolean valid;

	public ValidateResult() {
		super();
	}

	public ValidateResult(boolean valid) {
		super();
		put("valid", valid);
	}

	public boolean isValid() {
		return valid;
	}

	public void setValidate(boolean valid) {
		this.valid = valid;
		put("valid", valid);
	}

}
