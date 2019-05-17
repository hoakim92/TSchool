package com.tsystems.lims.controller;

import com.tsystems.lims.dto.RoleDto;
import com.tsystems.lims.dto.UserDto;
import com.tsystems.lims.service.interfaces.IRoleService;
import com.tsystems.lims.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
@Secured("ROLE_ADMIN")
public class UserController {
    @Autowired
    IUserService userService;

    @Autowired
    IRoleService<RoleDto> roleService;

    @PostMapping("/users")
    public String postUser(Model model, @RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "password", required = false) String password, @RequestParam(value = "roleId", required = false) Integer roleId) {
        UserDto userDto = userService.createOrUpdateUser(id, username, password, roleId);
        return "redirect:users/" + userDto.getId();
    }

    @GetMapping("users/createuser")
    public String createUser(Model model) {
        model.addAttribute("roles", roleService.getRoles());
        return "user";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "usersList";
    }

    @RequestMapping(value = "/users/{id}", method = GET)
    public String getUser(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getRoles());
        return "user";
    }

}    

 
