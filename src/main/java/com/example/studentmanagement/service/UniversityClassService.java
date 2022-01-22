package com.example.studentmanagement.service;

import com.example.studentmanagement.dao.UniversityClassDao;
import com.example.studentmanagement.exceptions.UniversityClassInvalidException;
import com.example.studentmanagement.model.UniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class UniversityClassService {
    private UniversityClassDao universityClassDao;

    @Autowired
    public UniversityClassService(UniversityClassDao universityClassDao) {
        this.universityClassDao = universityClassDao;
    }

    public List<UniversityClass> getAllClasses() {
        return (List<UniversityClass>) universityClassDao.findAll();
    }

    public UniversityClass addClass(UniversityClass universityClass) {
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        if (universityClass.getYear() < currYear) {
            throw new UniversityClassInvalidException("Cannot add class in the past");
        }
        if (universityClass.getYear() > currYear + 1) {
            throw new UniversityClassInvalidException("Cannot add class in the far future");
        }
        return universityClassDao.save(universityClass);
    }
}
