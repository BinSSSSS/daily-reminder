package cn.tblack.reminder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.reminder.entity.User;
import cn.tblack.reminder.result.WebResult;
import cn.tblack.reminder.service.UserService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/bs")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/user-list/search")
	public Page<User> findAllUser(@RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize,
			@RequestParam(name = "pageNo",defaultValue = "0")Integer pageNo,
			@RequestParam(name = "searchText",defaultValue = "")String searchText){
		
		return userService.findAllByUsername(searchText,PageRequest.of(pageNo, pageSize));
	}
	
	
	@RequestMapping("/user/delete-user")
	public WebResult deleteUser(Integer id) {
		
		WebResult result = new WebResult();
		
		try {
			userService.deleteById(id);
			result.setMsg("删除成功");
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			result.setMsg("删除失败");
			result.setSuccess(false);
		}
		
		return result;
	}
	
}
