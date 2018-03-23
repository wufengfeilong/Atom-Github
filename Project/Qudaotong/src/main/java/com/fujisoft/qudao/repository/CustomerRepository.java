package com.fujisoft.qudao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fujisoft.qudao.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public List<Customer> findById(Integer id);

	@Query("from Customer u where u.id=:id")
	public List<Customer> Customer(@Param("id") Integer id);

}
