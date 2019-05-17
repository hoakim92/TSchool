package com.tsystems.lims.dto;

import com.tsystems.lims.models.Prescription;
import com.tsystems.lims.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    String name;
    Integer id;

    public RoleDto(Role role){
        this.name = role.getName();
        this.id = role.getId();
    }

    public static List<RoleDto> getAsDto(List<Role> roles) {
        return roles.stream().map(RoleDto::new).collect(Collectors.toList());
    }
}
