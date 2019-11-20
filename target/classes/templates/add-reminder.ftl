<#include 'common/header-uclose.ftl'>
	<link href="/css/reminder.css" rel="stylesheet">
	 <link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
</head>  
<#include 'common/nav.ftl'>
  <div class="container text-primary text-center">
    <div class="jumbotron">
      <h1 class="">添加一个新提醒</h1>

       <div class="top-padding">
        <form class="" id="reminder-form" method="post" action="javascript:void(0)" enctype="multipart/form-data">
          <div class="form-group row">
            <label class="col-sm-3 col-form-label" for="reminder-date">提醒时间：</label>
            <!--指定 date标记-->
            <div class='input-group date col-sm-3' id='datetimepicker'  >
                <input type='text' class="form-control"  id="reminder-date" name="date"/>
               
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>        
            </div>
           </div>
           <div class="form-group row">
            <label class="col-sm-3 col-form-label" >提醒类型：</label>
              <select class="form-control col-sm-3" style="width: auto;" id="isRepeat-select">
                <option>单次提醒</option>
                <option>重复提醒</option>
                <option>范围提醒</option>
                <option>自定义Cron提醒</option>
              </select>
            </div>
            <div class="form-group row">
            <label class="col-sm-3 col-form-label" >提醒间隔：</label>
              <select class="form-control col-sm-3" style="width: auto;" id="repeatInterval-select" disabled="disabled">
                <option value="0">每个小时</option>
                <option value="1">每天</option>
                <option value="2">每周</option>
                <option value="3">每月</option>
              </select>
            </div>
            <div class="form-group row">
            <label class="col-sm-3 col-form-label" >Cron表达式：</label>
               <div class='input-group  col-sm-3' >
                 <!-- <span class="input-group-addon" >C</span> -->
                <input class="form-control" type="text" name="cron" placeholder="提交一个合法的Cron表达式" id="cron" disabled>
               </div>
            </div> 
           <!--  <div class="form-group row">
              <label class="col-sm-3 col-form-label" >提醒信息发送到哪个邮箱：</label>
              <div class='input-group  col-sm-3'  >
                 <span class="input-group-addon">E</span>
                <input class="form-control" type="text" name="email" placeholder="myname@163.com">
               </div>
            </div> -->
            <div class="form-group row">
              <label class="col-sm-3 col-form-label" >邮件的标题：</label>
              <div class='input-group  col-sm-3' id='custom-cron' >
                 <!-- <span class="input-group-addon">T</span> -->
                <input class="form-control" type="text" name="title" placeholder="主题名称" required>
              </div>
            </div>
            <div class="form-group row">
              <label class="col-sm-3 col-form-label" >发送的内容：</label>
              <div class='input-group  col-sm-3'>
                <textarea class="form-control" id="content" name="content">
                </textarea>
              </div>
              <small>可以在当中插入Html标签</small>
            </div>
            <div class="form-group row">
              <label class="col-sm-3 col-form-label" >选择需要发送的附件：</label>
              <div class='input-group  col-sm-3'  >
               <input id='location' class="form-control" onclick="$('#i-file').click();" placeholder="大小不超过20M">
                <label class="input-group-btn">
                  <input type="button" id="i-check" value="浏览文件" class="btn btn-primary" onclick="$('#i-file').click();">
                </label>
                </div>
                <input type="file" name="attachment" id='i-file' onchange="$('#location').val($('#i-file').val());" style="display: none">
            </div>
            </div>
           <div class='form-group row'>
             <button type="submit" class="btn btn-primary center" id="reminder-submit">提交创建</button><br/>
             <small>提醒邮件将会发送到你注册的邮箱中</small>
           </div>
        </form>
       </div>
  </div>   
</div>
<button type="button" class="btn btn-primary hidden" data-toggle="modal" id="tips-modal-btn"data-target="#tipsModal">
</button>
    <div class="modal fade" id="tipsModal" tabindex="-1" role="dialog"  aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-body modal-tips">
          </div>
        </div>
      </div>
    </div>
  </div>
<script src="/js/crsf_ajax.js"></script>
<script src="/js/addReminder.js"></script>
<#include 'common/footer.ftl'>