package com.tsystems.lims.dao;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dao.interfaces.ICureDao;
import com.tsystems.lims.dao.interfaces.IPatientDao;
import com.tsystems.lims.dao.interfaces.IPrescriptionDao;
import com.tsystems.lims.models.Cure;
import com.tsystems.lims.models.Patient;
import com.tsystems.lims.models.Prescription;
import com.tsystems.lims.models.PrescriptionPeriodType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfigTest.class})
@WebAppConfiguration
public class PrescriptionDao {
    @Autowired
    IPrescriptionDao dao;

    @Autowired
    ICureDao cureDao;

    @Autowired
    IPatientDao patientDao;

    Patient patient = Patient.builder().name("Patient").insurance("insurance").build();
    Patient patient2 = Patient.builder().name("Patient2").insurance("insurance").build();
    Cure cure = Cure.builder().name("Cure").build();
    Cure cure2 = Cure.builder().name("Cure2").build();

    @Before
    @Transactional
    public void beforeTest() {
        dao.findAll().forEach(entity -> dao.delete(entity));
    }


    @Test
    @Transactional
    public void testDeleteAfterCreate() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now();
        patientDao.create(patient);
        patient = patientDao.findOne(patient.getId());
        cureDao.create(cure);
        cure = cureDao.findOne(cure.getId());

        Prescription entity = Prescription.builder().cure(cure).patient(patient).dateOfBegin(date1).dateOfEnd(date2).dates("dates").daysOfWeek("daysOfWeek").doze("doze").periodType(PrescriptionPeriodType.ONCE).build();
        dao.create(entity);

        assertEquals(1, dao.findAll().size());
        entity = dao.findOne(entity.getId());
        assertEquals(cure, entity.getCure());
        assertEquals(patient, entity.getPatient());
        assertEquals(date1, entity.getDateOfBegin());
        assertEquals(date2, entity.getDateOfEnd());
        assertEquals("dates", entity.getDates());
        assertEquals("daysOfWeek", entity.getDaysOfWeek());
        assertEquals("doze", entity.getDoze());
        assertEquals(PrescriptionPeriodType.ONCE, entity.getPeriodType());
        assertTrue(entity.getId() > 0);

        dao.delete(entity);
        assertEquals(0, dao.findAll().size());
        assertNull(dao.findOne(entity.getId()));
    }


    @Test
    @Transactional
    public void testUpdate() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now();
        LocalDate date3 = LocalDate.now().plusDays(1);
        LocalDate date4 = LocalDate.now().plusDays(1);
        patientDao.create(patient);
        patient = patientDao.findOne(patient.getId());
        cureDao.create(cure);
        cureDao.create(cure2);
        cure = cureDao.findOne(cure.getId());
        cure2 = cureDao.findOne(cure2.getId());

        Prescription entity = Prescription.builder().cure(cure).patient(patient).dateOfBegin(date1).dateOfEnd(date2).dates("dates").daysOfWeek("daysOfWeek").doze("doze").periodType(PrescriptionPeriodType.ONCE).build();
        dao.create(entity);

        entity.setCure(cure2);
        entity.setPatient(patient2);
        entity.setDateOfBegin(date3);
        entity.setDateOfEnd(date4);
        entity.setDates("dates2");
        entity.setDaysOfWeek("daysOfWeek2");
        entity.setDoze("doze2");
        entity.setPeriodType(PrescriptionPeriodType.DAILY);
        dao.update(entity);

        entity = dao.findOne(entity.getId());
        assertEquals(cure2, entity.getCure());
        assertEquals(patient2, entity.getPatient());
        assertEquals(date3, entity.getDateOfBegin());
        assertEquals(date4, entity.getDateOfEnd());
        assertEquals("dates2", entity.getDates());
        assertEquals("daysOfWeek2", entity.getDaysOfWeek());
        assertEquals("doze2", entity.getDoze());
        assertEquals(PrescriptionPeriodType.DAILY, entity.getPeriodType());
        assertTrue(entity.getId() > 0);
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        dao.findAll().forEach(cure -> dao.delete(cure));
        assertEquals(0, dao.findAll().size());
    }
}
