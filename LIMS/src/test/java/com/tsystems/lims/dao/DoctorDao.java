package com.tsystems.lims.dao;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dao.interfaces.IDiagnosisDao;
import com.tsystems.lims.dao.interfaces.IDoctorDao;
import com.tsystems.lims.models.Diagnosis;
import com.tsystems.lims.models.Doctor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfigTest.class})
@WebAppConfiguration
public class DoctorDao {
    @Autowired
    IDoctorDao dao;

    @Before
    @Transactional
    public void beforeTest() {
        dao.create(Doctor.builder().name("Doctor1").build());
        dao.create(Doctor.builder().name("Doctor2").build());
        dao.create(Doctor.builder().name("Doctor3").build());
    }

    @After
    @Transactional
    public void afterTest() {
        dao.findAll().forEach(doctor -> dao.delete(doctor));
    }

    @Test
    @Transactional
    public void testDeleteAfterCreate() {
        Doctor entity = Doctor.builder().name("Doctor4").build();
        dao.create(entity);

        assertEquals(4, dao.findAll().size());
        entity = dao.findOne(entity.getId());
        assertEquals("Doctor4", entity.getName());
        assertTrue(entity.getId() > 0);

        dao.delete(entity);
        assertEquals(3, dao.findAll().size());
        assertNull(dao.findOne(entity.getId()));
    }

    @Test
    @Transactional
    public void testUpdate() {
        Doctor entity = Doctor.builder().name("Doctor4").build();
        dao.create(entity);
        Integer id = entity.getId();

        assertEquals(4, dao.findAll().size());
        entity = dao.findOne(entity.getId());
        assertEquals("Doctor4", entity.getName());
        assertEquals(id, entity.getId());

        entity.setName("Doctor");
        dao.update(entity);

        entity = dao.findOne(entity.getId());
        assertEquals("Doctor", entity.getName());
        assertEquals(id, entity.getId());
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        dao.findAll().forEach(cure -> dao.delete(cure));
        assertEquals(0, dao.findAll().size());
    }

}