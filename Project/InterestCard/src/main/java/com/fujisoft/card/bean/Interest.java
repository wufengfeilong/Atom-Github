package com.fujisoft.card.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** 
* create by 張風武 
* 2017/12/14 14:28:27 
*/
@Entity
public class Interest implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;
    
    private String interest_name;
    private Integer level;
    @OneToMany(mappedBy="interest",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();
    @JsonIgnore
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Interest() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public Interest(String interest_name, Integer level) {
        super();
        this.interest_name = interest_name;
        this.level = level;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getInterest_name() {
        return interest_name;
    }
    public void setInterest_name(String interest_name) {
        this.interest_name = interest_name;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Interest [id=" + id + ", interest_name=" + interest_name + ", level=" + level + "]";
    }
    
    
    
    
    
    
}
