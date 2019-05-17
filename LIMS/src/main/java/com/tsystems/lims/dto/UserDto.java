package com.tsystems.lims.dto;

import com.tsystems.lims.models.Role;
import com.tsystems.lims.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    Integer id;
    String username;
    RoleStructure role;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = new RoleStructure(user.getRole());
    }

    @Data
    public class RoleStructure {
        Integer id;
        String name;

        public RoleStructure(Role role) {
            this.id = role.getId();
            this.name = role.getName();
        }
    }

}
