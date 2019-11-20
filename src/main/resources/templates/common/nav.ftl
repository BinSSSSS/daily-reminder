
<body>
 <nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Daily Reminder</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">提醒<span class="sr-only">(current)</span></a></li>
        <li class="dropdown">  
         
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
          ${(Session["SPRING_SECURITY_CONTEXT"].authentication.principal.username)!}
           <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
          <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
            <li><a href="#">用户资料</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/reminder-list">管理提醒</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/add-reminder">创建新提醒</a></li>
            <li role="separator" class="divider"></li>
            <li>
              <form action="/logout" method="post">
              	 <input type="hidden" name="${_csrf.parameterName!}" value="${_csrf.token!}"/>
                 <button type="submit" class="btn btn-logout">注销</button>
              </form>
            </li>  
          </ul>  
        </li>
        <li><a href="#">关于</a></li>
      </ul>
      <form class="navbar-form navbar-right">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
 </nav>
