package com.fujisoft.qudao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.fujisoft.qudao.domain.Companylist;
import com.fujisoft.qudao.domain.Customer;
import com.fujisoft.qudao.repository.CustomerRepository;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.internal.Path;

@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerRepository;

	// 查询全部客户信息
	/*public List<Customer> getCustomerList(Customer customer) {
		return customerRepository.findAll();
	}*/
	public Page<Customer> queryForPage(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page,8, null,"id");
		return customerRepository.findAll(pageable);
	} 
	// 根据id查询客户信息
	public List<Customer> findCustomerById(Integer id) {
		return customerRepository.findById(id);
	}

	// 编辑客户信息,保存信息
	public void edit(Customer customer) {

		customerRepository.save(customer);
	}

	// 删除客户
	public void delete(Integer id) {
		customerRepository.delete(id);
	}

	// 保存客户
	public void save(Customer customer) {
		customerRepository.save(customer);
	}

	// 查询公司列表
	public List<Customer> findCompanylist() {
		return customerRepository.findAll();
	}

	

}
