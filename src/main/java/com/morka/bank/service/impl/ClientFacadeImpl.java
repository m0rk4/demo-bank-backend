package com.morka.bank.service.impl;

import com.morka.bank.dto.ClientDto;
import com.morka.bank.dto.UpdateClientDto;
import com.morka.bank.dto.UpdatePassportDto;
import com.morka.bank.exception.EmailExistsException;
import com.morka.bank.exception.PassportExistsException;
import com.morka.bank.mapper.Mapper;
import com.morka.bank.model.Client;
import com.morka.bank.model.Passport;
import com.morka.bank.repository.*;
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
        validateClient(id);
        validateEmail(clientDto.getEmail(), id);
        validatePassport(clientDto.getPassport(), id);
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
        validateEmail(updateClientDto.getEmail());
        validatePassport(updateClientDto.getPassport());
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

    private void validateClient(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No user found by id.");
        }
    }

    private void validatePassport(UpdatePassportDto dto, Long clientId) {
        boolean exists = passportRepository.existsByPassportIdAndIdIsNot(dto.getPassportId(), clientId)
                || passportRepository.existsByPassportNumberAndIdIsNot(dto.getPassportNumber(), clientId);
        if (exists) {
            throw new PassportExistsException("Passport with such ID or number already exists.");
        }
    }

    private void validateEmail(String email, Long clientId) {
        if (repository.existsByEmailAndIdIsNot(email, clientId)) {
            throw new EmailExistsException("Client with such Email already exists.");
        }
    }

    private void validateEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new EmailExistsException("Client with such Email already exists.");
        }
    }

    private void validatePassport(UpdatePassportDto dto) {
        if (passportRepository.existsByPassportIdOrPassportNumber(dto.getPassportId(), dto.getPassportNumber())) {
            throw new PassportExistsException("Passport with such ID or number already exists.");
        }
    }
}
