package com.tsystems.lims.service.interfaces;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dto.PatientDto;
import com.tsystems.lims.models.Patient;
import com.tsystems.lims.models.PatientGender;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IPatientService {
    public PatientDto saveOrUpdatePatient(Integer id, Integer doctorId, Integer[] diagnosisIds, String name, PatientGender gender, LocalDate dateOfBirth, String insurance, String email, String status);

    public List<PatientDto> getPatients(Map<String, String> parameters, Integer offset, Integer count);

    public List<PatientDto> getPatients();

    public PatientDto getPatient(Integer id);

    public void deletePatient(Integer id);
}
