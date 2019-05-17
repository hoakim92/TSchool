package com.tsystems.lims.service.interfaces;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dto.PrescriptionDto;
import com.tsystems.lims.dto.TherapyEventDto;
import com.tsystems.lims.models.TherapyEvent;
import com.tsystems.lims.models.TherapyEventCause;
import com.tsystems.lims.models.TherapyEventStatus;
import com.tsystems.lims.service.common.IOperationsService;

import java.util.List;
import java.util.Map;

public interface ITherapyEventService  {
    public List<TherapyEventDto> getTherapyEvents();
    public List<TherapyEventDto> getTherapyEvents(Map<String, String> parameters, int offset, int count);
    public List<TherapyEventDto> getTherapyEventsByPrescription(Integer id, Integer offset, Integer count);
    public List<TherapyEventDto> getTherapyEventsByPrescription(Integer id);
    public List<TherapyEventDto> getTherapyEvents(String period, Integer[] patients, Integer offset, Integer count);
    public List<TherapyEventDto> getTherapyEvents(String period, Integer[] patients);
    public TherapyEventDto getTherapyEvent(Integer id);
    public TherapyEventDto updateStatusAndCause(Integer id, TherapyEventStatus status, TherapyEventCause cause);
    public void deleteTherapyevent(Integer id);
}
