package com.morka.bank.controller;


import com.morka.bank.dto.UpdateClientDto;
import com.morka.bank.dto.ClientDto;
import com.morka.bank.service.ClientFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientFacade clientFacade;

    @GetMapping
    public Page<ClientDto> getClients(Pageable pageable) {
        return clientFacade.getClients(pageable);
    }

    @GetMapping("/{id}")
    public ClientDto getClient(@PathVariable Long id) {
        return clientFacade.getClient(id);
    }

    @PostMapping
    public ClientDto addClient(@RequestBody UpdateClientDto updateClientDto) {
        return clientFacade.addClient(updateClientDto);
    }

    @PutMapping("/{id}")
    public ClientDto updateClient(@PathVariable Long id, @RequestBody UpdateClientDto clientDto) {
        return clientFacade.updateClient(id, clientDto);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientFacade.delete(id);
    }
}
