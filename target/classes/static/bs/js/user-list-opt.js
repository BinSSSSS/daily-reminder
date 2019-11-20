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
 * @删除指定的用户
 * @param id
 * @returns
 */
function deleteUser(id) {

	$.ajax({
		url : "/bs/user/delete-user",
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