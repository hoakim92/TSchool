package com.tsystems.lims.dto;

import com.tsystems.lims.models.Cure;
import com.tsystems.lims.models.Patient;
import com.tsystems.lims.models.TherapyEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TherapyEventDto {
    Integer id;
    LocalDateTime date;
    CureStructure cure;
    PatientStructure patient;
    Integer prescription;
    String status;
    String cause;


    public TherapyEventDto(TherapyEvent therapyEvent) {
        this.id = therapyEvent.getId();
        this.date = therapyEvent.getDate();
        this.cure = new CureStructure(therapyEvent.getCure());
        this.patient = new PatientStructure(therapyEvent.getPatient());
        this.prescription = therapyEvent.getPrescription().getId();
        this.status = therapyEvent.getStatus().name();
        this.cause = therapyEvent.getCause() == null ? null : therapyEvent.getCause().toString();
    }

    @Data
    public class PatientStructure {
        Integer id;
        String name;

        public PatientStructure(Patient patient) {
            this.id = patient.getId();
            this.name = patient.getName();
        }
    }

    @Data
    public class CureStructure {
        Integer id;
        String name;

        public CureStructure(Cure cure) {
            this.id = cure.getId();
            this.name = cure.getName();
        }
    }

    public static List<TherapyEventDto> getAsDto(List<TherapyEvent> events) {
        return events.stream().map(TherapyEventDto::new).collect(Collectors.toList());
    }
}
