package com.tsystems.lims.service.impl;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dao.interfaces.ICureDao;
import com.tsystems.lims.dto.CureDto;
import com.tsystems.lims.dto.PatientDto;
import com.tsystems.lims.models.Cure;
import com.tsystems.lims.models.CureType;
import com.tsystems.lims.models.User;
import com.tsystems.lims.service.common.AbstractHibernateService;
import com.tsystems.lims.service.interfaces.ICureService;
import com.tsystems.lims.util.DataGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.tsystems.lims.dto.CureDto.getAsDto;

@Service
@Transactional
public class CureService extends AbstractHibernateService<Cure> implements ICureService {
    @Autowired
    ICureDao dao;

    public CureDto saveOrUpdateCure(Integer id, String name, String type) {
        Cure cure = null;
        if (id == null) {
            cure = Cure.builder().name(name).type(CureType.valueOf(type)).build();
            dao.create(cure);
        } else {
            cure = Cure.builder().id(id).name(name).type(CureType.valueOf(type)).build();
            dao.update(cure);
        }
        return getCure(cure.getId());
    }

    @Override
    protected IOperations<Cure> getDao() {
        return dao;
    }

    @Override
    public List<CureDto> getCures() {
        return getCures(null, null, null);
    }

    @Override
    public List<CureDto> getCures(Map<String, String> parameters, Integer offset, Integer count) {
        return getAsDto(dao.findAll(parameters, offset, count));
    }

    @Override
    public CureDto getCure(Integer id) {
        Cure cure = findOne(id);
        if (cure == null)
            throw new EntityNotFoundException("Not found cure with id = " + id);
        return new CureDto(findOne(id));
    }

    @Override
    public void deleteCure(Integer id) {
        dao.deleteById(id);
    }

}
