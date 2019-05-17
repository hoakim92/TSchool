package com.tsystems.lims.service.interfaces;

import com.tsystems.lims.dto.PrescriptionDto;
import com.tsystems.lims.models.PrescriptionPeriodType;
import com.tsystems.lims.models.TherapyEvent;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IPrescriptionServiceJMS {
    public List<PrescriptionDto> getPrescriptions();

    public List<PrescriptionDto> getPrescriptions(Map<String, String> parameters, int offset, int count);

    public PrescriptionDto getPrescription(Integer id);

    public List<PrescriptionDto> getPrescriptionsByPatientId(Integer patientId);

    public PrescriptionDto saveOrUpdatePrescription(Integer id, Integer patientId, Integer cureId, LocalDate dateOfBegin, LocalDate dateOfEnd, String doze, PrescriptionPeriodType periodType, String[] dates, Integer[] daysOfWeek);

    public void cancelPlannedEvents(Integer id);

    public void deletePrescription(Integer id);
}
