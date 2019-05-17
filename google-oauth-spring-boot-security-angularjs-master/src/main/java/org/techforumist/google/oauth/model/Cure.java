package org.techforumist.google.oauth.model;

import javax.persistence.*;

@Entity
@Table(name = "cure")
public class Cure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    public String getName() {
        return name;
    }

    public CureType getType() {
        return type;
    }

    String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    CureType type;
}