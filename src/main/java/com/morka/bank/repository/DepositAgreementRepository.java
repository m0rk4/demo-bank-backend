package com.morka.bank.repository;

import com.morka.bank.model.DepositAgreement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositAgreementRepository extends JpaRepository<DepositAgreement, Long> {

    boolean existsByNumber(String number);

    @EntityGraph(attributePaths = {"currentAccount", "percentAccount", "depositCurrency.currencyType"})
    @NonNull
    List<DepositAgreement> findAll();
}
