package com.tsystems.lims.service.impl;

import com.tsystems.lims.dto.TherapyEventDto;
import com.tsystems.lims.models.TherapyEventCause;
import com.tsystems.lims.models.TherapyEventStatus;
import com.tsystems.lims.service.common.JmsService;
import com.tsystems.lims.service.interfaces.ITherapyEventService;
import com.tsystems.lims.service.interfaces.ITherapyEventServiceJMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TherapyEventServiceJMS extends JmsService implements ITherapyEventServiceJMS {
    @Autowired
    ITherapyEventService therapyEventService;

    @Override
    public List<TherapyEventDto> getTherapyEvents() {
        return therapyEventService.getTherapyEvents();
    }

    @Override
    public List<TherapyEventDto> getTherapyEventsByPrescription(Integer id, Integer offset, Integer count) {
        return therapyEventService.getTherapyEventsByPrescription(id, offset, count);
    }

    @Override
    public List<TherapyEventDto> getTherapyEventsByPrescription(Integer id) {
        return therapyEventService.getTherapyEventsByPrescription(id);
    }

    @Override
    public List<TherapyEventDto> getTherapyEvents(String period, Integer[] patients, Integer offset, Integer count) {
        return therapyEventService.getTherapyEvents(period, patients, offset, count);
    }

    @Override
    public List<TherapyEventDto> getTherapyEvents(String period, Integer[] patients) {
        return therapyEventService.getTherapyEvents(period, patients);
    }

    @Override
    public TherapyEventDto getTherapyEvent(Integer id) {
        return therapyEventService.getTherapyEvent(id);
    }

    @Override
    public TherapyEventDto updateStatusAndCause(Integer id, TherapyEventStatus status, TherapyEventCause cause) {
        TherapyEventDto therapyEventDto = therapyEventService.updateStatusAndCause(id, status, cause);
        sendJmsNotification();
        return therapyEventDto;
    }


    @Override
    public void deleteTherapyevent(Integer id) {
        sendJmsNotification();
        therapyEventService.deleteTherapyevent(id);
    }
}
