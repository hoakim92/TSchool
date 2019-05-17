package com.tsystems.lims.dao.impl;

import com.tsystems.lims.dao.common.AbstractHibernateDao;
import com.tsystems.lims.dao.interfaces.IPrescriptionDao;
import com.tsystems.lims.models.Prescription;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PrescriptionDao extends AbstractHibernateDao<Prescription> implements IPrescriptionDao {
    @PersistenceContext
    EntityManager entityManager;

    public PrescriptionDao() {
        setClazz(Prescription.class);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(Integer patientId) {
        Query query = entityManager.createQuery("from" + clazz.getName() + "where patient_id = :patientId");
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }
}
