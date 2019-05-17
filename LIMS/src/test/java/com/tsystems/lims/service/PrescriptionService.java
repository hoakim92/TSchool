package com.tsystems.lims.service;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dto.*;
import com.tsystems.lims.models.PatientGender;
import com.tsystems.lims.models.PrescriptionPeriodType;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfigTest.class})
@WebAppConfiguration
public class PrescriptionService {
    @Autowired
    IPrescriptionServiceJMS service;

    @Autowired
    ICureService cureService;

    @Autowired
    IDiagnosisService diagnosisService;

    @Autowired
    IDoctorService doctorService;

    @Autowired
    IPatientService patientService;

    @Autowired
    ITherapyEventServiceJMS therapyEventService;

    LocalDate date1 = LocalDate.now();
    LocalDate date2 = LocalDate.now();
    CureDto cure;
    CureDto cure2;
    DiagnosisDto diagnosis;
    DiagnosisDto diagnosis2;
    DoctorDto doctor;
    DoctorDto doctor2;
    PatientDto patient;
    PatientDto patient2;
    PrescriptionDto entity;
    Integer milisecondsInDay = 1 * 24 * 60 * 60 * 1000;


    @Before
    @Transactional
    public void beforeTest() {
        service.getPrescriptions().forEach(entity -> service.deletePrescription(entity.getId()));
        cure = cureService.saveOrUpdateCure(null, "Cure1", "DRUG");
        diagnosis = diagnosisService.saveOrUpdateDiagnosis(null, "Diagnosis1", "");
        doctor = doctorService.saveOrUpdateDoctor(null, "Doctor1");
        patient = patientService.saveOrUpdatePatient(null, doctor.getId(), new Integer[]{diagnosis.getId()}, "Patient1", PatientGender.MALE, LocalDate.now(), "Insurance1",null, "TREAT");
    }


    @Test
    @Transactional
    public void testDeleteAfterCreate() {
        entity = service.saveOrUpdatePrescription(null, patient.getId(), cure.getId(), date1, date2, "doze", PrescriptionPeriodType.ONCE, new String[]{date1.toString() + "T06:00"}, null);

        assertEquals(1, service.getPrescriptions().size());
        assertEquals("Cure1", entity.getCure().getName());
        assertEquals("Patient1", entity.getPatient().getName());
        assertEquals(date1, entity.getDateOfBegin());
        assertEquals(date2, entity.getDateOfEnd());
        assertEquals(1, entity.getEvents().size());
        assertEquals(date1.toString() + "T06:00", entity.getEvents().get(0).getDate());
        assertNull(entity.getEvents().get(0).getDayOfWeek());
        assertEquals("doze", entity.getDoze());
        assertEquals(PrescriptionPeriodType.ONCE.toString(), entity.getPeriodType());
        assertTrue(entity.getId() > 0);
    }


    @Test
    @Transactional
    public void testUpdate() {
        entity = service.saveOrUpdatePrescription(null, patient.getId(), cure.getId(), date1, date2, "doze", PrescriptionPeriodType.ONCE, new String[]{date1.toString() + "T06:00"}, null);
        LocalDate date3 = LocalDate.now().minusDays(1);
        LocalDate date4 = LocalDate.now().plusDays(1);
        cure2 = cureService.saveOrUpdateCure(null, "Cure2", "DRUG");
        diagnosis2 = diagnosisService.saveOrUpdateDiagnosis(null, "Diagnosis2", "");
        doctor2 = doctorService.saveOrUpdateDoctor(null, "Doctor2");
        patient2 = patientService.saveOrUpdatePatient(null, doctor.getId(), new Integer[]{diagnosis.getId()}, "Patient2",PatientGender.MALE, LocalDate.now(), "Insurance2",null, "TREAT");
        entity = service.saveOrUpdatePrescription(entity.getId(), patient2.getId(), cure2.getId(), date3, date4, "doze2", PrescriptionPeriodType.ONCE, new String[]{date3.toString() + "T06:00"}, null);

        assertEquals("Cure2", entity.getCure().getName());
        assertEquals("Patient2", entity.getPatient().getName());
        assertEquals(date3, entity.getDateOfBegin());
        assertEquals(date4, entity.getDateOfEnd());
        assertEquals(1, entity.getEvents().size());
        assertEquals(date3.toString() + "T06:00", entity.getEvents().get(0).getDate());
        assertNull(entity.getEvents().get(0).getDayOfWeek());
        assertEquals("doze2", entity.getDoze());
        assertEquals(PrescriptionPeriodType.ONCE.toString(), entity.getPeriodType());
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        service.getPrescriptions().forEach(e -> service.deletePrescription(e.getId()));
        assertEquals(0, service.getPrescriptions().size());
    }

