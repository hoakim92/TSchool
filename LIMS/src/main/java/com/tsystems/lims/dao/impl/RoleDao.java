package com.tsystems.lims.dao.impl;

import com.tsystems.lims.dao.common.AbstractHibernateDao;
import com.tsystems.lims.dao.interfaces.IRoleDao;
import com.tsystems.lims.models.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao  extends AbstractHibernateDao<Role> implements IRoleDao {
    public RoleDao(){
        setClazz(Role.class);
    }
}
