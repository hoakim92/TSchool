package com.tsystems.lims.dao;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dao.interfaces.ICureDao;
import com.tsystems.lims.models.Cure;
import com.tsystems.lims.models.CureType;
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
public class CureDao {
    @Autowired
    ICureDao dao;

    @Before
    @Transactional
    public void beforeTest(){
        dao.create(Cure.builder().name("CureA").type(CureType.DRUG).build());
        dao.create(Cure.builder().name("CureB").type(CureType.DRUG).build());
        dao.create(Cure.builder().name("CureC").type(CureType.DRUG).build());
    }

    @After
    @Transactional
    public void afterTest(){
        dao.findAll().forEach(cure -> dao.delete(cure));
    }

    @Test
    @Transactional
    public void testFindAllAfterCreate() {
        assertEquals(3, dao.findAll().size());
    }

    @Test
    @Transactional
    public void testDeleteAfterCreate() {
        Cure cure = Cure.builder().name("CureD").type(CureType.DRUG).build();
        dao.create(cure);

        assertEquals(4, dao.findAll().size());
        cure = dao.findOne(cure.getId());
        assertEquals("CureD", cure.getName());
        assertEquals(CureType.DRUG, cure.getType());
        assertTrue(cure.getId() > 0);

        dao.delete(cure);
        assertEquals(3, dao.findAll().size());
        assertNull(dao.findOne(cure.getId()));
    }

    @Test
    @Transactional
    public void testUpdate() {
        Cure cure = Cure.builder().name("CureD").type(CureType.DRUG).build();
        dao.create(cure);
        Integer id = cure.getId();

        assertEquals(4, dao.findAll().size());
        cure = dao.findOne(cure.getId());
        assertEquals("CureD", cure.getName());
        assertEquals(CureType.DRUG, cure.getType());
        assertEquals(id, cure.getId());

        cure.setName("Therapy");
        cure.setType(CureType.PROCEDURE);
        dao.update(cure);

        cure = dao.findOne(cure.getId());
        assertEquals("Therapy", cure.getName());
        assertEquals(CureType.PROCEDURE, cure.getType());
        assertEquals(id, cure.getId());

    }

    @Test
    @Transactional
    public void testDeleteAll() {
        dao.findAll().forEach(cure -> dao.delete(cure));
        assertEquals(0, dao.findAll().size());
    }
}
