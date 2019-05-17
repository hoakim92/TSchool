package com.tsystems.lims.service;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dto.*;
import com.tsystems.lims.models.PatientGender;
import com.tsystems.lims.models.PrescriptionPeriodType;
import com.tsystems.lims.models.TherapyEventCause;
import com.tsystems.lims.models.TherapyEventStatus;
import com.tsystems.lims.service.interfaces.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfigTest.class})
@WebAppConfiguration
public class TherapyEventService {
    @Autowired
    ITherapyEventService service;

    @Autowired
    ICureService cureService;

    @Autowired
    IDiagnosisService diagnosisService;

    @Autowired
    IDoctorService doctorService;

    @Autowired
    IPatientService patientService;

    @Autowired
    IPrescriptionService prescriptionService;

    CureDto cure;
    DiagnosisDto diagnosis;
    DoctorDto doctor;
    PatientDto patient;
    PrescriptionDto prescription;
    TherapyEventDto entity;

    @Before
    @Transactional
    public void beforeTest() {
        service.getTherapyEvents().forEach(e -> service.deleteTherapyevent(e.getId()));
        cure = cureService.saveOrUpdateCure(null, "Cure1", "DRUG");
        diagnosis = diagnosisService.saveOrUpdateDiagnosis(null, "Diagnosis1", "");
        doctor = doctorService.saveOrUpdateDoctor(null, "Doctor1");
        patient = patientService.saveOrUpdatePatient(null, doctor.getId(), new Integer[]{diagnosis.getId()}, "Patient1", PatientGender.MALE, LocalDate.now(), "Insurance1",null, "TREAT");
        LocalDate date = LocalDate.now();
        prescription = prescriptionService.saveOrUpdatePrescription(null, patient.getId(), cure.getId(), date, date, "doze1", PrescriptionPeriodType.ONCE, new String[]{date.toString() + "T06:00"}, null);
        entity = service.getTherapyEvents().get(0);
    }


    @Test(expected = UnsupportedOperationException.class)
    @Transactional
    public void testChangeStatus() {
        service.updateStatusAndCause(entity.getId(), TherapyEventStatus.EXECUTED, null);
        service.updateStatusAndCause(entity.getId(), TherapyEventStatus.CANCELED, TherapyEventCause.NO_CURE);
        service.updateStatusAndCause(entity.getId(), TherapyEventStatus.EXECUTED, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    @Transactional
    public void testChangeStatus2() {
        service.updateStatusAndCause(entity.getId(), TherapyEventStatus.CANCELED, TherapyEventCause.NO_CURE);
        service.updateStatusAndCause(entity.getId(), TherapyEventStatus.EXECUTED, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    @Transactional
    public void testChangeStatus3() {
        service.updateStatusAndCause(entity.getId(), TherapyEventStatus.CANCELED, null);
    }


    @Test
    @Transactional
    public void testDeleteAll() {
        service.getTherapyEvents().forEach(e -> service.deleteTherapyevent(e.getId()));
        assertEquals(0, service.getTherapyEvents().size());
    }

}
