package com.tsystems.lims.service.impl;

import com.tsystems.lims.dto.PrescriptionDto;
import com.tsystems.lims.models.PrescriptionPeriodType;
import com.tsystems.lims.models.TherapyEvent;
import com.tsystems.lims.service.common.JmsService;
import com.tsystems.lims.service.interfaces.IPrescriptionService;
import com.tsystems.lims.service.interfaces.IPrescriptionServiceJMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class PrescriptionServiceJMS extends JmsService implements IPrescriptionServiceJMS {
    @Autowired
    IPrescriptionService prescriptionService;

    @Override
    public List<PrescriptionDto> getPrescriptions() {
        return prescriptionService.getPrescriptions();
    }

    @Override
    public List<PrescriptionDto> getPrescriptions(Map<String, String> parameters, int offset, int count) {
        return prescriptionService.getPrescriptions(parameters, offset, count);
    }

    @Override
    public PrescriptionDto getPrescription(Integer id) {
        return prescriptionService.getPrescription(id);
    }

    @Override
    public List<PrescriptionDto> getPrescriptionsByPatientId(Integer patientId) {
        return prescriptionService.getPrescriptionsByPatientId(patientId);
    }

    @Override
    public PrescriptionDto saveOrUpdatePrescription(Integer id, Integer patientId, Integer cureId, LocalDate dateOfBegin, LocalDate dateOfEnd, String doze, PrescriptionPeriodType periodType, String[] dates, Integer[] daysOfWeek) {
        PrescriptionDto prescriptionDto =  prescriptionService.saveOrUpdatePrescription(id,patientId,cureId,dateOfBegin,dateOfEnd,doze,periodType,dates,daysOfWeek);
        sendJmsNotification();
        return prescriptionDto;
    }

    @Override
    public void cancelPlannedEvents(Integer id) {
        prescriptionService.cancelPlannedEvents(id);
        sendJmsNotification();
    }

    @Override
    public void deletePrescription(Integer id) {
        prescriptionService.deletePrescription(id);
        sendJmsNotification();
    }
}
