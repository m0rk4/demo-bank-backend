package com.morka.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Client {

    @Id
    private Long id;

    private String firstname;

    private String lastname;

    private String patronymic;

    private LocalDate dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    private String address;

    private String phoneNumberHome;

    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private boolean isRetired;

    private Integer monthlyIncome;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_actual_id", nullable = false)
    private City cityActual;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "marital_status_id", nullable = false)
    private MaritalStatus maritalStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "citizenship_id", nullable = false)
    private Citizenship citizenship;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "disability_id", nullable = false)
    private Disability disability;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
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
