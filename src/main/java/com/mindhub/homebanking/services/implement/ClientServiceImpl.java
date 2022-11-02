package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getListClientDTO(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientDTOById(Long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public ClientDTO getClientByEmail(String email) {
        return new ClientDTO( clientRepository.findByEmail(email));
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }


}
