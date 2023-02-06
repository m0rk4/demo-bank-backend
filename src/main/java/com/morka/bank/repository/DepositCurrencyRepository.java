package com.morka.bank.repository;

import com.morka.bank.model.DepositCurrency;
import com.morka.bank.model.DepositType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositCurrencyRepository extends JpaRepository<DepositCurrency, Long> {

    @EntityGraph(attributePaths = {"currencyType"})
    List<DepositCurrency> findByDepositType(DepositType depositType);
}
