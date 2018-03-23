package com.fujisoft.qudao.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

   @Entity
   public class Good implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
    private Integer id;
	private String  good_number;
	private String  good_name;
	private String  purchase_price;
	private String  inventory_quantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGood_number() {
		return good_number;
	}

	public void setGood_number(String good_number) {
		this.good_number = good_number;
	}
	public String getGood_name() {
		return good_name;
	}
	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}
	public String getPurchase_price() {
		return purchase_price;
	}
	public void setPurchase_price(String purchase_price) {
		this.purchase_price = purchase_price;
	}
	public String getInventory_quantity() {
		return inventory_quantity;
	}
	public void setInventory_quantity(String inventory_quantity) {
		this.inventory_quantity = inventory_quantity;
	}
}
