package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RequestMapping("/api")
@RestController
public class ClientController {
    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/clients")
    public List<ClientDTO> getAll(){
        return clientService.getListClientDTO();
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClientDTO(@PathVariable Long id){
         return clientService.getClientDTOById(id);
     }

    @RequestMapping("/clients/current")
    public ClientDTO getCurrent(Authentication authentication){
        return clientService.getClientByEmail(authentication.getName());
    }


    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName,
                                           @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.getClientByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client current = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        String newNumberAccount = accountService.generateNumberAccount();

        Account newAccount = new Account(newNumberAccount, LocalDateTime.now(), (double) 0,current);

        clientService.saveClient(current);

        accountService.saveAccount(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
