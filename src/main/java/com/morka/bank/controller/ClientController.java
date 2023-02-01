package com.morka.bank.controller;


import com.morka.bank.dto.ClientDto;
import com.morka.bank.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public Page<ClientDto> getClients(Pageable pageable) {
        return clientService.getClients(pageable);
    }
}
