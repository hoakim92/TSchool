package com.tsystems.lims.service;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dto.DiagnosisDto;
import com.tsystems.lims.models.Diagnosis;
import com.tsystems.lims.service.interfaces.IDiagnosisService;
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
public class DiagnosisService {
    @Autowired
    IDiagnosisService service;

    @Before
    @Transactional
    public void beforeTest() {
        service.saveOrUpdateDiagnosis(null, "DiagnosisA","DescriptionA");
        service.saveOrUpdateDiagnosis(null, "DiagnosisB","DescriptionB");
        service.saveOrUpdateDiagnosis(null, "DiagnosisC","DescriptionC");
    }

    @After
    @Transactional
    public void afterTest() {
        service.getDiagnoses().forEach(e -> service.deleteDiagnosis(e.getId()));
    }

    @Test
    @Transactional
    public void testFindAllAfterCreate() {
        List<DiagnosisDto> diagnosisDtoList = service.getDiagnoses();
        assertEquals(3, diagnosisDtoList.size());
        assertTrue(diagnosisDtoList.stream().anyMatch(c -> c.getName().equals("DiagnosisA")));
        assertTrue(diagnosisDtoList.stream().anyMatch(c -> c.getName().equals("DiagnosisB")));
        assertTrue(diagnosisDtoList.stream().anyMatch(c -> c.getName().equals("DiagnosisC")));
    }

    @Test(expected = EntityNotFoundException.class)
    @Transactional
    public void testDeleteAfterCreate() {
        DiagnosisDto diagnosis = service.saveOrUpdateDiagnosis(null, "DiagnosisD", "");

        assertEquals(4, service.getDiagnoses().size());
        assertEquals("DiagnosisD", diagnosis.getName());
        assertTrue(diagnosis.getId() > 0);

        service.deleteDiagnosis(diagnosis.getId());
        assertEquals(3, service.getDiagnoses().size());
        assertNull(service.getDiagnosis(diagnosis.getId()));
    }

    @Test
    @Transactional
    public void testUpdate() {
        DiagnosisDto diagnosis = service.saveOrUpdateDiagnosis(null, "DiagnosisD", "");

        assertEquals(4, service.getDiagnoses().size());
        assertEquals("DiagnosisD", diagnosis.getName());
        assertTrue(diagnosis.getId() > 0);

        diagnosis = service.saveOrUpdateDiagnosis(diagnosis.getId(), "Diagnosis","");

        assertEquals("Diagnosis", diagnosis.getName());
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        service.getDiagnoses().forEach(e -> service.deleteDiagnosis(e.getId()));
        assertEquals(0, service.getDiagnoses().size());
    }

}
