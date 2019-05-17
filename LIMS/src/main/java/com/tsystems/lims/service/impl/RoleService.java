package com.tsystems.lims.service.impl;

import com.tsystems.lims.dto.PrescriptionDto;
import com.tsystems.lims.models.Prescription;
import com.tsystems.lims.service.common.AbstractHibernateService;
import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dao.interfaces.IRoleDao;
import com.tsystems.lims.dto.RoleDto;
import com.tsystems.lims.models.Role;
import com.tsystems.lims.service.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleService extends AbstractHibernateService<Role> implements IRoleService<RoleDto> {
    @Autowired
    IRoleDao dao;

    public List<RoleDto> getRoles() {
        return RoleDto.getAsDto(dao.findAll());
    }

    public RoleDto getRole(Integer id) {
        Role role = findOne(id);
        if (role == null)
            throw new EntityNotFoundException("Not found role with id = " + id);
        return new RoleDto(findOne(id));
    }

    @Override
    protected IOperations<Role> getDao() {
        return dao;
    }
}
