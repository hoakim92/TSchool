package org.techforumist.google.oauth.model;

import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
}
