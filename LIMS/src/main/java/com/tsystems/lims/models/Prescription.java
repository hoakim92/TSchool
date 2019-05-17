package com.tsystems.lims.models;

import com.tsystems.lims.dao.common.LocalDatePersistenceConverter;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;
    @ManyToOne
    Patient patient;
    @OneToOne
    @JoinColumn(name = "id")
    Cure cure;
    @Convert(converter = LocalDatePersistenceConverter.class)
    LocalDate dateOfBegin;
    @Convert(converter = LocalDatePersistenceConverter.class)
    LocalDate dateOfEnd;
    String doze;
    @Enumerated(EnumType.STRING)
    PrescriptionPeriodType periodType;
    String dates;
    String daysOfWeek;

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", patient=" + patient.getId() +
                ", cure=" + cure +
                ", dateOfBegin=" + dateOfBegin +
                ", dateOfEnd=" + dateOfEnd +
                ", doze='" + doze + '\'' +
                ", periodType=" + periodType +
                ", dates='" + dates + '\'' +
                '}';
    }
}
