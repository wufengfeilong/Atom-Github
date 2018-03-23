package com.fujisoft.qudao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fujisoft.qudao.domain.Customer;
import com.fujisoft.qudao.domain.Good;
import com.fujisoft.qudao.repository.GoodRepository;
@Service
public class GoodService {
	 @Autowired
      private 	GoodRepository goodRepository;
	//查询全部货物信息
	public Page<Good> getGoodList(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page,8, null,"id");
		return goodRepository.findAll(pageable);
	}
	
	//添加客户信息
	public void save(Good good) {
		goodRepository.save(good);
	}
	 //根据id查询客户信息
    public List<Good> findGoodById(Integer id) {
        return goodRepository.findById(id);
    }

  //编辑客户信息,保存信息
    public void edit(Good good) {
    	
    	goodRepository.save(good);
    }

  //删除客户
    public void delete(Integer id) {
    	goodRepository.delete(id);
    }
    //查询货物列表
	public List<Good> findGoodlist() {
		return goodRepository.findAll();
	}
}
