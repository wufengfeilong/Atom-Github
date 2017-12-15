package com.fujisoft.card.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fujisoft.card.bean.Card;

/** 
* create by 張風武 
* 2017/12/14 15:54:32 
*/
public interface CardRepository extends JpaRepository<Card, Integer> {
    
    @Query(value = "select * from Card c where c.is_look = 0 and c.interest_id = ?1 limit ?2",nativeQuery=true)
    public List<Card> getCardsByInterestId(int interest_id,int limit_count);
}
