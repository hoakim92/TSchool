package com.tsystems.lims.dto;

import com.tsystems.lims.models.Diagnosis;
import com.tsystems.lims.models.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    Integer id;
    String name;

    public DoctorDto(Doctor doctor){
        this.id = doctor.getId();
        this.name = doctor.getName();
    }


    public static List<DoctorDto> getAsDto(List<Doctor> doctors) {
        return doctors.stream().map(DoctorDto::new).collect(Collectors.toList());
    }

}
