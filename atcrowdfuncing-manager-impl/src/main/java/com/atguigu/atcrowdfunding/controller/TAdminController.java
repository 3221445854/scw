package com.atguigu.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class TAdminController {

	/**☆：
	 *增：接受参数，bean封装
	 *删：接受参数，删除的id
	 *改:接受参数，根据id回显数据，接受参数，bean封装修改数据
	 *查：接受参数，分页查询 封装分页查询条件
	 */
	@Autowired
	TAdminService adminService;
	//删除用户
	@RequestMapping("/admin/doDelete")
	public String doDelete(Integer id,Integer pageNum) {
		
		adminService.deleteTAdmin(id);
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	//修改用户
	@RequestMapping("/admin/doUpdate")
	public String doUpdate(TAdmin admin,Integer pageNum) {
		
		adminService.updateTAdmin(admin);
		
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	//跳转修改用户界面，回显数据
	@RequestMapping("/admin/toUpdate")
	public String toUpdate(Integer id,Model model) {
		TAdmin admin = adminService.getTAdminById(id);
		model.addAttribute("admin", admin);
		return "admin/update";
	}
	
	
	//添加用户
	@RequestMapping("/admin/doAdd")
	public String doAdd(TAdmin admin) {
		
		adminService.saveTAdmin(admin);
		
		//return "redirect:/admin/index";
		return "redirect:/admin/index?pageNum="+Integer.MAX_VALUE;
	}
	
	//跳转添加用户界面
	@RequestMapping("/admin/toAdd")
	public String toAdd() {
		
		return "admin/add";
		
	}
	
	//数据展示，接受分页数据，设置默认分页条件
	@RequestMapping("/admin/index")
	public String index(@RequestParam(value="pageNum",required=false,defaultValue="1") Integer pageNum,
						@RequestParam(value="pageSize",required=false,defaultValue="2") Integer pageSize,
						Model model) {
		

		PageHelper.startPage(pageNum, pageSize);  //线程绑定
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		PageInfo<TAdmin> page = adminService.listAdminPage(paramMap);
		
		model.addAttribute("page", page);
		
		return "admin/index";
	}
	
	
}
