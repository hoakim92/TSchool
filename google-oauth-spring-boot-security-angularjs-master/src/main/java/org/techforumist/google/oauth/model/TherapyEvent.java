package org.techforumist.google.oauth.model;


import org.techforumist.google.oauth.utils.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "therapyevent")
public class TherapyEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public Cure getCure() {
        return cure;
    }

    @ManyToOne
    Cure cure;
    @ManyToOne
    Patient patient;

    public TherapyEventStatus getStatus() {
        return status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    TherapyEventStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "cause")
    TherapyEventCause cause;
}
