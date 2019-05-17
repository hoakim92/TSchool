package com.tsystems.lims.dao.impl;

import com.tsystems.lims.dao.common.AbstractHibernateDao;
import com.tsystems.lims.dao.interfaces.ITherapyEventDao;
import com.tsystems.lims.models.Prescription;
import com.tsystems.lims.models.TherapyEvent;
import com.tsystems.lims.models.TherapyEventStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class TherapyEventDao extends AbstractHibernateDao<TherapyEvent> implements ITherapyEventDao {
    public TherapyEventDao() {
        setClazz(TherapyEvent.class);
    }

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<TherapyEvent> findAllByPeriod(LocalDateTime begin, LocalDateTime end) {
        return findAllByPeriod(begin, end, null, null);
    }

    @Override
    public List<TherapyEvent> findAllByPatients(List<Integer> patientIds) {
        return findAllByPatients(patientIds, null, null);
    }

    @Override
    public List<TherapyEvent> findAllByPeriodAndPatients(LocalDateTime begin, LocalDateTime end, List<Integer> patients) {
        return findAllByPeriodAndPatients(begin, end, patients,null,null);
    }

    @Override
    public List<TherapyEvent> findAllByPrescription(Integer prescriptionId) {
        return findAllByPrescription(prescriptionId, null, null);
    }

    @Override
    public List<TherapyEvent> findAllByPrescriptionAndStatus(Integer prescriptionId, List<TherapyEventStatus> statuses) {
        return findAllByPrescriptionAndStatus(prescriptionId, statuses, null, null);
    }

    @Override
    public List<TherapyEvent> findAllByPeriod(LocalDateTime begin, LocalDateTime end, Integer offset, Integer count) {
        Query query = entityManager.createQuery("from " + clazz.getName() + " where date between :begin and :end");
        query.setParameter("begin", begin);
        query.setParameter("end", end);
        return getQueryResult(query, offset, count);
    }

    @Override
    public List<TherapyEvent> findAllByPeriodAndPatients(LocalDateTime begin, LocalDateTime end, List<Integer> patients, Integer offset, Integer count) {
        Query query = entityManager.createQuery("from " + clazz.getName() + " where date between :begin and :end and patient.id in (:patients)");
        query.setParameter("begin", begin);
        query.setParameter("end", end);
        query.setParameter("patients", patients);
        return getQueryResult(query, offset, count);
    }

    @Override
    public List<TherapyEvent> findAllByPatients(List<Integer> patients, Integer offset, Integer count) {
        Query query = entityManager.createQuery("from " + clazz.getName() + " where patient.id in (:patients)");
        query.setParameter("patients", patients);
        return getQueryResult(query, offset, count);
    }

    @Override
    public List<TherapyEvent> findAllByPrescription(Integer prescriptionId, Integer offset, Integer count) {
        Query query = entityManager.createQuery("from " + clazz.getName() + " where prescription.id = :id");
        query.setParameter("id", prescriptionId);
        return getQueryResult(query, offset, count);
    }

    @Override
    public List<TherapyEvent> findAllByPrescriptionAndStatus(Integer prescriptionId, List<TherapyEventStatus> statuses, Integer offset, Integer count) {
        Query query = entityManager.createQuery("from " + clazz.getName() + " where prescription.id = :id and status in (:statuses)");
        query.setParameter("id", prescriptionId);
        query.setParameter("statuses", statuses);
        return getQueryResult(query, offset, count);
    }

    public List<TherapyEvent> getQueryResult(Query query, Integer offset, Integer count){
        if (offset != null)
            query.setFirstResult(offset);
        if (count != null)
            query.setMaxResults(count);
        return query.getResultList();
    }




}
