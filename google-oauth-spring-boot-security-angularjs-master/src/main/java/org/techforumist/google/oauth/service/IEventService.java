package org.techforumist.google.oauth.service;

import org.techforumist.google.oauth.dto.TherapyEventDto;

import java.util.List;

public interface IEventService {
    List<TherapyEventDto> getEventsByEmail(String email);
}
