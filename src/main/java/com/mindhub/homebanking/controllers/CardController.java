package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.Utils.*;

@RequestMapping("/api")
@RestController
public class CardController {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/clients/current/cards")
    public List<CardDTO> getCurrentCards(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(path="/clients/current/cards",method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType type, @RequestParam CardColor color, Authentication authentication){
        Client current = clientRepository.findByEmail(authentication.getName());
        Set<Card> cardsDebitCurrent = current.getCards().stream().filter(card -> card.getType().equals(CardType.DEBIT)).collect(Collectors.toSet());
        Set<Card> cardsCreditCurrent = current.getCards().stream().filter(card -> card.getType().equals(CardType.CREDIT)).collect(Collectors.toSet());

        if(type.equals(CardType.CREDIT) && cardsCreditCurrent.size() >= 3){
            return new ResponseEntity<>("You can't create more than 3 cards", HttpStatus.FORBIDDEN);
        }

        if(type.equals(CardType.DEBIT) && cardsDebitCurrent.size() >= 3){
            return new ResponseEntity<>("You can't create more than 3 cards", HttpStatus.FORBIDDEN);
        }

        cardRepository.save(new Card( current,type, color, Long.valueOf(getRandomNumberInt(1000,9999)+""+getRandomNumberInt(1000,9999)+""+getRandomNumberInt(1000,9999)+""+getRandomNumberInt(1000,9999)), (short) getRandomNumberInt(100,999), LocalDate.now().plusYears(5), LocalDate.now()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
