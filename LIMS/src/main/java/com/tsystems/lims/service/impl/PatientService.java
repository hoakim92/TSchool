package com.tsystems.lims.service.impl;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dao.interfaces.IDiagnosisDao;
import com.tsystems.lims.dao.interfaces.IDoctorDao;
import com.tsystems.lims.dao.interfaces.IPatientDao;
import com.tsystems.lims.dto.PatientDto;
import com.tsystems.lims.models.*;
import com.tsystems.lims.service.common.AbstractHibernateService;
import com.tsystems.lims.service.interfaces.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tsystems.lims.dto.PatientDto.getAsDto;

@Service
@Transactional
public class PatientService extends AbstractHibernateService<Patient> implements IPatientService {
    @Autowired
    IPatientDao dao;

    @Autowired
    IDoctorDao doctorDao;

    @Autowired
    IDiagnosisDao diagnosisDao;

    @Override
    public PatientDto saveOrUpdatePatient(Integer id, Integer doctorId, Integer[] diagnosisIds, String name, PatientGender gender, LocalDate dateOfBirth, String insurance, String email, String status) {
        Patient patient = null;
        Doctor doctor = doctorDao.findOne(doctorId);
        Set<Diagnosis> diagnosesSet = null;
        if (diagnosisIds != null)
            diagnosesSet = Arrays.stream(diagnosisIds).map(d -> diagnosisDao.findOne(d)).collect(Collectors.toSet());
        Patient.PatientBuilder builder = Patient.builder();
        if (id == null) {
            patient = builder.name(name).gender(gender).dateOfBirth(dateOfBirth).insurance(insurance).email(email).doctor(doctor).diagnosisSet(diagnosesSet).status(PatientStatus.valueOf(status)).build();
            if (email != null) {
                if (dao.findAll().stream().noneMatch(p -> {
                    if (p.getEmail() != null)
                        return p.getEmail().equals(email);
                    else
                        return false;
                })) {
                    dao.create(patient);
                } else {
                    throw new UnsupportedOperationException("You already have user with such email");
                }
            }
        } else {
            patient = builder.id(id).name(name).gender(gender).dateOfBirth(dateOfBirth).insurance(insurance).email(email).doctor(doctor).diagnosisSet(diagnosesSet).status(PatientStatus.valueOf(status)).build();
            if (email != null) {
                if (dao.findAll().stream().filter(p -> (p.getEmail() != null) && (p.getEmail().equals(email))).count() == 0)
                    dao.update(patient);

                if (dao.findAll().stream().filter(p -> (p.getEmail() != null) && (p.getEmail().equals(email))).count() == 1) {
                    if (dao.findOne(id).getEmail() != null && dao.findOne(id).getEmail().equals(email))
                        dao.update(patient);
                    else {
                        throw new UnsupportedOperationException("You already have user with such email");
                    }
                }
                if (dao.findAll().stream().filter(p -> (p.getEmail() != null) && (p.getEmail().equals(email))).count() > 1)
                    throw new UnsupportedOperationException("You already have user with such email");
            }
        }
        return new PatientDto(findOne(patient.getId()));
    }

    @Override
    public List<PatientDto> getPatients(Map<String, String> parameters, Integer offset, Integer count) {
        return getAsDto(dao.findAll(parameters, offset, count));
    }

    @Override
    public List<PatientDto> getPatients() {
        return getAsDto(dao.findAll());
    }

    @Override
    public PatientDto getPatient(Integer id) {
        Patient patient = dao.findOne(id);
        if (patient == null)
            throw new EntityNotFoundException("Not found patient with id = " + id);
        return new PatientDto(findOne(id));
    }

    @Override
    public void deletePatient(Integer id) {
        dao.deleteById(id);
    }

    @Override
    protected IOperations<Patient> getDao() {
        return dao;
    }


}