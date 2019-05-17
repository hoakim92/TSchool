package com.tsystems.lims.service.impl;

import com.tsystems.lims.service.common.AbstractHibernateService;
import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dao.interfaces.IDoctorDao;
import com.tsystems.lims.dto.DoctorDto;
import com.tsystems.lims.models.Doctor;
import com.tsystems.lims.service.interfaces.IDoctorService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.tsystems.lims.dto.DoctorDto.getAsDto;
import static com.tsystems.lims.util.DataGenerateUtils.getFilesAsList;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class DoctorService extends AbstractHibernateService<Doctor> implements IDoctorService {
    @Autowired
    IDoctorDao dao;

    public DoctorDto saveOrUpdateDoctor(Integer id, String name) {
        Doctor doctor = null;
        if (id == null) {
            doctor = Doctor.builder().name(name).build();
            dao.create(doctor);
        } else {
            doctor = Doctor.builder().id(id).name(name).build();
            dao.update(doctor);
        }
        return getDoctor(doctor.getId());
    }

    @Override
    public List<DoctorDto> getDoctors(Map<String, String> parameters, Integer offset, Integer count) {
        return getAsDto(dao.findAll(parameters, offset, count));
    }

    @Override
    public List<DoctorDto> getDoctors() {
        return getAsDto(dao.findAll());
    }

    @Override
    public DoctorDto getDoctor(Integer id) {
        Doctor doctor = findOne(id);
        if (doctor == null)
            throw new EntityNotFoundException("Not found doctor with id = " + id);
        return new DoctorDto(doctor);
    }

    @Override
    public void deleteDoctor(Integer id) {
        dao.deleteById(id);
    }

    @Override
    protected IOperations<Doctor> getDao() {
        return dao;
    }
}