package org.techforumist.google.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.techforumist.google.oauth.dto.TherapyEventDto;
import org.techforumist.google.oauth.model.TherapyEventStatus;
import org.techforumist.google.oauth.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService implements IEventService {
    @Autowired
    EventRepository repository;

    @Override
    public List<TherapyEventDto> getEventsByEmail(String email) {
        return repository.findByPatientEmail(email).stream().filter(e -> e.getStatus().equals(TherapyEventStatus.PLANNED)).map(TherapyEventDto::new).collect(Collectors.toList());
    }
}
