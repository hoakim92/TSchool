package com.tsystems.lims.dto;

import com.tsystems.lims.models.Cure;
import com.tsystems.lims.models.TherapyEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CureDto {
    Integer id;
    String name;
    String type;

    public CureDto(Cure cure) {
        this.id = cure.getId();
        this.name = cure.getName();
        this.type = cure.getType().toString();
    }

    public static List<CureDto> getAsDto(List<Cure> cures) {
        return cures.stream().map(CureDto::new).collect(Collectors.toList());
    }
}
