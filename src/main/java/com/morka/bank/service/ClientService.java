package com.morka.bank.service;

import com.morka.bank.dto.UpdateClientDto;
import com.morka.bank.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    Page<Client> getClients(Pageable pageable);

    void delete(Long id);

    Client getClient(Long id);

    Client updateClient(Long id, UpdateClientDto clientDto);

    Client addClient(UpdateClientDto updateClientDto);
}
