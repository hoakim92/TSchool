package com.tsystems.lims.models;

import com.tsystems.lims.dao.common.LocalDatePersistenceConverter;
import com.tsystems.lims.dao.common.LocalDateTimePersistenceConverter;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "therapyevent")
public class TherapyEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    LocalDateTime date;
    @ManyToOne
    Patient patient;
    @ManyToOne
    Prescription prescription;
    @ManyToOne
    Cure cure;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    TherapyEventStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "cause")
    TherapyEventCause cause;
}