    @Test
    @Transactional
    public void testCreateEventsOnce() {
        entity = service.saveOrUpdatePrescription(null, patient.getId(), cure.getId(), date1, date2, "doze", PrescriptionPeriodType.ONCE, new String[]{date1.toString() + "T06:00"}, null);
        List<TherapyEventDto> events = therapyEventService.getTherapyEventsByPrescription(entity.getId());

        assertEquals(1, events.size());
        TherapyEventDto event = events.get(0);
        assertEquals(date1.toString() + "T06:00", event.getDate().toString());
        assertEquals(entity.getId(), event.getPrescription());
        assertEquals("Cure1", event.getCure().getName());
        assertEquals("Patient1", event.getPatient().getName());
        assertEquals(TherapyEventStatus.PLANNED.toString(), event.getStatus());
    }

    @Test
    @Transactional
    public void testCreateEventsDaily() {
        LocalDate date3 = LocalDate.now();
        LocalDate date4 = LocalDate.now().plusDays(3);
        entity = service.saveOrUpdatePrescription(null, patient.getId(), cure.getId(), date3, date4, "doze", PrescriptionPeriodType.DAILY, new String[]{"06:00"}, null);
        List<TherapyEventDto> events = therapyEventService.getTherapyEventsByPrescription(entity.getId());
        LocalDateTime dateTime = date3.atTime(6, 0);

        assertEquals(3, events.size());
        assertTrue(events.stream().anyMatch(event -> event.getDate().equals(dateTime)));
        assertTrue(events.stream().anyMatch(event -> event.getDate().equals(dateTime.plusDays(1))));
        assertTrue(events.stream().anyMatch(event -> event.getDate().equals(dateTime.plusDays(2))));
        assertTrue(events.stream().allMatch(event -> event.getPrescription().equals(entity.getId())));
        assertTrue(events.stream().allMatch(event -> event.getCure().getName().equals("Cure1")));
        assertTrue(events.stream().allMatch(event -> event.getPatient().getName().equals("Patient1")));
        assertTrue(events.stream().allMatch(event -> event.getStatus().equals(TherapyEventStatus.PLANNED.toString())));
    }

    @Test
    @Transactional
    public void testCreateEventsWeekly() {
        LocalDate dateMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate date4 = dateMonday.plusWeeks(1);
        entity = service.saveOrUpdatePrescription(null, patient.getId(), cure.getId(), dateMonday, date4, "doze", PrescriptionPeriodType.WEEKLY, new String[]{"12:00","12:00"}, new Integer[]{1, 5});
        List<TherapyEventDto> events = therapyEventService.getTherapyEventsByPrescription(entity.getId());
        assertEquals(2, events.size());
        assertTrue(events.stream().anyMatch(event -> event.getDate().equals(dateMonday.atTime(12,0))));
        assertTrue(events.stream().anyMatch(event -> event.getDate().equals(dateMonday.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).atTime(12, 0))));
        assertTrue(events.stream().allMatch(event -> event.getPrescription().equals(entity.getId())));
        assertTrue(events.stream().allMatch(event -> event.getCure().getName().equals("Cure1")));
        assertTrue(events.stream().allMatch(event -> event.getPatient().getName().equals("Patient1")));
        assertTrue(events.stream().allMatch(event -> event.getStatus().equals(TherapyEventStatus.PLANNED.toString())));
    }
}
