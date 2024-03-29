package com.atguigu.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.exception.LoginException;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.atguigu.atcrowdfunding.util.AppDateUtils;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.github.pagehelper.PageInfo;

@Service
public class TAdminServiceImpl implements TAdminService{

	Logger log = LoggerFactory.getLogger(TAdminServiceImpl.class);
	
	@Autowired
	TAdminMapper adminMapper;
	@Override
	public TAdmin getTAdminByLogin(Map<String, Object> paramMap) {
		//1.密码加密
		//2.查询用户
		String loginacct = (String) paramMap.get("loginacct");
		String userpswd = (String) paramMap.get("userpswd");
		//3.判断用户是否为null
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);
		
		List<TAdmin> list = adminMapper.selectByExample(example);
		if(list==null||list.size()==0) {
			throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
		}
		TAdmin admin = list.get(0);
		//4.判断密码是否一致
		if(!admin.getUserpswd().equals(MD5Util.digest(userpswd))) {
			throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
		}
		//5.返回结果
		return admin;
	}
	@Override
	public PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap) {

		TAdminExample examle = new TAdminExample();
		
		
		//examle.setOrderByClause("createtime desc");
		
		List<TAdmin> list = adminMapper.selectByExample(examle);
		
		PageInfo<TAdmin> page = new PageInfo<TAdmin>(list,5);
		log.debug("userpswd={}",page);
		return page;
	}
	@Override
	public void saveTAdmin(TAdmin admin) {

		admin.setUserpswd(MD5Util.digest(Const.DEFAULT_USERPSWD));
		
		admin.setCreatetime(AppDateUtils.getFormatTime());
		adminMapper.insertSelective(admin);//动态sql,有选择性保存。
	}
	@Override
	public TAdmin getTAdminById(Integer id) {
		
		return adminMapper.selectByPrimaryKey(id);
	}
	@Override
	public void updateTAdmin(TAdmin admin) {

		adminMapper.updateByPrimaryKeySelective(admin);
	}
	@Override
	public void deleteTAdmin(Integer id) {

		adminMapper.deleteByPrimaryKey(id);
	}

}
