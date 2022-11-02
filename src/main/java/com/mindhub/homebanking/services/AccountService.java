package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;

public interface AccountService {

    void saveAccount(Account account);

    String generateNumberAccount();
}
