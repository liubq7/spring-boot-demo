package com.example.studentmanegement.service;

import com.example.studentmanegement.dao.StudentDao;
import com.example.studentmanegement.dao.UniversityClassDao;
import com.example.studentmanegement.exceptions.StudentEmptyNameException;
import com.example.studentmanegement.exceptions.StudentNonExistException;
import com.example.studentmanegement.exceptions.UniversityClassInvalidException;
import com.example.studentmanegement.model.Student;
import com.example.studentmanegement.model.UniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentDao studentDao;
    private UniversityClassDao universityClassDao;

    @Autowired
    public StudentService(StudentDao studentDao, UniversityClassDao universityClassDao) {
        this.studentDao = studentDao;
        this.universityClassDao = universityClassDao;
    }

    public Student addStudent(Student student) {
        if (student.getName().isEmpty()) {
            throw new StudentEmptyNameException("Student name cannot be empty!");
        }
        return studentDao.save(student);
    }

    public Student updateStudent(Student student) {
        if (student.getId() == null || !studentDao.existsById(student.getId())) {
            throw new StudentNonExistException("Cannot find student id!");
        }
        return studentDao.save(student);
    }

    public Student assignClass(Long studentId, Long classId) {
        if (!studentDao.existsById(studentId)) {
            throw new StudentNonExistException("Student id doesn't exist");
        }
        if (!universityClassDao.existsById(classId)) {
            throw new UniversityClassInvalidException("Cannot find class id");
        }
        Student student = getStudentById(studentId).get();
        UniversityClass universityClass = universityClassDao.findById(classId).get();

        student.setUniversityClass(universityClass);
        return studentDao.save(student);
    }

    public List<Student> getAllStudents() {
        return (List<Student>) studentDao.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentDao.findById(id);
    }

    public List<Student> getStudentsByName(String name) {
        return studentDao.findByName(name);
    }
}
