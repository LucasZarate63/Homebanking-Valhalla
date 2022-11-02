package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){

        Client current = clientRepository.findByEmail(authentication.getName());
        Loan loanSelected = loanRepository.getById(loanApplicationDTO.getLoanId());
        Account destinyAccount = accountRepository.findByNumber(loanApplicationDTO.getNumberAccount());
        Double interestRate = loanApplicationDTO.getAmount()*0.2;

        if (loanApplicationDTO.getLoanId().equals(0L) || loanApplicationDTO.getAmount().equals(0.0) || loanApplicationDTO.getPayment().equals(0) || loanApplicationDTO.getNumberAccount().isEmpty()){
           return new ResponseEntity<>("missing or invalid data", HttpStatus.FORBIDDEN);
        }

        if (!loanRepository.existsById(loanApplicationDTO.getLoanId())){
            return new ResponseEntity<>("the loan selected doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (loanSelected.getMaxAmount() < loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("the amount requested exceeds the maximum allowed", HttpStatus.FORBIDDEN);
        }

        if (!loanSelected.getPayments().contains(loanApplicationDTO.getPayment())){
            return new ResponseEntity<>("requested fees are not available on this loan", HttpStatus.FORBIDDEN);
        }

        if (accountRepository.findByNumber(loanApplicationDTO.getNumberAccount()) == null) {
           return new ResponseEntity<>("invalid account number", HttpStatus.FORBIDDEN);
        }

        if (!current.getAccounts().contains(destinyAccount)){
            return new ResponseEntity<>("the client doesn't have this account", HttpStatus.FORBIDDEN);
        }

        clientLoanRepository.save(new ClientLoan(loanApplicationDTO.getAmount() + interestRate,loanApplicationDTO.getPayment(), current, loanSelected));

        transactionRepository.save(new Transaction(TransactionType.CREDIT, "Loan approved", loanApplicationDTO.getAmount(), LocalDateTime.now(), destinyAccount));

        destinyAccount.setBalance(destinyAccount.getBalance() + loanApplicationDTO.getAmount());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}