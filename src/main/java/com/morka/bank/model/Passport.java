package com.morka.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String passportId;

    @Column(nullable = false)
    private String passportSeries;

    @Column(nullable = false, unique = true)
    private String passportNumber;

    @Column(nullable = false)
    private String passportIssuer;

    @Column(nullable = false)
    private LocalDate passportIssuedDate;

    @Column(nullable = false)
    private String passportAddress;

    @Column(nullable = false)
    private String placeOfBirth;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Passport that = (Passport) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + "id = " + id + ")";
    }
}
