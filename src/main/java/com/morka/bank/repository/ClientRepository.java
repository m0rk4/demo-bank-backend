package com.morka.bank.repository;

import com.morka.bank.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @EntityGraph(attributePaths = {
            "cityActual",
            "disability",
            "maritalStatus",
            "citizenship",
            "passport"
    })
    @NonNull
    Page<Client> findAll(@NonNull Pageable pageable);
}
