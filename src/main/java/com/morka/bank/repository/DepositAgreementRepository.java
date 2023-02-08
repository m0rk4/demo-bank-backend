package com.morka.bank.repository;

import com.morka.bank.model.DepositAgreement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositAgreementRepository extends JpaRepository<DepositAgreement, Long> {

    boolean existsByNumber(String number);

    @EntityGraph(attributePaths = {"currentAccount", "percentAccount", "depositCurrency.currencyType"})
    @NonNull
    List<DepositAgreement> findAllByExpiredTsIsNull();

    @EntityGraph(attributePaths = {"currentAccount", "depositCurrency.currencyType"})
    @NonNull
    Optional<DepositAgreement> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"client", "depositCurrency.depositType", "depositCurrency.currencyType"})
    Page<DepositAgreement> findAllByExpiredTsIsNull(Pageable pageable);
}
