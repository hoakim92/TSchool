package com.tsystems.lims.dao.interfaces;

import com.tsystems.lims.dao.common.IFindByName;
import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.models.User;

import java.util.List;

public interface IUserDao extends IOperations<User>, IFindByName<User> {
    public List<User> getUsersByName(String name);
}
