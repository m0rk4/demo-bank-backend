package com.morka.bank.service;

import com.morka.bank.dto.ClientDto;
import com.morka.bank.dto.UpdateClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientFacade {

    Page<ClientDto> getClients(Pageable pageable);

    void delete(Long id);

    ClientDto getClient(Long id);

    ClientDto updateClient(Long id, UpdateClientDto clientDto);

    ClientDto addClient(UpdateClientDto updateClientDto);
}
