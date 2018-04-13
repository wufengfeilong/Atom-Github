package com.fujisoft.card.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fujisoft.card.bean.Interest;

/** 
* create by 張風武 
* 2017/12/21 9:58:01 
*/
public interface InterestRepository extends JpaRepository<Interest, Integer>{
    @Query(value="select * from Interest",nativeQuery=true)
    public List<Interest> getInterestInfo();
}
