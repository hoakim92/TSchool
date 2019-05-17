package com.tsystems.lims.service.impl;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dao.interfaces.IRoleDao;
import com.tsystems.lims.dao.interfaces.IUserDao;
import com.tsystems.lims.dto.PrescriptionDto;
import com.tsystems.lims.dto.UserDto;
import com.tsystems.lims.models.Prescription;
import com.tsystems.lims.models.User;
import com.tsystems.lims.service.common.AbstractHibernateService;
import com.tsystems.lims.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService extends AbstractHibernateService<User> implements IUserService {
    @Autowired
    IUserDao dao;

    @Autowired
    IRoleDao roleDao;

    public UserDto createOrUpdateUser(Integer id, String username, String password, Integer roleId) {
        User user = null;
        password = new BCryptPasswordEncoder().encode(password);
        List<User> users = dao.getUsersByName(username);
        if (id == null) {
            user = User.builder().username(username).password(password).role(roleDao.findOne(roleId)).build();
            if (!(users.size() > 0))
                dao.create(user);
            else
                throw new UnsupportedOperationException("You already have user with such name");
        } else {
            user = User.builder().id(id).username(username).password(password).role(roleDao.findOne(roleId)).build();
            User existingUser = dao.findOne(id);
            if (users.size() < 2) {
                if (users.size()  == 0)
                    dao.update(user);
                if (users.size() == 1 && existingUser.getUsername().equals(username))
                    dao.update(user);
                else
                    throw new UnsupportedOperationException("You already have user with such name");
            } else
                throw new UnsupportedOperationException("You already have user with such name");
            dao.update(user);
        }
        return getUser(user.getId());
    }

    @Override
    public List<UserDto> getUsers() {
        return dao.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Integer id) {
        User user = findOne(id);
        if (user == null)
            throw new EntityNotFoundException("Not found user with id = " + id);
        return new UserDto(findOne(id));
    }

    @Override
    protected IOperations<User> getDao() {
        return dao;
    }
}