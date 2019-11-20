var header = $("meta[name='_csrf.parameterName']").attr("content");
var token = $("meta[name='_csrf.token']").attr("content");

// 在所有的Ajax请求提交之前触发- 用于设置请求头
$(document).ajaxSend(function(e, xhr, options) {
	//console.log("ajaxsend");
	xhr.setRequestHeader('X-CSRF-TOKEN', token);
})