package com.tsystems.lims.dao.impl;

import com.tsystems.lims.dao.common.AbstractHibernateDao;
import com.tsystems.lims.dao.interfaces.IPatientDao;
import com.tsystems.lims.models.Patient;
import org.springframework.stereotype.Repository;

@Repository
public class PatientDao  extends AbstractHibernateDao<Patient> implements IPatientDao {
    public PatientDao(){
        setClazz(Patient.class);
    }
}
