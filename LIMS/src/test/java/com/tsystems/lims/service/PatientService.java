package com.tsystems.lims.service;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dto.DiagnosisDto;
import com.tsystems.lims.dto.DoctorDto;
import com.tsystems.lims.dto.PatientDto;
import com.tsystems.lims.models.*;
import com.tsystems.lims.service.interfaces.IDiagnosisService;
import com.tsystems.lims.service.interfaces.IDoctorService;
import com.tsystems.lims.service.interfaces.IPatientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfigTest.class})
@WebAppConfiguration
public class PatientService {

    @Autowired
    IPatientService service;

    @Autowired
    IDoctorService doctorService;

    @Autowired
    IDiagnosisService diagnosisService;

    PatientDto patient;
    DoctorDto doctor1;
    DoctorDto doctor2;
    DiagnosisDto diagnosis1;
    DiagnosisDto diagnosis2;

    @Before
    @Transactional
    public void beforeTest() {
        doctor1 = doctorService.saveOrUpdateDoctor(null,"Doctor1");
        doctor2 = doctorService.saveOrUpdateDoctor(null,"Doctor2");

        diagnosis1 = diagnosisService.saveOrUpdateDiagnosis(null,"Diagnosis1","Description1");
        diagnosis2 = diagnosisService.saveOrUpdateDiagnosis(null,"Diagnosis2","Description2");

        service.getPatients().forEach(e -> service.deletePatient(e.getId()));
        patient = service.saveOrUpdatePatient(null,doctor1.getId(), new Integer[]{diagnosis1.getId()},"Patient1", PatientGender.MALE, LocalDate.now(),"Insurance1",null,"TREAT");
    }


    @Test
    @Transactional
    public void testFindAllAfterCreate() {
        List<PatientDto> patients = service.getPatients();
        assertEquals(1, patients.size());
        assertTrue(patients.contains(patient));
    }


    @Test
    @Transactional
    public void testUpdate() {
        patient = service.saveOrUpdatePatient(null,doctor2.getId(), new Integer[]{diagnosis2.getId()},"Patient2",PatientGender.MALE, LocalDate.now(),"Insurance2",null,"DISCHARGED");
        assertEquals("Patient2", patient.getName());
        assertEquals("Doctor2", patient.getDoctor().getName());
        assertEquals("Insurance2", patient.getInsurance());
        assertEquals(PatientStatus.DISCHARGED.toString(), patient.getStatus());
        assertEquals(1, patient.getDiagnosisList().size());
        assertEquals("Diagnosis2", patient.getDiagnosisList().get(0).getName());

    }

    @Test
    @Transactional
    public void testDeleteAll() {
        service.getPatients().forEach(entity -> service.deletePatient(entity.getId()));
        assertEquals(0, service.getPatients().size());
    }
}
