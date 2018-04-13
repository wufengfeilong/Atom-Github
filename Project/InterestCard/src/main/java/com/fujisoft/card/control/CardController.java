package com.fujisoft.card.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fujisoft.card.bean.Card;
import com.fujisoft.card.repository.CardRepository;
import com.fujisoft.card.service.CardService;

/** 
* create by 張風武 
* 2017/12/14 13:50:18 
*/
@RestController
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private CardRepository cardRepository;
    
    @GetMapping("/getCards")
    public List<Card> getCards(){
        return cardService.getCards();
    }
    
    @PostMapping("/changeInterestById")
    public void changeInterestById(
            @RequestParam(value="id") int id
            ,@RequestParam(value="num") int num){
        System.out.println(id);
        System.out.println(num);
        cardRepository.updateInterest(id, num);
    }
    
    
}
