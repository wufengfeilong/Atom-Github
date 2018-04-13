package com.fujisoft.card.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.fujisoft.card.bean.Card;

/** 
* create by 張風武 
* 2017/12/14 15:54:32 
*/
public interface CardRepository extends JpaRepository<Card, Integer> {
    
    @Query(value = "select * from Card c where c.is_look = 0 and c.interest_id = ?1 order by rand() limit ?2 ",nativeQuery=true)
    public List<Card> getCardsByInterestId(int interest_id,int limit_count);
    @Modifying
    @Transactional
    @Query(value="update Interest i set i.level = i.level+?2 where i.id = ?1",nativeQuery=true)
    public int updateInterest(int id,int num);
    
    
}
