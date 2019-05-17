package com.tsystems.lims.dao.interfaces;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dto.PrescriptionDto;
import com.tsystems.lims.models.Prescription;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

public interface IPrescriptionDao extends IOperations<Prescription> {
    public List<Prescription> getPrescriptionsByPatientId(Integer patientId);
}
