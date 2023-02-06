package com.morka.bank.service;

import com.morka.bank.dto.DepositTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepositTypeService {

    Page<DepositTypeDto> getTypes(Pageable pageable);
}
