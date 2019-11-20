function sortNumber(a, b) {
	return a - b;
}
// 将2000-01-01 00:00形式的时间转化为Date对象
function createDate(date) {
	var ps = date.split(" ");
	var pd = ps[0].split("-");
	var pt = ps.length > 1 ? ps[1].split(":") : [ 0, 0 ];
	return new Date(pd[0], pd[1] - 1, pd[2], pt[0], pt[1]);
}
function singleCron(date) {
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();

	cron = "0 " + minute + " " + hour + " " + day + " " + month + " ?";

	return cron;
}
// 表示提前预定好的间隔时间，单位为小时
var intervalArr = new Array(1, 24, 7 * 24, 30 * 24);
function reminderUpdating(date, index) {

	var cron = null;
	// 构建单次提醒
	if (index == 0) {
		cron = singleCron(date);
	}
	// 表示多次提醒
	else if (index == 1) {

		var intervalIndex = $("#repeatInterval-select").get(0).selectedIndex;

		// console.log(intervalArr[intervalIndex]);

		// 拿到间隔时间
		var interval = intervalArr[intervalIndex];

		var month = "*";
		var day = date.getDate();
		var minute = date.getMinutes();
		var hour = date.getHours();
		var week = "?";

		// 通过间隔时间生成一个循环的Cron表达式
		// 间隔时间为一个月
		if (interval >= 24 * 30) {

			var newDate = new Date();
			newDate.setTime(date.getTime());
			newDate.setDate(date.getDate() + 1);

			// 如果设置的日期为最后一天的时候
			if (newDate.getMonth() != date.getMonth()) {

				day = "L";
			}
		}
		// 间隔时间为7天的时候
		else if (interval >= 24 * 7) {
			day = "?";
			week = date.getDay() + 1;
			// console.log(date.getDay());
		}
		// 间隔时间为1天的时候
		else if (interval >= 24) {
			day = "*";
			// day = "1/1";
		}
		// 间隔时间为一个小时
		else {
			day = "*";
			hour = "1/1";
		}
		cron = "0 " + minute + " " + hour + " " + day + " " + month + " "
				+ week;

	}
	// 表示指定范围的提醒
	else if (index == 2) {

		// console.log("范围提醒");
		var intervalIndex = $("#repeatInterval-select").get(0).selectedIndex;

		var cron = null;
		// 表示朝九晚五每小时执行
		if (intervalIndex == 0) {
			cron = "0 0 9-17 * * ?";
		}
		// 表示周一至周五每天执行
		else if (intervalIndex == 1) {
			cron = "0 " + date.getMinutes() + " " + date.getHours()
					+ " ? * 2-6";
		}
		// 表示周末每天执行
		else if (intervalIndex == 2) {
			cron = "0 " + date.getMinutes() + " " + date.getHours()
					+ " ? * 1,7";
		}
		// 表示1号到15号每天执行
		else {
			cron = "0 " + date.getMinutes() + " " + date.getHours()
					+ " 1-15 * ?";
		}
	}
	// 表示自定义Cron提醒
	else {

	}

	$("#cron").val(cron);
	console.log("当前生成的Cron表达式: " + cron);
}

