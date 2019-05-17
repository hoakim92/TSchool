package com.tsystems.lims.service.interfaces;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dto.UserDto;
import com.tsystems.lims.models.User;
import com.tsystems.lims.service.common.AbstractHibernateService;
import com.tsystems.lims.service.common.IOperationsService;

import java.util.List;

public interface IUserService  {
    public List<UserDto> getUsers();
    public UserDto createOrUpdateUser(Integer id, String name, String password, Integer roleId);
    public UserDto getUser(Integer id);
}
