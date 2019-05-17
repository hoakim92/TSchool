package com.tsystems.lims;

import com.tsystems.lims.config.MvcConfigTest;
import com.tsystems.lims.dao.impl.DiagnosisDao;
import com.tsystems.lims.dao.interfaces.ICureDao;
import com.tsystems.lims.dao.interfaces.IDiagnosisDao;
import com.tsystems.lims.dao.interfaces.IDoctorDao;
import com.tsystems.lims.dao.interfaces.IPatientDao;
import com.tsystems.lims.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static com.tsystems.lims.util.DataGenerateUtils.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfigTest.class})
@WebAppConfiguration
public class GenerateData {
    @Autowired
    ICureDao cureDao;

    @Autowired
    IDoctorDao doctorDao;

    @Autowired
    IDiagnosisDao diagnosisDao;

    @Autowired
    IPatientDao patientDao;

    @Test
    @Transactional
    public void generateAll() throws IOException {
        addCures("C:\\cures.txt");
        addDiagnoses("C:\\diagnoses.txt");
        addDoctors("C:\\doctors.txt");
        addPatients("C:\\names.txt", "C:\\lastNames.txt", 100);
    }

    @Test
    @Transactional
    public void addCures(String path) throws IOException {
        getFilesAsList(path).forEach(d -> cureDao.create(Cure.builder().name(d).type(CureType.DRUG).build()));
    }

    @Test
    @Transactional
    public void addDiagnoses(String path) throws IOException {
        DiagnosisDao diagnosisDao = new DiagnosisDao();
        getFilesAsList(path).forEach(d -> diagnosisDao.create(Diagnosis.builder().name(d).build()));
    }

    @Test
    @Transactional
    public void addPatients(String namesPath, String lastNamesPath, int size) throws IOException {
        List<Doctor> doctors = doctorDao.findAll();
        List<Diagnosis> diagnoses = diagnosisDao.findAll();
        List<String> names = getFilesAsList(namesPath);
        List<String> lastNames = getFilesAsList(lastNamesPath);
        for (int i = 0; i < size; i++) {
            patientDao.create(Patient.builder()
                    .name(prepareName(getRandomElementFromList(names)) + " " + prepareName(getRandomElementFromList(lastNames)))
                    .insurance(generateInsurance())
                    .doctor(getRandomElementFromList(doctors))
                    .diagnosisSet(getRandomSetOfElementsFromList(diagnoses))
                    .status(PatientStatus.TREAT)
                    .build());
        }
    }

    @Test
    @Transactional
    public void addDoctors(String path) throws IOException {
        getFilesAsList(path).forEach(d -> doctorDao.create(Doctor.builder().name(d).build()));
    }
}
