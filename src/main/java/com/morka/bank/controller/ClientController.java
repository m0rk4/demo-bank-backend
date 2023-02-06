package com.morka.bank.controller;


import com.morka.bank.dto.ClientDto;
import com.morka.bank.dto.UpdateClientDto;
import com.morka.bank.facade.ClientFrontFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientFrontFacade clientFrontFacade;

    @GetMapping
    public Page<ClientDto> getClients(Pageable pageable) {
        return clientFrontFacade.getClients(pageable);
    }

    @PostMapping
    public ClientDto addClient(@Valid @RequestBody UpdateClientDto updateClientDto) {
        return clientFrontFacade.addClient(updateClientDto);
    }

    @PutMapping("/{id}")
    public ClientDto updateClient(@PathVariable Long id, @Valid @RequestBody UpdateClientDto clientDto) {
        return clientFrontFacade.updateClient(id, clientDto);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientFrontFacade.delete(id);
    }
}
