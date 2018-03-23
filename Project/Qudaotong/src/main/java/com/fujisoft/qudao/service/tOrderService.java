package com.fujisoft.qudao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fujisoft.qudao.domain.Good;
import com.fujisoft.qudao.domain.tOrder;
import com.fujisoft.qudao.repository.CustomerRepository;
import com.fujisoft.qudao.repository.tOrderRepository;
@Service
public class tOrderService {
	 @Autowired
     private 	tOrderRepository torderRepository;
	 @Autowired
     private    CustomerRepository customerRepository;
	//查询全部订单信息
	 public Page<tOrder> getOrderList(Integer page, Integer size) {
			Pageable pageable = new PageRequest(page,8, null,"id");
			Page<tOrder> orders = torderRepository.findAll(pageable);
			List<tOrder> orderList = orders.getContent();
//			System.out.println(orderList.get(0).getCustomer().getCustomername());
//			Page<Customer> customers = customerRepository.findAll(pageable);
//            List<Customer> customerList = customers.getContent();
            //customerList.get(0).getOrders().get(0).getOrdernumber()
//			System.out.println(customerList.get(0).getOrders());
			return orders;
		}
	//添加订单信息
	 
	public void save(tOrder torder) {
		
		torderRepository.save(torder);
	}
	 //根据id查询订单信息
   public List<tOrder> findOrderById(Integer id) {
       return torderRepository.findById(id);
   }

 //编辑订单信息,保存信息
   public void edit(tOrder torder) {
   	torderRepository.save(torder);
   }

 //删除订单
   public void delete(Integer id) {
   	torderRepository.delete(id);
   }

	 }
