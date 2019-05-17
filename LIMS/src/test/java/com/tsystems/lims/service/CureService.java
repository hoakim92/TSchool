package com.tsystems.lims.service;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dto.CureDto;
import com.tsystems.lims.models.Cure;
import com.tsystems.lims.models.CureType;
import com.tsystems.lims.service.interfaces.ICureService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfigTest.class})
@WebAppConfiguration
public class CureService {
    @Autowired
    ICureService service;

    @Before
    @Transactional
    public void beforeTest(){
        service.saveOrUpdateCure(null, "CureA","DRUG");
        service.saveOrUpdateCure(null, "CureB","DRUG");
        service.saveOrUpdateCure(null, "CureC","DRUG");
    }

    @After
    @Transactional
    public void afterTest(){
        service.getCures().forEach(e -> service.deleteCure(e.getId()));
    }

    @Test
    @Transactional
    public void testFindAllAfterCreate(){
        List<CureDto> cures =service.getCures();
        assertEquals(3, cures.size());
        assertTrue(cures.stream().anyMatch(c -> c.getName().equals("CureA") & c.getType().equals(CureType.DRUG.toString())));
        assertTrue(cures.stream().anyMatch(c -> c.getName().equals("CureB") & c.getType().equals(CureType.DRUG.toString())));
        assertTrue(cures.stream().anyMatch(c -> c.getName().equals("CureC") & c.getType().equals(CureType.DRUG.toString())));
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDeleteAfterCreate() {
        CureDto cure = service.saveOrUpdateCure(null, "CureD", "DRUG");

        assertEquals(4, service.getCures().size());
        assertEquals("CureD", cure.getName());
        assertEquals(CureType.DRUG.toString(), cure.getType());
        assertTrue(cure.getId() > 0);

        service.deleteCure(cure.getId());
        assertEquals(3, service.getCures().size());
        assertNull(service.getCure(cure.getId()));
    }

    @Test
    @Transactional
    public void testUpdate() {
        CureDto cure = service.saveOrUpdateCure(null, "CureD","DRUG");

        assertEquals(4, service.getCures().size());
        assertEquals("CureD", cure.getName());
        assertEquals(CureType.DRUG.toString(), cure.getType());
        assertEquals(cure.getId(), cure.getId());

        cure = service.saveOrUpdateCure(cure.getId(), cure.getName(), "PROCEDURE");

        assertEquals("CureD", cure.getName());
        assertEquals(CureType.PROCEDURE.toString(), cure.getType());
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        service.getCures().forEach(cure -> service.deleteCure(cure.getId()));
        assertEquals(0, service.getCures().size());
    }

}
