/**
 * @弹出模态的信息框显示信息
 * @param msg
 * @returns
 */
function showMessage(msg) {
	// 弹出添加信息
	document.getElementById("tips-modal-btn").click();
	$(".modal-tips").text(msg);
}

/**
 * @删除指定的提醒
 * @param id
 * @returns
 */
function deleteReminder(id) {

	$.ajax({
		url : "/reminder/delete-reminder",
		data : {
			id : id
		},
		dataType : 'json',
		type : 'post',
		success : function(data) {

			console.log(data);
			if (data.success) {
				window.location.reload();
			} else {
				showMessage(data.msg);
			}
		}
	});
}
/**
 * @开始指定的提醒
 * @param id
 * @returns
 */
function startReminder(id) {
	$.ajax({
		url : "/reminder/start-reminder",
		data : {
			id : id
		},
		dataType : 'json',
		type : 'post',
		success : function(data) {

			if (data.success) {
				window.location.reload();
			} else {
				showMessage(data.msg);
			}

		}
	});
}
/**
 * @暂停指定的提醒
 * @param id
 * @returns
 */
function pauseReminder(id) {
	$.ajax({
		url : "/reminder/pause-reminder",
		data : {
			id : id
		},
		dataType : 'json',
		type : 'post',
		success : function(data) {

			if (data.success) {
				window.location.reload();
			} else {
				showMessage(data.msg);
			}
		}
	});
}

/**
 * 测试按钮，测试某个邮件是否能够
 */

function sendTestReminder(id) {
	$.ajax({
		url : "/reminder/send-test-reminder",
		data : {
			id : id
		},
		dataType : 'json',
		type : 'post',
		success : function(data) {

			if (data.success) {
				showMessage(data.msg);
			} else {
				showMessage(data.msg);
			}
		}
	});
}
