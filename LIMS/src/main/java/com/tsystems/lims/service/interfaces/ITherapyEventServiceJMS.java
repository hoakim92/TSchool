package com.tsystems.lims.service.interfaces;

import com.tsystems.lims.dto.TherapyEventDto;
import com.tsystems.lims.models.TherapyEventCause;
import com.tsystems.lims.models.TherapyEventStatus;

import java.util.List;

public interface ITherapyEventServiceJMS {
    public List<TherapyEventDto> getTherapyEvents();
    public List<TherapyEventDto> getTherapyEventsByPrescription(Integer id, Integer offset, Integer count);
    public List<TherapyEventDto> getTherapyEventsByPrescription(Integer id);
    public List<TherapyEventDto> getTherapyEvents(String period, Integer[] patients, Integer offset, Integer count);
    public List<TherapyEventDto> getTherapyEvents(String period, Integer[] patients);
    public TherapyEventDto getTherapyEvent(Integer id);
    public TherapyEventDto updateStatusAndCause(Integer id, TherapyEventStatus status, TherapyEventCause cause);
    public void deleteTherapyevent(Integer id);
}
