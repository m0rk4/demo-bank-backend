package com.morka.bank.facade.impl;

import com.morka.bank.dto.ClientDto;
import com.morka.bank.dto.UpdateClientDto;
import com.morka.bank.facade.ClientFrontFacade;
import com.morka.bank.mapper.Mapper;
import com.morka.bank.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientFrontFacadeImpl implements ClientFrontFacade {

    private final Mapper mapper;

    private final ClientService clientService;

    @Override
    public Page<ClientDto> getClients(Pageable pageable) {
        var page = clientService.getClients(pageable);
        return page.map(client -> mapper.map(client, ClientDto.class));
    }

    @Transactional
    @Override
    public ClientDto updateClient(Long id, UpdateClientDto clientDto) {
        return mapper.map(clientService.updateClient(id, clientDto), ClientDto.class);
    }

    @Transactional
    @Override
    public ClientDto addClient(UpdateClientDto dto) {
        return mapper.map(clientService.addClient(dto), ClientDto.class);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        clientService.delete(id);
    }
}