$(function() {

	// 对于 boostrap datetimepicker的一些常用配置
	// 该 bootstrap是http://eonasdan.github.io/bootstrap-datetimepicker/中的

	// 表示提前预定好的执行范围- 1表示为朝九晚五每小时执行 2表示周一到周五 3表示周末 4 表示每月1号到15号
	var rangeIntervalOptions = new Array(1, 2, 3, 4);

	var dateTimeFormat = 'YYYY-MM-DD HH:mm';
	var timeFormat = "HH:mm";

	$('#datetimepicker').datetimepicker({
		format : dateTimeFormat,
		locale : moment.locale('zh-cn'),
		minDate : new Date()
	// 最小能够设置的时间为当前时间
	})
	// 绑定到更改事件，当日期发生改变的时候触发该事件
	.bind("dp.change", function(e) {

		var date = new Date(e.date);

		var index = $("#isRepeat-select").get(0).selectedIndex;

		reminderUpdating(date, index);
	});

	// 当选择改变的时候触发
	$("#isRepeat-select").change(
			function() {

				var index = $(this).get(0).selectedIndex;
				// var date = createDate($("#reminder-date").val());

				$("#cron").attr("disabled", "disabled");
				$("#repeatInterval-select").removeAttr("disabled");
				$("#reminder-date").removeAttr("disabled");
				// 表示单次提醒
				if (index == 0) {
					$("#repeatInterval-select").attr("disabled", "disabled");

					var dataOptions = $('#datetimepicker').data(
							"DateTimePicker").options();
					dataOptions.format = dateTimeFormat;
					dataOptions.minDate = new Date();
					$('#datetimepicker').data("DateTimePicker").options(
							dataOptions);
				}
				// 多次提醒
				else if (index == 1) {
					// 更新Cron
					var options = $("#repeatInterval-select").find("option");
					$(options[0]).text("每小时");
					$(options[1]).text("每天");
					$(options[2]).text("每周");
					$(options[3]).text("每月");
					$("#repeatInterval-select").removeAttr("disabled");

					var dataOptions = $('#datetimepicker').data(
							"DateTimePicker").options();
					dataOptions.format = dateTimeFormat;
					dataOptions.minDate = new Date();
					$('#datetimepicker').data("DateTimePicker").options(
							dataOptions);
					return;
				}
				// 指定范围提醒
				else if (index == 2) {
					var options = $("#repeatInterval-select").find("option");
					$(options[0]).text("朝九晚五每小时一次");
					$(options[1]).text("周一至周五每天一次");
					$(options[2]).text("周六到周日每天一次");
					$(options[3]).text("1号到15号每天一次");
					$("#repeatInterval-select").removeAttr("disabled");

					// 将时间选择器改为只能选择时间
					var minDate = new Date();
					minDate.setMinutes(0);
					minDate.setHours(0);
					// console.log($('#datetimepicker').data("DateTimePicker").options());
					var dataOptions = $('#datetimepicker').data(
							"DateTimePicker").options();
					dataOptions.format = timeFormat;
					dataOptions.minDate = minDate;
					$('#datetimepicker').data("DateTimePicker").options(
							dataOptions);

				}
				// 自定义Cron表达式提醒
				else {

					// 选择时间不可用. 提醒间隔不可用
					$("#repeatInterval-select").attr("disabled", "disabled");
					$("#reminder-date").attr("disabled", "disabled");
					// Cron表达式可用
					$("#cron").removeAttr("disabled");

				}
				reminderUpdating(new Date($('#datetimepicker').data(
						"DateTimePicker").date()), index);
			});

	// 当间隔时间发生改变的时候
	$("#repeatInterval-select").change(
			function() {

				var index = $("#isRepeat-select").get(0).selectedIndex;

				reminderUpdating(new Date($('#datetimepicker').data(
						"DateTimePicker").date()), index);
	});
	
	function reminderValidator(){
		$("#reminder-form").bootstrapValidator({
			excluded : [ ':disabled' ], // 对于禁用的域不进行验证

			fields : {

				date : {
					validators : {
						notEmpty : {
							message : '日期不能为空'
						}
					}
				},
				cron : {
					validators : {
						notEmpty : {
							message : 'cron表达式不能为空'

						},
						remote : {
							message : '非法表达式',
							url : "/reminder/verify-cron-expression",
							type : 'post',
							delay : 1500

						}
					}
				},
				title : {
					validators : {
						notEmpty : {
							message : '邮件的标题不能为空'
						}
					}
				},
				content : {
					validators : {
						notEmpty : {
							message : '邮件内容不能为空'
						},
						stringLength : {
							message : '内容长度不能超过200个字符',
							max : 200
						}
					}
				},
				file : {
					validators : {

					}
				}

			}
		});
	}
	reminderValidator();
	
	// 提交表单的时候触发
	$("#reminder-form").submit(
			function() {
				// 首先验证验证是否通过
				var validator = $("#reminder-form").data("bootstrapValidator");

				// 不管Cron是否被禁用， 在传递之前都需要进行Cron字段的验证
				validator.validateField("cron");

				if (!validator.isValidField("cron")) {
					//console.log("验证失败");
					return;
				}
				//console.log("验证成功");
				
				if (validator.isValid()) {

					// 提交到后台   

					// 要提交的数据：
					var formData = new FormData(document
							.getElementById("reminder-form"));
//					console.log(formData.get("attachment"));
					

					if(formData.get("cron") == null){
						formData.append("cron", $("#cron").val());
					}
					
					console.log(formData.get("cron"));
					// 判断当前是单次提醒还是重复提醒
					// 多次提醒的时候需要添加一个DelayCron对象
					if ($("#isRepeat-select").get(0).selectedIndex == 1) {
						formData.append("delayCron", singleCron(createDate($(
								"#reminder-date").val())));
					}   
					$.ajax({  
						url : "/reminder/addReminder",
						type : 'POST',
						processData : false, // 数据不需要处理
						contentType : false, // 类型按照表单类型提交
						data : formData,       
						success : function(data) {
							//弹出添加信息
							document.getElementById(
                            "tips-modal-btn").click();
							$(".modal-tips").text(data.msg);
							
							//如果当前提醒添加成功。清空 日期、title、content、attachment、cron
							if(data.success){
								var $form = $("#reminder-form");
								$form.find("input[name=date]").val("");
								$form.find("input[name=title]").val("");
								$form.find("input[name=attachment]").val("");
								$form.find("input[name=cron]").val("");
								$form.find("textarea[name=content]").val("");
								$form.find("input[name=attachment]").val("");
								//重置验证对象的状态
								validator.destroy();
								$("#reminder-form").data("bootstrapValidator",null);
								
								//重新添加验证
								reminderValidator();
							}
							
						}      
					});    
				}
			});

});