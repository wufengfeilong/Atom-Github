package com.fujisoft.qudao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fujisoft.qudao.domain.tOrder;

public interface tOrderRepository extends JpaRepository<tOrder, Integer> {
	public List<tOrder> findById(Integer id);

	@Query("from tOrder u where u.id=:id")
	public List<tOrder> tOrder(@Param("id") Integer id);
	  /* @Query("select u from tOrder u where u.id IN(select customername from customer where customername=:customername)")
     	   public Page<Params> findByParams_IDAndParams_Customername(Pageable pageable);
	   @Query("select u from tOrder u where u.id IN(select id from customer where id=:id)")
	   public Page<tOrder> findOrder(Pageable pageable);
	   @Query("select fv from FieldValue fv where field_id IN (select id from Field where table_id=:tableId) ORDER BY field_id") 
	   List<tOrder> getTableValue(@Param("tableId") int tableId); */
	 
//	   public Page<tOrder> findAll(Pageable pageable);
//	  public tOrder findMany(Pageable pageable);
//	  public Page<Params> findAll(Specification<tOrder> specification, Object pageable);

}
