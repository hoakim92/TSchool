package org.techforumist.google.oauth.web;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techforumist.google.oauth.dto.TherapyEventDto;
import org.techforumist.google.oauth.model.TherapyEvent;
import org.techforumist.google.oauth.repository.EventRepository;
import org.techforumist.google.oauth.service.EventService;
import org.techforumist.google.oauth.service.IEventService;

@RestController
public class UserRestController {
	@Autowired
	private IEventService eventService;

	@RequestMapping("/user")
	public Principal sayHello(Principal principal) {
		return principal;
	}

	@RequestMapping("/events")
	public List<TherapyEventDto> getEvents(Principal principal) {
		return eventService.getEventsByEmail(((LinkedHashMap<String,String>)((OAuth2Authentication) principal).getUserAuthentication().getDetails()).get("email"));
	}

}
