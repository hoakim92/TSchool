package com.tsystems.lims.service.impl;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dao.interfaces.ITherapyEventDao;
import com.tsystems.lims.dto.PrescriptionDto;
import com.tsystems.lims.dto.TherapyEventDto;
import com.tsystems.lims.models.Prescription;
import com.tsystems.lims.models.TherapyEvent;
import com.tsystems.lims.models.TherapyEventCause;
import com.tsystems.lims.models.TherapyEventStatus;
import com.tsystems.lims.service.common.AbstractHibernateService;
import com.tsystems.lims.service.interfaces.ITherapyEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.tsystems.lims.dto.TherapyEventDto.getAsDto;
import static com.tsystems.lims.util.DateUtils.*;
import static java.util.Arrays.asList;

@Service
@Transactional
public class TherapyEventService extends AbstractHibernateService<TherapyEvent> implements ITherapyEventService {
    @Autowired
    ITherapyEventDao dao;

    @Override
    protected IOperations<TherapyEvent> getDao() {
        return dao;
    }

    @Override
    public List<TherapyEventDto> getTherapyEvents() {
        return getAsDto(dao.findAll());
    }

    @Override
    public List<TherapyEventDto> getTherapyEvents(Map<String, String> parameters, int offset, int count) {
        return TherapyEventDto.getAsDto(dao.findAll(parameters, offset, count));
    }

    @Override
    public List<TherapyEventDto> getTherapyEventsByPrescription(Integer id, Integer offset, Integer count) {
        return getAsDto(getDao().findAll(new HashMap<String, String>() {{
            put("prescription.id", id.toString());
        }}, offset, count));
    }

    @Override
    public List<TherapyEventDto> getTherapyEventsByPrescription(Integer id) {
        return getTherapyEventsByPrescription(id, null, null);
    }

    @Override
    public List<TherapyEventDto> getTherapyEvents(String period, Integer[] patients, Integer offset, Integer count) {
        List<TherapyEventDto> result = new ArrayList<>();
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime time = LocalTime.now();
        if (period.equals("All")) {
            if (patients != null && patients.length > 0) {
                result = getAsDto(dao.findAllByPatients(asList(patients), offset, count));
            } else {
                result = getAsDto(dao.findAll(offset, count));
            }
        }
        if (period.equals("ByDay")) {
            if (patients != null && patients.length > 0) {
                result = getAsDto(dao.findAllByPeriodAndPatients(date.atStartOfDay(), date.atTime(LocalTime.MAX), asList(patients), offset, count));
            } else {
                result = getAsDto(dao.findAllByPeriod(date.atStartOfDay(), date.atTime(LocalTime.MAX), offset, count));
            }
        }
        if (period.equals("ByHour")) {
            if (patients != null && patients.length > 0) {
                result = getAsDto(dao.findAllByPeriodAndPatients(dateTime.withHour(time.getHour()), dateTime.withHour(time.getHour()).withMinute(59).withSecond(59), asList(patients), offset, count));
            } else {
                result = getAsDto(dao.findAllByPeriod(dateTime.withHour(time.getHour()), dateTime.withHour(time.getHour()).withMinute(59).withSecond(59), offset, count));
            }
        }
        return result;
    }

    @Override
    public List<TherapyEventDto> getTherapyEvents(String period, Integer[] patients) {
        return getTherapyEvents(period, patients, null, null);
    }

    @Override
    public TherapyEventDto getTherapyEvent(Integer id) {
        TherapyEvent therapyEvent = findOne(id);
        if (therapyEvent == null)
            throw new EntityNotFoundException("Not found therapyEvent with id = " + id);
        return new TherapyEventDto(dao.findOne(id));
    }

    @Override
    public TherapyEventDto updateStatusAndCause(Integer id, TherapyEventStatus status, TherapyEventCause cause) throws UnsupportedOperationException {
        TherapyEvent therapyEvent = findOne(id);
        if (therapyEvent.getStatus().equals(TherapyEventStatus.CANCELED) | therapyEvent.getStatus().equals(TherapyEventStatus.EXECUTED))
            throw new UnsupportedOperationException("You could update cancelled or executed event");
        if (therapyEvent.getStatus().equals(TherapyEventStatus.PLANNED) & status == TherapyEventStatus.CANCELED & cause == null)
            throw new UnsupportedOperationException("You should define cause when you cancel event");
        therapyEvent.setStatus(status);
        if (!status.equals(TherapyEventStatus.EXECUTED))
            therapyEvent.setCause(cause);
        update(therapyEvent);
        return new TherapyEventDto(therapyEvent);

    }

    @Override
    public void deleteTherapyevent(Integer id) {
        dao.deleteById(id);
    }
}
