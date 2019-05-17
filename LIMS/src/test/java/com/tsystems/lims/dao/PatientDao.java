package com.tsystems.lims.dao;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dao.interfaces.IDiagnosisDao;
import com.tsystems.lims.dao.interfaces.IDoctorDao;
import com.tsystems.lims.dao.interfaces.IPatientDao;
import com.tsystems.lims.models.Diagnosis;
import com.tsystems.lims.models.Doctor;
import com.tsystems.lims.models.Patient;
import com.tsystems.lims.models.PatientStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfigTest.class})
@WebAppConfiguration
public class PatientDao {
    @Autowired
    IPatientDao dao;

    @Autowired
    IDoctorDao doctorDao;

    @Autowired
    IDiagnosisDao diagnosisDao;

    Doctor doctor1;
    Doctor doctor2;
    Diagnosis diagnosis1;
    Diagnosis diagnosis2;

    @Before
    @Transactional
    public void beforeTest() {
        doctor1 = Doctor.builder().name("Doctor1").build();
        doctor2 = Doctor.builder().name("Doctor2").build();
        doctorDao.create(doctor1);
        doctorDao.create(doctor2);
        diagnosis1 = Diagnosis.builder().name("Diagnosis1").description("Description1").build();
        diagnosis2 = Diagnosis.builder().name("Diagnosis2").description("Description2").build();
        diagnosisDao.create(diagnosis1);
        diagnosisDao.create(diagnosis2);

        dao.findAll().forEach(patient -> dao.delete(patient));
        dao.create(Patient.builder().name("Patient1").doctor(doctor1).insurance("insurance1").status(PatientStatus.TREAT).diagnosisSet(new HashSet<Diagnosis>() {{
            add(diagnosis1);
        }}).build());

    }


    @Test
    @Transactional
    public void testDeleteAfterCreate() {
        Patient entity = Patient.builder().name("Patient").doctor(doctor1).insurance("insurance").status(PatientStatus.TREAT).diagnosisSet(new HashSet<Diagnosis>() {{
            add(diagnosis1);
        }}).build();
        dao.create(entity);

        assertEquals(2, dao.findAll().size());
        entity = dao.findOne(entity.getId());
        assertEquals("Patient", entity.getName());
        assertEquals(doctor1, entity.getDoctor());
        assertEquals("insurance", entity.getInsurance());
        assertEquals(PatientStatus.TREAT, entity.getStatus());
        assertEquals(1, entity.getDiagnosisSet().size());
        assertTrue(entity.getDiagnosisSet().contains(diagnosis1));
        assertTrue(entity.getId() > 0);

        dao.delete(entity);
        assertEquals(1, dao.findAll().size());
        assertNull(dao.findOne(entity.getId()));
    }

    @Test
    @Transactional
    public void testUpdate() {
        Patient entity = Patient.builder().name("Patient").doctor(doctor1).insurance("insurance").status(PatientStatus.TREAT).diagnosisSet(new HashSet<Diagnosis>() {{
            add(diagnosis1);
        }}).build();
        dao.create(entity);
        Integer id = entity.getId();

        entity.setName("Patient3");
        entity.setDiagnosisSet(new HashSet<Diagnosis>() {{
            add(diagnosis2);
        }});
        entity.setDoctor(doctor2);
        entity.setInsurance("insuranceC");
        entity.setStatus(PatientStatus.DISCHARGED);
        dao.update(entity);

        entity = dao.findOne(entity.getId());
        assertEquals("Patient3", entity.getName());
        assertEquals(doctor2, entity.getDoctor());
        assertEquals("insuranceC", entity.getInsurance());
        assertEquals(PatientStatus.DISCHARGED, entity.getStatus());
        assertEquals(1, entity.getDiagnosisSet().size());
        assertTrue(entity.getDiagnosisSet().contains(diagnosis2));
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        dao.findAll().forEach(cure -> dao.delete(cure));
        assertEquals(0, dao.findAll().size());
    }

}
