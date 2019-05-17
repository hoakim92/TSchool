package com.tsystems.lims.dto;

import com.tsystems.lims.models.Cure;
import com.tsystems.lims.models.Diagnosis;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisDto {
    Integer id;
    String name;
    String description;

    public DiagnosisDto(Diagnosis diagnosis){
        this.id = diagnosis.getId();
        this.name = diagnosis.getName();
        this.description = diagnosis.getDescription();
    }

    public static List<DiagnosisDto> getAsDto(List<Diagnosis> diagnoses) {
        return diagnoses.stream().map(DiagnosisDto::new).collect(Collectors.toList());
    }
}
