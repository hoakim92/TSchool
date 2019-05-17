package com.tsystems.lims.service.interfaces;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dto.CureDto;
import com.tsystems.lims.dto.PatientDto;
import com.tsystems.lims.models.Cure;

import java.util.List;
import java.util.Map;

public interface ICureService {
    public CureDto saveOrUpdateCure(Integer id, String name, String type);

    public List<CureDto> getCures();

    public List<CureDto> getCures(Map<String, String> parameters, Integer offset, Integer count);

    public CureDto getCure(Integer id);

    public void deleteCure(Integer id);
}
