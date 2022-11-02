package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;

    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createTransfer(Authentication authentication, @RequestParam Double amount, @RequestParam String originAccountNumber, @RequestParam String destinyAccountNumber,@RequestParam String description) {

        if (amount.equals(0.0) && originAccountNumber.isEmpty() && destinyAccountNumber.isEmpty() && description.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (originAccountNumber.equals(destinyAccountNumber)) {
            return new ResponseEntity<>("You can't make the transfer to the same account", HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(originAccountNumber) == null) {
            return new ResponseEntity<>("The account of origin doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (!clientRepository.findByEmail(authentication.getName()).getAccounts().contains(accountRepository.findByNumber(originAccountNumber))) {
            return new ResponseEntity<>("This account doesn't belong to the client", HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(destinyAccountNumber) == null) {
            return new ResponseEntity<>("The account of destiny doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(originAccountNumber).getBalance() < amount){
            return new ResponseEntity<>("You don't have enough money in the account", HttpStatus.FORBIDDEN);
        }

        Account currentAccount = accountRepository.findByNumber(originAccountNumber);
        Account destinyAccount = accountRepository.findByNumber(destinyAccountNumber);

        transactionRepository.save(new Transaction(TransactionType.DEBIT, description + " For " +destinyAccountNumber, -amount, LocalDateTime.now(),currentAccount));
        transactionRepository.save(new Transaction(TransactionType.CREDIT,description + " From " +originAccountNumber,amount,LocalDateTime.now(),destinyAccount));

        currentAccount.setBalance(currentAccount.getBalance() - amount);
        destinyAccount.setBalance(destinyAccount.getBalance() + amount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
