package com.tsystems.lims.dao.impl;

import com.tsystems.lims.dao.common.AbstractHibernateDao;
import com.tsystems.lims.dao.interfaces.IDiagnosisDao;
import com.tsystems.lims.models.Diagnosis;
import org.springframework.stereotype.Repository;

@Repository
public class DiagnosisDao extends AbstractHibernateDao<Diagnosis> implements IDiagnosisDao {
    public DiagnosisDao(){
        setClazz(Diagnosis.class);
    }

}
