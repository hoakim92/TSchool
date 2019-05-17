package com.tsystems.lims.dto;

import com.tsystems.lims.models.Cure;
import com.tsystems.lims.models.Patient;
import com.tsystems.lims.models.Prescription;
import com.tsystems.lims.models.PrescriptionPeriodType;
import com.tsystems.lims.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDto {
    Integer id;
    PatientStructure patient;
    CureStructure cure;
    LocalDate dateOfBegin;
    LocalDate dateOfEnd;
    String doze;
    String periodType;
    final List<DateStructure> events = new ArrayList<>();

    public PrescriptionDto(Prescription prescription) {
        this.id = prescription.getId();
        if (prescription.getPatient() != null)
            this.patient = new PatientStructure(prescription.getPatient());
        if (prescription.getCure() != null)
            this.cure = new CureStructure(prescription.getCure());
        this.dateOfBegin = prescription.getDateOfBegin();
        this.dateOfEnd = prescription.getDateOfEnd();
        this.doze = prescription.getDoze();
        this.periodType = prescription.getPeriodType().name();
        if (this.periodType.equals(PrescriptionPeriodType.WEEKLY.toString())) {
            String dates[] = prescription.getDates().split(DateUtils.delimiter);
            String days[] = prescription.getDaysOfWeek().split(DateUtils.delimiter);
            for (int i = 0; i < days.length; i++) {
                this.events.add(new DateStructure(dates[i], Integer.valueOf(days[i])));
            }
        } else {
            for (String date : prescription.getDates().split(DateUtils.delimiter)) {
                this.events.add(new DateStructure(date));
            }
        }
    }

    @Data
    public class DateStructure {
        String date;
        Integer dayOfWeek;

        public DateStructure(String date) {
            this.date = date;
        }

        public DateStructure(String date, Integer dayOfWeek) {
            this.date = date;
            this.dayOfWeek = dayOfWeek;
        }
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

    public static List<PrescriptionDto> getAsDto(List<Prescription> prescriptions) {
        return prescriptions.stream().map(PrescriptionDto::new).collect(Collectors.toList());
    }
}
