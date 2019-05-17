package com.tsystems.lims.dao.impl;

import com.tsystems.lims.dao.common.AbstractHibernateDao;
import com.tsystems.lims.dao.interfaces.IDoctorDao;
import com.tsystems.lims.models.Doctor;
import org.springframework.stereotype.Repository;

@Repository
public class DoctorDao extends AbstractHibernateDao<Doctor> implements IDoctorDao {
    public DoctorDao(){
        setClazz(Doctor.class);
    }
}
