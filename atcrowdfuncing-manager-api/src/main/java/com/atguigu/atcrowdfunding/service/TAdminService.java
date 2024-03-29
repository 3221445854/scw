package com.atguigu.atcrowdfunding.service;

import java.util.Map;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.github.pagehelper.PageInfo;

public interface TAdminService {

	TAdmin getTAdminByLogin(Map<String, Object> paramMap);

	PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap);

	void saveTAdmin(TAdmin admin);

	TAdmin getTAdminById(Integer id);

	void updateTAdmin(TAdmin admin);

	void deleteTAdmin(Integer id);

}
