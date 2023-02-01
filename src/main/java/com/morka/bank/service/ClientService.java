package com.morka.bank.service;

import com.morka.bank.dto.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    Page<ClientDto> getClients(Pageable pageable);
}
