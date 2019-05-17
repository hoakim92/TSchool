package com.tsystems.lims.dao;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dao.interfaces.*;
import com.tsystems.lims.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfigTest.class})
@WebAppConfiguration
public class TherapyEventDao {
    @Autowired
    ITherapyEventDao dao;

    @Autowired
    ICureDao cureDao;

    @Autowired
    IDiagnosisDao diagnosisDao;

    @Autowired
    IDoctorDao doctorDao;

    @Autowired
    IPatientDao patientDao;

    @Autowired
    IPrescriptionDao prescriptionDao;

    Cure cure = Cure.builder().name("cure").build();
    Cure cure2 = Cure.builder().name("cure2").build();
    Diagnosis diagnosis = Diagnosis.builder().name("diagnosis").build();
    Diagnosis diagnosis2 = Diagnosis.builder().name("diagnosis2").build();
    Doctor doctor = Doctor.builder().name("doctor").build();
    Doctor doctor2 = Doctor.builder().name("doctor2").build();
    Patient patient = Patient.builder().name("Patient1").doctor(doctor).insurance("insurance1").status(PatientStatus.TREAT).diagnosisSet(new HashSet<Diagnosis>() {{
        add(diagnosis);
    }}).build();
    Patient patient2 = Patient.builder().name("Patient2").doctor(doctor).insurance("insurance2").status(PatientStatus.TREAT).diagnosisSet(new HashSet<Diagnosis>() {{
        add(diagnosis2);
    }}).build();
    Prescription prescription = Prescription.builder().cure(cure).patient(patient).dateOfBegin(LocalDate.now()).dateOfEnd(LocalDate.now()).dates("dates").daysOfWeek("daysOfWeek").doze("doze").periodType(PrescriptionPeriodType.ONCE).build();
    Prescription prescription2 = Prescription.builder().cure(cure2).patient(patient2).dateOfBegin(LocalDate.now()).dateOfEnd(LocalDate.now()).dates("dates2").daysOfWeek("daysOfWeek2").doze("doze2").periodType(PrescriptionPeriodType.DAILY).build();



    @Before
    @Transactional
    public void beforeTest() {
        dao.findAll().forEach(entity -> dao.delete(entity));
    }


    @Test
    @Transactional
    public void testDeleteAfterCreate() {
        cureDao.create(cure);
        diagnosisDao.create(diagnosis);
        doctorDao.create(doctor);
        patientDao.create(patient);
        prescriptionDao.create(prescription);
        LocalDateTime localDateTime = LocalDateTime.now();

        TherapyEvent entity = TherapyEvent.builder().date(localDateTime).patient(patient).cure(cure).status(TherapyEventStatus.PLANNED).prescription(prescription).build();
        dao.create(entity);

        assertEquals(1, dao.findAll().size());
        entity = dao.findOne(entity.getId());
        assertEquals(localDateTime, entity.getDate());
        assertEquals(patient, entity.getPatient());
        assertEquals(cure, entity.getCure());
        assertEquals(TherapyEventStatus.PLANNED, entity.getStatus());
        assertEquals(prescription, entity.getPrescription());
        assertTrue(entity.getId() > 0);

        dao.delete(entity);
        assertEquals(0, dao.findAll().size());
        assertNull(dao.findOne(entity.getId()));
    }


    @Test
    @Transactional
    public void testUpdate() {
        cureDao.create(cure);
        cureDao.create(cure2);
        diagnosisDao.create(diagnosis);
        diagnosisDao.create(diagnosis2);
        doctorDao.create(doctor);
        doctorDao.create(doctor2);
        patientDao.create(patient);
        patientDao.create(patient2);
        prescriptionDao.create(prescription);
        prescriptionDao.create(prescription2);
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1 = LocalDateTime.now().plusDays(1);

        TherapyEvent entity = TherapyEvent.builder().date(localDateTime).patient(patient).cure(cure).status(TherapyEventStatus.PLANNED).prescription(prescription).build();
        dao.create(entity);
        entity.setDate(localDateTime1);
        entity.setPatient(patient2);
        entity.setCure(cure2);
        entity.setStatus(TherapyEventStatus.EXECUTED);
        entity.setPrescription(prescription2);
        dao.update(entity);

        entity = dao.findOne(entity.getId());
        assertEquals(localDateTime1, entity.getDate());
        assertEquals(patient2, entity.getPatient());
        assertEquals(cure2, entity.getCure());
        assertEquals(TherapyEventStatus.EXECUTED, entity.getStatus());
        assertEquals(prescription2, entity.getPrescription());
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        dao.findAll().forEach(cure -> dao.delete(cure));
        assertEquals(0, dao.findAll().size());
    }
}
