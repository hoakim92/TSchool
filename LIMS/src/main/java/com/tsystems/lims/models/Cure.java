package com.tsystems.lims.models;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cure")
public class Cure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;
    String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    CureType type;
}
