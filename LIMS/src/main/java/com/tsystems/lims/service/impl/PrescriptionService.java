package com.tsystems.lims.service.impl;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dao.interfaces.ICureDao;
import com.tsystems.lims.dao.interfaces.IPatientDao;
import com.tsystems.lims.dao.interfaces.IPrescriptionDao;
import com.tsystems.lims.dao.interfaces.ITherapyEventDao;
import com.tsystems.lims.dto.PrescriptionDto;
import com.tsystems.lims.models.*;
import com.tsystems.lims.service.common.AbstractHibernateService;
import com.tsystems.lims.service.interfaces.IPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.tsystems.lims.dto.PrescriptionDto.getAsDto;
import static com.tsystems.lims.models.TherapyEventStatus.*;
import static com.tsystems.lims.util.DateUtils.*;
import static java.lang.String.join;
import static java.util.Collections.singletonList;

@Service
@Transactional
public class PrescriptionService extends AbstractHibernateService<Prescription> implements IPrescriptionService {
    @Autowired
    IPrescriptionDao dao;

    @Autowired
    ICureDao cureDao;

    @Autowired
    IPatientDao patientDao;

    @Autowired
    ITherapyEventDao therapyEventDao;

    public List<PrescriptionDto> getPrescriptions() {
        return getAsDto(dao.findAll());
    }

    public List<PrescriptionDto> getPrescriptions(Map<String, String> parameters, int offset, int count) {
        return getAsDto(dao.findAll(parameters, offset, count));
    }

    public List<PrescriptionDto> getPrescriptionsByPatientId(Integer patientId) {
        return PrescriptionDto.getAsDto(dao.getPrescriptionsByPatientId(patientId));
    }

    public PrescriptionDto getPrescription(Integer id) {
        Prescription prescription = findOne(id);
        if (prescription == null)
            throw new EntityNotFoundException("Not found prescription with id = " + id);
        return new PrescriptionDto(findOne(id));
    }

    public PrescriptionDto saveOrUpdatePrescription(Integer id, Integer patientId, Integer cureId, LocalDate dateOfBegin, LocalDate dateOfEnd, String doze, PrescriptionPeriodType periodType, String[] dates, Integer[] daysOfWeek) {
        Prescription prescription = null;
        String daysOfWeekString = null;
        if (dates == null || dates.length == 0) {
            throw new UnsupportedOperationException("you should define dates");
        }

        if (dateOfBegin.isAfter(dateOfEnd)) {
            throw new UnsupportedOperationException("date of begin should be early then date of end");
        }

        if (periodType.equals(PrescriptionPeriodType.ONCE)) {
            validateDate(dates, dateTimeFormat);
        }

        if (periodType.equals(PrescriptionPeriodType.DAILY)) {
            validateDate(dates, timeFormat);
        }

        if (periodType.equals(PrescriptionPeriodType.WEEKLY)) {
            if (daysOfWeek == null || daysOfWeek.length == 0)
                throw new UnsupportedOperationException("you should define days of week");
            validateDate(dates, timeFormat);
            Arrays.asList(daysOfWeek).forEach(d -> {
                if (d < 0 | d > 7)
                    throw new UnsupportedOperationException("Invalid day of week format");
            });

            daysOfWeekString = Arrays.stream(daysOfWeek).map(Object::toString).collect(Collectors.joining(delimiter));
        }


        Cure cure = cureDao.findOne(cureId);
        Patient patient = patientDao.findOne(patientId);
        Prescription.PrescriptionBuilder builder = Prescription.builder();
        if (id == null) {
            if (LocalDate.now().isAfter(dateOfBegin))
                throw new UnsupportedOperationException("you try to create prescription with date of begin in the past");
            prescription = builder
                    .patient(patient)
                    .cure(cure)
                    .dateOfBegin(dateOfBegin)
                    .dateOfEnd(dateOfEnd)
                    .doze(doze)
                    .periodType(periodType)
                    .daysOfWeek(daysOfWeekString)
                    .dates(join(delimiter, dates))
                    .build();
            dao.create(prescription);
            id = prescription.getId();
        } else {
            if (dateOfEnd.isAfter(LocalDate.now())) {
                if (therapyEventDao.findAllByPrescriptionAndStatus(id, singletonList(EXECUTED)).size() == 0) {
                    cancelPlannedEvents(id);
                    dao.update(builder
                            .id(id)
                            .patient(patient)
                            .cure(cure)
                            .dateOfBegin(dateOfBegin)
                            .dateOfEnd(dateOfEnd)
                            .doze(doze)
                            .periodType(periodType)
                            .daysOfWeek(daysOfWeekString)
                            .dates(join(delimiter, dates))
                            .build());
                } else {
                    throw new UnsupportedOperationException("you have happened events for this prescription");
                }
            } else {
                throw new UnsupportedOperationException("you try to update ended prescription");
            }
        }
        prescription = findOne(id);
        generateEvents(prescription).forEach(e -> therapyEventDao.create(e));
        return new PrescriptionDto(prescription);
    }


    @Override
    protected IOperations<Prescription> getDao() {
        return dao;
    }

    List<TherapyEvent> generateEvents(Prescription prescription) {
        List<TherapyEvent> result = new ArrayList<>();
        if (prescription.getPeriodType().equals(PrescriptionPeriodType.ONCE)) {
            for (String date : prescription.getDates().split(delimiter)) {
                result.add(TherapyEvent.builder().prescription(prescription).cure(prescription.getCure()).date(LocalDateTime.parse(date)).patient(prescription.getPatient()).status(PLANNED).build());
            }
        }
        if (prescription.getPeriodType().equals(PrescriptionPeriodType.DAILY)) {
            for (String time : prescription.getDates().split(delimiter)) {
                for (int i = 0; i < prescription.getDateOfBegin().until(prescription.getDateOfEnd(), ChronoUnit.DAYS); i++) {
                    LocalDateTime dateTime = prescription.getDateOfBegin().plusDays(i).atTime(LocalTime.parse(time));
                    result.add(TherapyEvent.builder().prescription(prescription).cure(prescription.getCure()).date(dateTime).patient(prescription.getPatient()).status(PLANNED).build());
                }
            }
        }
        if (prescription.getPeriodType().equals(PrescriptionPeriodType.WEEKLY)) {
            String[] days = prescription.getDaysOfWeek().split(delimiter);
            String[] dates = prescription.getDates().split(delimiter);
            for (int i = 0; i < dates.length; i++) {
                int dayOfWeek = Integer.valueOf(days[i]);
                for (int j = 0; j < prescription.getDateOfBegin().until(prescription.getDateOfEnd(), ChronoUnit.DAYS); j++) {
                    LocalDate date = prescription.getDateOfBegin().plusDays(j);
                    if (date.getDayOfWeek().getValue() == dayOfWeek) {
                        result.add(TherapyEvent.builder().prescription(prescription).cure(prescription.getCure()).date(date.atTime(LocalTime.parse(dates[i]))).patient(prescription.getPatient()).status(PLANNED).build());
                    }
                }
            }
        }
        return result;
    }

    public void cancelPlannedEvents(Integer id) {
        therapyEventDao.findAllByPrescriptionAndStatus(id, singletonList(PLANNED)).forEach(event -> {
            event.setStatus(CANCELED);
            event.setCause(TherapyEventCause.AUTO_CANCEL);
            therapyEventDao.update(event);
        });
    }

    @Override
    public void deletePrescription(Integer id) {
        therapyEventDao.findAllByPrescription(id).forEach(therapyEvent -> therapyEventDao.deleteById(therapyEvent.getId()));
        dao.deleteById(id);
    }

}
