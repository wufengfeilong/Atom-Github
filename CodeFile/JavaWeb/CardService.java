package com.fujisoft.card.service;
/** 
* create by 張風武 
* 2017/12/15 11:40:29 
*/

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fujisoft.card.bean.Card;
import com.fujisoft.card.bean.Interest;
import com.fujisoft.card.repository.CardRepository;
import com.fujisoft.card.repository.InterestRepository;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private InterestRepository interestRepository;
    List<Interest> interestLists;
    List<Card> cardLists;
    int jingjiRate;
    int luyouRate;
    int xuexiRate;
    int meishiRate;
    int tiyuRate;
    public List<Card> getCards(){
        interestLists = new ArrayList<>();
        interestLists = interestRepository.getInterestInfo();
        int jingji=0,luyou=0,xuexi=0,meishi=0,tiyu=0,total=0;
        System.out.println();
        for(Interest i:interestLists){
            if(i.getId()==1){
                jingji = i.getLevel();
            }
            if(i.getId()==2){
                luyou = i.getLevel();
            }
            if(i.getId()==3){
                xuexi = i.getLevel();
            }
            if(i.getId()==6){
                meishi = i.getLevel();
            }
            if(i.getId()==8){
                tiyu = i.getLevel();
            }
        }
        System.out.println("jingji:"+jingji);
        System.out.println("luyou:"+luyou);
        System.out.println("xuexi:"+xuexi);
        System.out.println("meishi:"+meishi);
        System.out.println("tiyu:"+tiyu);
        total = jingji + luyou + xuexi + meishi + tiyu;
        jingjiRate = jingji*100/total;
        luyouRate = luyou*100/total;
        xuexiRate = xuexi*100/total;
        meishiRate = meishi*100/total;
        tiyuRate = 100-jingjiRate-luyouRate-xuexiRate-meishiRate;
        System.out.println("jingji:"+jingjiRate);
        System.out.println("luyou:"+luyouRate);
        System.out.println("xuexi:"+xuexiRate);
        System.out.println("meishi:"+meishiRate);
        System.out.println("tiyu:"+tiyuRate);
        cardLists = new ArrayList<>();
        for(int i=0;i<10;i++){
            getCardList(); 
        }
        return cardLists;
    }
    
    private void getCardList(){
        int randomNumber;  
        randomNumber = (int) (Math.random()*100); 
        if (randomNumber >= 0 && randomNumber <= jingjiRate)  
        {  
            System.out.println("randomNumber:1");
            cardLists.addAll(cardRepository.getCardsByInterestId(1,1));
        }  
        else if (randomNumber >= jingjiRate && randomNumber <= jingjiRate + luyouRate)  
        {  
            System.out.println("randomNumber:2");
            cardLists.addAll(cardRepository.getCardsByInterestId(2,1));  
        }  
        else if (randomNumber >= jingjiRate + luyouRate  
          && randomNumber <= jingjiRate + luyouRate + xuexiRate)  
        {  
            System.out.println("randomNumber:3");
            cardLists.addAll(cardRepository.getCardsByInterestId(3,1));
        }  
        else if (randomNumber >= jingjiRate + luyouRate + xuexiRate  
          && randomNumber <= jingjiRate + luyouRate + xuexiRate + meishiRate)  
        {  
            System.out.println("randomNumber:6");
            cardLists.addAll(cardRepository.getCardsByInterestId(6,1)); 
        }  
        else if (randomNumber >= jingjiRate + luyouRate + xuexiRate + meishiRate  
          && randomNumber <= jingjiRate + luyouRate + xuexiRate + meishiRate + tiyuRate)  
        {  
            System.out.println("randomNumber:8");
            cardLists.addAll(cardRepository.getCardsByInterestId(8,1));
        }  
        else{
            System.out.println("randomNumber:1+");
            cardLists.addAll(cardRepository.getCardsByInterestId(1,1));
        }
    }
}
