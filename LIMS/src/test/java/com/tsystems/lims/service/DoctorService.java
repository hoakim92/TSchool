package com.tsystems.lims.service;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dto.DoctorDto;
import com.tsystems.lims.service.interfaces.IDoctorService;
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
public class DoctorService {
    @Autowired
    IDoctorService service;

    @Before
    @Transactional
    public void beforeTest() {
        service.saveOrUpdateDoctor(null, "Doctor1");
        service.saveOrUpdateDoctor(null, "Doctor2");
        service.saveOrUpdateDoctor(null, "Doctor3");
    }

    @After
    @Transactional
    public void afterTest() {
        service.getDoctors().forEach(doctor -> service.deleteDoctor(doctor.getId()));
    }

    @Test
    @Transactional
    public void testFindAllAfterCreate() {
        List<DoctorDto> doctorsDtoList = service.getDoctors();
        assertEquals(3, doctorsDtoList.size());
        assertTrue(doctorsDtoList.stream().anyMatch(c -> c.getName().equals("Doctor1")));
        assertTrue(doctorsDtoList.stream().anyMatch(c -> c.getName().equals("Doctor2")));
        assertTrue(doctorsDtoList.stream().anyMatch(c -> c.getName().equals("Doctor3")));
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDeleteAfterCreate() {
        DoctorDto entity = service.saveOrUpdateDoctor(null, "Doctor4");

        assertEquals(4, service.getDoctors().size());
        assertEquals("Doctor4", entity.getName());
        assertTrue(entity.getId() > 0);

        service.deleteDoctor(entity.getId());
        assertEquals(3, service.getDoctors().size());
        assertNull(service.getDoctor(entity.getId()));
    }

    @Test
    @Transactional
    public void testUpdate() {
        DoctorDto entity = service.saveOrUpdateDoctor(null, "Doctor4");

        assertEquals(4, service.getDoctors().size());
        assertEquals("Doctor4", entity.getName());
        assertTrue(entity.getId() > 0);

        entity = service.saveOrUpdateDoctor(null, "Doctor");

        assertEquals("Doctor", entity.getName());
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        service.getDoctors().forEach(e -> service.deleteDoctor(e.getId()));
        assertEquals(0, service.getDoctors().size());
    }

}
