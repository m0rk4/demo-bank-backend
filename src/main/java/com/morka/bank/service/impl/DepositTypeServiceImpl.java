package com.morka.bank.service.impl;

import com.morka.bank.dto.DepositTypeDto;
import com.morka.bank.mapper.Mapper;
import com.morka.bank.repository.DepositTypeRepository;
import com.morka.bank.service.DepositTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepositTypeServiceImpl implements DepositTypeService {

    private final Mapper mapper;

    private final DepositTypeRepository repository;

    @Override
    public Page<DepositTypeDto> getTypes(Pageable pageable) {
        var page = repository.findAll(pageable);
        var clients = page.get().map(client -> mapper.map(client, DepositTypeDto.class)).toList();
        return new PageImpl<>(clients, pageable, page.getTotalElements());
    }
}
