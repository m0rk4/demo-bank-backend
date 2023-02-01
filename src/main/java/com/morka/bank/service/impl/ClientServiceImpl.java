package com.morka.bank.service.impl;

import com.morka.bank.dto.ClientDto;
import com.morka.bank.model.Client;
import com.morka.bank.repository.ClientRepository;
import com.morka.bank.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Override
    public Page<ClientDto> getClients(Pageable pageable) {
        Page<Client> page = repository.findAll(pageable);
        List<ClientDto> clients = page.get().map(this::mapToDto).toList();
        return new PageImpl<>(clients, pageable, page.getTotalElements());
    }

    private ClientDto mapToDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getFirstname(),
                client.getLastname(),
                client.getPatronymic(),
                client.getDateOfBirth(),
                client.getSex(),
                client.getAddress(),
                client.getCityActual().getName(),
                client.getDisability().getName(),
                client.getCitizenship().getName(),
                client.getMaritalStatus().getName(),
                client.isRetired(),
                client.getEmail()
        );
    }
}
