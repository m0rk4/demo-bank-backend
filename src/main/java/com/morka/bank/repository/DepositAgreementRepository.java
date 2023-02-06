package com.morka.bank.repository;

import com.morka.bank.model.DepositAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositAgreementRepository extends JpaRepository<DepositAgreement, Long> {

    boolean existsByNumber(String number);
}
