package cn.tblack.reminder.util;

import cn.tblack.reminder.mail.EmailSender;

public class EmailSenderUtils {

	private static EmailSender emailSender;

	public static EmailSender getEmailSender() {
		return emailSender;
	}

	public static void setEmailSender(EmailSender emailSender) {
		EmailSenderUtils.emailSender = emailSender;
	}
}
