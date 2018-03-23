package com.fujisoft.qudao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fujisoft.qudao.domain.Customer;
import com.fujisoft.qudao.domain.Good;

public interface GoodRepository extends JpaRepository<Good, Integer> {
	public List<Good> findById(Integer id);

	@Query("from Good u where u.id=:id") // id数据库的数据+id传入的参数
	public List<Good> Good(@Param("id") Integer id);
}
