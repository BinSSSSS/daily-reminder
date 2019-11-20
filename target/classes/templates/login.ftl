<#include 'common/header.ftl'>
<#include 'common/nav.ftl'>

<div class="container text-info text-center">
    <div class="lowin lowin-purple">
		<div class="lowin-brand">
			<img src="/images/kodinger.jpg" alt="logo">
		</div>
		<div class="lowin-wrapper">
			<div class="lowin-box lowin-login">
				<div class="lowin-box-inner">
					<form method="post" action="/login">
						<p>登录到Daily Reminder</p>
						
						<div class="lowin-group">
							<label>用户名 <a href="#" class="login-back-link">登录?</a></label>
							<input type="text" name="username" class="lowin-input" required>
							<small>${error!}
			                ${msg!}</small>
						</div>  
						<div class="lowin-group password-group">
							<label>密码 
	<!-- <a href="#" class="forgot-link">Forgot Password?</a> -->
							</label>
							<input type="password" name="password" autocomplete="current-password" class="lowin-input" required>
						</div>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<button class="lowin-btn login-btn">
							登录
						</button>

						<div class="text-foot">
							还没有账号? <a href="" class="register-link">注册</a>
						</div>
					</form>
				</div>
			</div>

			<div class="lowin-box lowin-register">
				<div class="lowin-box-inner">
					<form method="post" action="javascript:void(0)" class="register">
						<p>开始注册你的账户</p>
						<div class="lowin-group col-sm-1">
							<label>用户名</label>
							<input type="text" name="username" autocomplete="username" class="lowin-input" required>
						</div>
						<div class="lowin-group col-sm-1">
							<label>邮箱</label>
							
							<div class='input-group'>
								<input type="email" autocomplete="email" name="email" class="form-control lowin-input email" required>
								<label class="input-group-btn email-code">
									<button type="button" id="send-vcode" class="btn btn-primary" >发送验证码</button>
								</label>
               				</div>     
						</div>
						<div class="lowin-group col-sm-1">
							<label>邮箱验证码</label>
							<input type="text" name="code" class="lowin-input">
						</div>
						<div class="lowin-group col-sm-1">
							<label>密码</label>
							<input type="password" name="password" autocomplete="current-password" class="lowin-input" required>
						</div>   

						<button class="lowin-btn">
							注册
						</button>  

						<div class="text-foot">
							已经存在账户? <a href="" class="login-link">登录</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	
		<footer class="lowin-footer">
			快来开启你的每日提醒吧
		</footer>
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
	<script src="/js/auth.js"></script>
	<script src="/js/login.js"></script>
<#include 'common/footer.ftl'>
