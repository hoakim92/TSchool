package com.tsystems.lims.dao.impl;

import com.tsystems.lims.dao.common.AbstractHibernateDao;
import com.tsystems.lims.dao.interfaces.ICureDao;
import com.tsystems.lims.models.Cure;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public class CureDao extends AbstractHibernateDao<Cure> implements ICureDao {
    public CureDao(){
        setClazz(Cure.class);
    }

}
