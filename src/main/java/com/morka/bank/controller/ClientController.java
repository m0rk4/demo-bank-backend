package com.morka.bank.controller;


import com.morka.bank.dto.ClientDto;
import com.morka.bank.dto.UpdateClientDto;
import com.morka.bank.service.ClientFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    public ClientDto addClient(@Valid @RequestBody UpdateClientDto updateClientDto) {
        return clientFacade.addClient(updateClientDto);
    }

    @PutMapping("/{id}")
    public ClientDto updateClient(@PathVariable Long id, @Valid @RequestBody UpdateClientDto clientDto) {
        return clientFacade.updateClient(id, clientDto);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientFacade.delete(id);
    }
}
