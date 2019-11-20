<head>
    <meta charset="UTF-8">
	
	<#if _csrf??>
	<meta name="_csrf.token" content="${(_csrf.token)!}"/>
    <meta name="_csrf.parameterName" content="${(_csrf.parameterName)!}"/>
	</#if>
    <title>Daily Reminder</title>
	
	
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="/images/fav-icon.png" rel="icon">
    <#-- <link rel="stylesheet" href="/css/app.css"> -->
	<link rel="stylesheet" href="/css/auth.css">
	
    <script src="/js/jquery-3.3.1.min.js"></script>
    <script src="/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script src="/js/date-format.js"></script>

	<#--引入 react 动态的添加 DOM元素所需要的JS文件， react 和 react-dom 需要添加 babel之后才能使用 ReactDOM直接添加 <html标签> -->
	<script src="https://cdn.bootcss.com/react/15.4.2/react.min.js"></script>
    <script src="https://cdn.bootcss.com/react/15.4.2/react-dom.min.js"></script>
    <script src="https://cdn.bootcss.com/babel-standalone/6.22.1/babel.min.js"></script>

    <link rel="stylesheet" href="/plugins/bootstrap-table/bootstrap-table.min.css">
    <script src="/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="/plugins/bootstrap-table/bootstrap-table-locale-all.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap-validator/0.5.3/js/bootstrapValidator.js"></script>
	<script src="https://cdn.bootcss.com/moment.js/2.24.0/moment.min.js"></script>
	<script src="https://cdn.bootcss.com/moment.js/2.24.0/locale/zh-cn.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
