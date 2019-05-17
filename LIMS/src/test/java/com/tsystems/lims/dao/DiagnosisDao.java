package com.tsystems.lims.dao;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dao.interfaces.IDiagnosisDao;
import com.tsystems.lims.models.Cure;
import com.tsystems.lims.models.CureType;
import com.tsystems.lims.models.Diagnosis;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfigTest.class})
@WebAppConfiguration
public class DiagnosisDao {
    @Autowired
    IDiagnosisDao dao;

    @Before
    @Transactional
    public void beforeTest() {
        dao.create(Diagnosis.builder().name("Diagnosis1").description("Description1").build());
        dao.create(Diagnosis.builder().name("Diagnosis2").description("Description2").build());
        dao.create(Diagnosis.builder().name("Diagnosis3").description("Description3").build());
    }

    @After
    @Transactional
    public void afterTest() {
        dao.findAll().forEach(cure -> dao.delete(cure));
    }

    @Test
    @Transactional
    public void testDeleteAfterCreate() {
        Diagnosis entity = Diagnosis.builder().name("Diagnosis4").description("Description4").build();
        dao.create(entity);

        assertEquals(4, dao.findAll().size());
        entity = dao.findOne(entity.getId());
        assertEquals("Diagnosis4", entity.getName());
        assertEquals("Description4", entity.getDescription());
        assertTrue(entity.getId() > 0);

        dao.delete(entity);
        assertEquals(3, dao.findAll().size());
        assertNull(dao.findOne(entity.getId()));
    }

    @Test
    @Transactional
    public void testUpdate() {
        Diagnosis entity = Diagnosis.builder().name("Diagnosis4").description("Description4").build();
        dao.create(entity);
        Integer id = entity.getId();

        assertEquals(4, dao.findAll().size());
        entity = dao.findOne(entity.getId());
        assertEquals("Diagnosis4", entity.getName());
        assertEquals("Description4", entity.getDescription());
        assertEquals(id, entity.getId());

        entity.setName("Diagnosis");
        entity.setDescription("Description");
        dao.update(entity);

        entity = dao.findOne(entity.getId());
        assertEquals("Diagnosis", entity.getName());
        assertEquals("Description", entity.getDescription());
        assertEquals(id, entity.getId());
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        dao.findAll().forEach(cure -> dao.delete(cure));
        assertEquals(0, dao.findAll().size());
    }

}
