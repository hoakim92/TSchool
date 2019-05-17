package com.tsystems.lims.service.interfaces;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.dto.DoctorDto;
import com.tsystems.lims.models.Doctor;

import java.util.List;
import java.util.Map;

public interface IDoctorService {
    public List<DoctorDto> getDoctors(Map<String,String> parameters, Integer offset, Integer count);

    public List<DoctorDto> getDoctors();

    public DoctorDto saveOrUpdateDoctor(Integer id, String name);

    public DoctorDto getDoctor(Integer id);

    public void deleteDoctor(Integer id);
}
