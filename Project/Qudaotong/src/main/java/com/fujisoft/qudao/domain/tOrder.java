package com.fujisoft.qudao.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class tOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	private String ordernumber;
	private String company;
	private String ordername;
	private String ordersettime;
	private int orderquantity;
	// 订单表与货物表的连接：多对一，单向：不产生中间表
	@ManyToOne(optional = false)    // optional=false 时join 查询关系为inner
									// join,optional=true 时join 查询关系为left join。

	// @JoinColumn来指定生成外键的名字，外键在多的一方表中产生,即tOrder表
	// name列位于源实体的表中,referencedColumnName被引用的键列位于拥有实体
	// 有两个实体映射到同一张表或者有两个属性,映射到数据库的同一列时，用insertable=false, updatable=false
	@JoinColumn(name = "ordername", referencedColumnName = "good_name", insertable = false, updatable = false)
	private Good good;

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	// 订单表与客户表的连接：多对一
	@ManyToOne(optional = false)
	@JoinColumn(name = "company", referencedColumnName = "company", insertable = false, updatable = false)
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOrdername() {
		return ordername;
	}

	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}

	public String getOrdersettime() {
		return ordersettime;
	}

	public void setOrdersettime(String ordersettime) {
		this.ordersettime = ordersettime;
	}

	public int getOrderquantity() {
		return orderquantity;
	}

	public void setOrderquantity(int orderquantity) {
		this.orderquantity = orderquantity;
	}

}
