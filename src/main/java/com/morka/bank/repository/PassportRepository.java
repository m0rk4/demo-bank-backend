package com.morka.bank.repository;

import com.morka.bank.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {

    boolean existsByPassportId(String passportId);

    boolean existsByPassportNumber(String passportNumber);

    boolean existsByPassportIdAndIdIsNot(String passportId, Long clientId);

    boolean existsByPassportNumberAndIdIsNot(String passportNumber, Long clientId);
}
