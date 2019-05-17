package com.tsystems.lims.models;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
@Table(name = "doctor")
@NamedQuery(name = "Doctor.getAll", query = "SELECT d from Doctor d")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
}
