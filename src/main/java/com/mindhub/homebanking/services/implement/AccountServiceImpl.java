package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.Utils.getRandomNumberInt;
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public String generateNumberAccount() {
        String newNumberAccount;

        do {newNumberAccount = "VIN-" + getRandomNumberInt(0,99999999);}
        while(accountRepository.findAll().stream().map(Account::getNumber).collect(Collectors.toSet()).contains(newNumberAccount));

        return newNumberAccount;
    }

}
