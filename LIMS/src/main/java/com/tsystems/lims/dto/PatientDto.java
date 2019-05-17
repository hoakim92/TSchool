package com.tsystems.lims.dto;

import com.tsystems.lims.models.Diagnosis;
import com.tsystems.lims.models.Doctor;
import com.tsystems.lims.models.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    Integer id;
    String name;
    String gender;
    LocalDate dateOfBirth;
    String email;
    String insurance;
    DoctorStructure doctor;
    List<DiagnosisStructure> diagnosisList;
    String Status;

    public PatientDto(Patient patient){
        this.id = patient.getId();
        this.name = patient.getName();
        this.gender = patient.getGender() == null ? null : patient.getGender().name();
        this.dateOfBirth = patient.getDateOfBirth();
        this.email = patient.getEmail();
        this.insurance = patient.getInsurance();
        if (patient.getDoctor() != null)
            this.doctor = new DoctorStructure(patient.getDoctor());
        if (patient.getDiagnosisSet() != null)
            this.diagnosisList = patient.getDiagnosisSet().stream().map(DiagnosisStructure::new).collect(Collectors.toList());
        this.Status = patient.getStatus().name();
    }

    @Data
    public class DoctorStructure {
        Integer id;
        String name;
        public DoctorStructure(Doctor doctor){
            this.id = doctor.getId();
            this.name = doctor.getName();
        }
    }

    @Data
    public class DiagnosisStructure {
        Integer id;
        String name;
        public DiagnosisStructure(Diagnosis diagnosis){
            this.id = diagnosis.getId();
            this.name = diagnosis.getName();
        }
    }

    public static List<PatientDto> getAsDto(List<Patient> patients) {
        return patients.stream().map(PatientDto::new).collect(Collectors.toList());
    }
}
