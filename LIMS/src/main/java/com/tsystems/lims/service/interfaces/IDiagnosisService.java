package com.tsystems.lims.service.interfaces;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dto.DiagnosisDto;
import com.tsystems.lims.models.Diagnosis;

import java.util.List;
import java.util.Map;

public interface IDiagnosisService {
    public List<DiagnosisDto> getDiagnoses();

    public DiagnosisDto saveOrUpdateDiagnosis(Integer id, String name, String description);

    public DiagnosisDto getDiagnosis(Integer id);

    public List<DiagnosisDto> getDiagnoses(Map<String, String> parameters, Integer offset, Integer count);

    public void deleteDiagnosis(Integer id);
}
