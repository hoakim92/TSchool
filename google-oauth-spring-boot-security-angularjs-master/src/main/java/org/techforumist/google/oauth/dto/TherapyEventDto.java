package org.techforumist.google.oauth.dto;

import org.techforumist.google.oauth.model.TherapyEvent;

import java.time.LocalDateTime;

public class TherapyEventDto {
    public String getCure() {
        return cure;
    }

    public void setCure(String cure) {
        this.cure = cure;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    String cure;
    String type;
    LocalDateTime date;

    public TherapyEventDto(TherapyEvent event) {
        cure = event.getCure().getName();
        type = event.getCure().getType().name();
        date = event.getDate();

    }
}
