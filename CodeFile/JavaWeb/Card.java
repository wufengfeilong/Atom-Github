package com.fujisoft.card.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** 
* create by 張風武 
* 2017/12/14 14:28:51 
*/
@Entity
public class Card implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;
    
    private Integer interest_id;
    private String img_name;
    private String content;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isLook;
    public boolean getIsLook() {
        return isLook;
    }
    public void setIsLook(boolean isLook) {
        this.isLook = isLook;
    }
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name="interest_id", referencedColumnName="id", insertable=false, updatable=false)
    private Interest interest;
    
    public Interest getInterest() {
        return interest;
    }
    public void setInterest(Interest interest) {
        this.interest = interest;
    }
    public Card() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Card(Integer interest_id, String img_name, String content) {
        super();
        this.interest_id = interest_id;
        this.img_name = img_name;
        this.content = content;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getInterest_id() {
        return interest_id;
    }
    public void setInterest_id(Integer interest_id) {
        this.interest_id = interest_id;
    }
    public String getImg_name() {
        return img_name;
    }
    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public String toString() {
        return "Card [id=" + id + ", interest_id=" + interest_id + ", img_name=" + img_name + ", content=" + content
                + "]";
    }
    
    
    
}
