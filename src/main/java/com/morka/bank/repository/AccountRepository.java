package com.morka.bank.repository;

import com.morka.bank.model.Account;
import com.morka.bank.model.AccountCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = {"currencyType"})
    Page<Account> findAllByNameContainingOrCodeIn(String name, List<AccountCode> codes, Pageable pageable);

    @EntityGraph(attributePaths = {"currencyType"})
    List<Account> findByCode(AccountCode code);
}
