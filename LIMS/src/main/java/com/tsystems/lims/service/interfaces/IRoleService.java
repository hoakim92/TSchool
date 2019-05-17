package com.tsystems.lims.service.interfaces;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.models.Role;
import com.tsystems.lims.service.common.IOperationsService;

import java.util.List;

public interface IRoleService<D>  {
    public List<D> getRoles();
    public D getRole(Integer id);
}
