package com.morka.bank.service.impl;

import com.morka.bank.dto.ClientDto;
import com.morka.bank.dto.UpdateClientDto;
import com.morka.bank.mapper.Mapper;
import com.morka.bank.model.Client;
import com.morka.bank.model.Passport;
import com.morka.bank.repository.CitizenshipRepository;
import com.morka.bank.repository.CityRepository;
import com.morka.bank.repository.ClientRepository;
import com.morka.bank.repository.DisabilityRepository;
import com.morka.bank.repository.MaritalStatusRepository;
import com.morka.bank.repository.PassportRepository;
import com.morka.bank.service.ClientFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientFacadeImpl implements ClientFacade {

    private final Mapper mapper;

    private final ClientRepository repository;

    private final CityRepository cityRepository;

    private final PassportRepository passportRepository;

    private final DisabilityRepository disabilityRepository;

    private final CitizenshipRepository citizenshipRepository;

    private final MaritalStatusRepository maritalStatusRepository;

    @Override
    public Page<ClientDto> getClients(Pageable pageable) {
        var page = repository.findAll(pageable);
        var clients = page.get().map(client -> mapper.map(client, ClientDto.class)).toList();
        return new PageImpl<>(clients, pageable, page.getTotalElements());
    }

    @Override
    public ClientDto getClient(Long id) {
        return repository.findById(id).map(client -> mapper.map(client, ClientDto.class))
                .orElseThrow(() -> new EntityNotFoundException("No user found."));
    }

    @Transactional
    @Override
    public ClientDto updateClient(Long id, UpdateClientDto clientDto) {
        var exists = repository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException("No user found.");
        }
        var passport = mapper.map(clientDto.getPassport(), Passport.class);
        passport.setId(id);
        var savedPassport = passportRepository.save(passport);
        var client = prepareClient(clientDto, savedPassport);
        client.setId(id);
        return mapper.map(repository.save(client), ClientDto.class);
    }

    @Transactional
    @Override
    public ClientDto addClient(UpdateClientDto updateClientDto) {
        var passport = mapper.map(updateClientDto.getPassport(), Passport.class);
        var savedPassport = passportRepository.save(passport);
        var client = prepareClient(updateClientDto, savedPassport);
        return mapper.map(repository.save(client), ClientDto.class);
    }

    private Client prepareClient(UpdateClientDto updateClientDto, Passport savedPassport) {
        var client = mapper.map(updateClientDto, Client.class);
        client.setPassport(savedPassport);
        client.setCitizenship(citizenshipRepository.getReferenceById(updateClientDto.getCitizenshipId()));
        client.setDisability(disabilityRepository.getReferenceById(updateClientDto.getDisabilityId()));
        client.setCityActual(cityRepository.getReferenceById(updateClientDto.getCityActualId()));
        client.setMaritalStatus(maritalStatusRepository.getReferenceById(updateClientDto.getMaritalStatusId()));
        return client;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.delete(repository.getReferenceById(id));
        passportRepository.delete(passportRepository.getReferenceById(id));
    }
}
