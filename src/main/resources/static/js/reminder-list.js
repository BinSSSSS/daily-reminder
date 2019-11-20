var App = {
    initBootstrapTable: function () {
        var columns = []
        columns.push(
            {
                title: "行号",
                align: 'left',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
            {
                title: "提醒类型",
                field: "schedule.repeat",
                align: 'left',
                valign: 'middle',
                formatter: function (value, row, index) {
                	if(value == 0){
                		return "单次提醒";
                	}else{
                		return "重复提醒";
                	}
//                	return value;
                }
            },
            {
                title: "发送标题",
                field: "mailSender.title",
                align: 'left',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return value;
                }
            },
            {
                title: "发送内容",
                field: "mailSender.content",
                align: 'left',
                valign: 'middle',
                formatter: function (value, row, index) {
                   
                    return value;
                }
            },
            {
                title: "下次发送时间",
                field: "mailSender.sendTime",
                align: 'left',
                valign: 'middle',
                formatter: function (value, row, index) {
                   
                	if(value != null)
                		return new Date(value).Format("yyyy-MM-dd hh:mm");
                	return null;
                }
            },
            {
                title: "有效性",
                field: "deprecated",
                align: 'left',
                valign: 'middle',
                formatter: function (value, row, index) {
                	
                	if(value == 1){
                		return "已暂停";
                	}else{
                		return "正在执行";
                	}
                }
            },
            {
                title: "创建时间",
                field: "createTime",
                align: 'left',
                valign: 'middle',
                formatter: function (value, row, index) {
                   
                	if(value != null)
                		return new Date(value).Format("yyyy-MM-dd hh:mm");
                	return null;
                }
            },
//            {
//                title: "最后完成时间",
//                field: "finishedTime",
//                align: 'left',
//                valign: 'middle',
//                formatter: function (value, row, index) {
//                   
//                	if(value != null)
//                		return new Date(value).Format("yyyy-MM-dd hh:mm");
//                	return null;
//                }
//            },
//            {
//                title: "已完成次数",
//                field: "finishedCount",
//                align: 'left',
//                valign: 'middle',
//                formatter: function (value, row, index) {
//                   
//                    return value;
//                }
//            },
            {
            	title: "操作",
            	align: 'left',
                valign: 'middle',
                formatter: function (value, row, index) {
                    
                    var actions = [];
                    actions.push("<a class='btn btn-danger btn-xs ' href='javascript:void(0)' " +
                    		"onclick='deleteReminder(" + row.id + ")'>删除</a>" );
                    
                    actions.push("<a class='btn btn-primary  btn-xs ' href='javascript:void(0)' " +
                    		"onclick='updateReminder(" + row.id + ")'>更新</a>" );
                    
                    actions.push("<a class='btn btn-primary  btn-xs ' href='javascript:void(0)' " +
                    		"onclick='sendTestReminder(" + row.id + ")'>测试</a>" );
                    
                    //如果当本个提醒未过期的时候，则可以开启暂停
                    if(row.deprecated == 0){
                    	actions.push("<a class='btn btn-warning btn-xs' href='javascript:void(0)'"+
                    			"onclick='pauseReminder(" + row.id + ")'>暂停</a>");
                    }else{
                    	actions.push("<a class='btn btn-success btn-xs' href='javascript:void(0)'"+
                    			"onclick='startReminder(" + row.id + ")'>开始</a>");
                    }
                    
                    return actions.join('');
                }
            }
        )

        $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN'])
        var searchText = $('.search').find('input').val()
        $('#App').bootstrapTable({
            url: '/reminder/search',
            sidePagination: "server",
            queryParamsType: 'pageNo,pageSize',
            method: 'get',
            striped: true,     //是否显示行间隔色
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,  //是否显示分页（*）
            paginationPreText: ' 上一页',
            paginationNextText: '下一页',
            search: true,
            searchText: searchText,
            searchAlign: 'right',
            searchOnEnterKey: false,
            trimOnSearch: true,
            pageNumber: 1,     //初始化加载第一页，默认第一页
            pageSize: 5,      //每页的记录行数（*）
//            pageList: [10, 20, 50, 100, 200], // 可选的每页数据
            totalField: 'totalElements', // 所有记录 count
            dataField: 'content', //后端 json 对应的表格List数据的 key
            columns: columns,
            smartDisplay: true,
            queryParams: function (params) {
                // 处理查询参数
                return {
                    pageSize: params.pageSize,
                    pageNo: params.pageNumber - 1,
                    sortName: params.sortName,
                    sortOrder: params.sortOrder,
                    searchText: params.searchText
                }
            },
            responseHandler: function (response) {
//                console.log(response)
                // 类似 Filter，处理响应数据；记得要返回 response
                $('#App').bootstrapTable('getOptions').data = response
                return response
            }
        })
    },

    init: function () {
        App.initBootstrapTable()
    }
}

$(App.init())