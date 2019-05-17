package com.tsystems.lims.models;

import com.tsystems.lims.dao.common.LocalDatePersistenceConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;
    @NonNull
    @Column(name = "name")
    String name;
    @Convert(converter = LocalDatePersistenceConverter.class)
    LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    PatientGender gender;
    @ManyToOne
    Doctor doctor;
    @ManyToMany()
    @JoinColumn(name = "id")
    Set<Diagnosis> diagnosisSet;
    @NonNull
    @Column(name = "insurance")
    String insurance;
    @Column(unique = true)
    String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    PatientStatus status;


    public Patient(String name, String insurance) {
        this.setName(name);
        this.setInsurance(insurance);
    }

    public Patient(String name, String insurance, PatientStatus status) {
        this.setName(name);
        this.setInsurance(insurance);
        this.setStatus(status);
    }

}
