package com.tsystems.lims.dao.interfaces;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.models.TherapyEvent;
import com.tsystems.lims.models.TherapyEventStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ITherapyEventDao extends IOperations<TherapyEvent> {
    public List<TherapyEvent> findAllByPeriod(LocalDateTime begin, LocalDateTime end);
    public List<TherapyEvent> findAllByPatients(List<Integer> patientIds);
    public List<TherapyEvent> findAllByPeriodAndPatients(LocalDateTime begin, LocalDateTime end, List<Integer>  patients);
    public List<TherapyEvent> findAllByPrescription(Integer prescriptionId);
    public List<TherapyEvent> findAllByPrescriptionAndStatus(Integer prescriptionId, List<TherapyEventStatus> statuses);

    public List<TherapyEvent> findAllByPeriod(LocalDateTime begin, LocalDateTime end, Integer offset, Integer count);
    public List<TherapyEvent> findAllByPatients(List<Integer> patientIds, Integer offset, Integer count);
    public List<TherapyEvent> findAllByPeriodAndPatients(LocalDateTime begin, LocalDateTime end, List<Integer>  patients, Integer offset, Integer count);
    public List<TherapyEvent> findAllByPrescription(Integer prescriptionId, Integer offset, Integer count);
    public List<TherapyEvent> findAllByPrescriptionAndStatus(Integer prescriptionId, List<TherapyEventStatus> statuses, Integer offset, Integer count);
}
