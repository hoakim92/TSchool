package com.tsystems.lims.service.impl;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dao.interfaces.IDiagnosisDao;
import com.tsystems.lims.dto.CureDto;
import com.tsystems.lims.dto.DiagnosisDto;
import com.tsystems.lims.models.Diagnosis;
import com.tsystems.lims.service.common.AbstractHibernateService;
import com.tsystems.lims.service.interfaces.IDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

import static com.tsystems.lims.dto.DiagnosisDto.getAsDto;

@Service
@Transactional
public class DiagnosisService extends AbstractHibernateService<Diagnosis> implements IDiagnosisService {
    @Autowired
    IDiagnosisDao dao;

    @Override
    protected IOperations<Diagnosis> getDao() {
        return dao;
    }

    public DiagnosisDto saveOrUpdateDiagnosis(Integer id, String name, String description) {
        Diagnosis diagnosis = null;
        if (id == null) {
            diagnosis = Diagnosis.builder().description(description).name(name).build();
            dao.create(diagnosis);
        } else {
            diagnosis = Diagnosis.builder().id(id).description(description).name(name).build();
            dao.update(diagnosis);
        }
        return getDiagnosis(diagnosis.getId());
    }

    @Override
    public List<DiagnosisDto> getDiagnoses() {
        return getDiagnoses(null, null, null);
    }

    @Override
    public List<DiagnosisDto> getDiagnoses(Map<String, String> parameters, Integer offset, Integer count) {
            return getAsDto(dao.findAll(parameters, offset, count));
    }

    @Override
    public void deleteDiagnosis(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public DiagnosisDto getDiagnosis(Integer id) {
        Diagnosis diagnosis = dao.findOne(id);
        if (diagnosis == null)
            throw new EntityNotFoundException("Not found diagnosis wit id = "+id);
        return new DiagnosisDto(dao.findOne(id));
    }

    @Override
    public List<Diagnosis> findAll(Integer offset, Integer count) {
        return null;
    }
}