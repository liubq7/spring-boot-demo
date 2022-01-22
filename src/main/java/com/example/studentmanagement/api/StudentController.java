package com.example.studentmanegement.api;

import com.example.studentmanegement.exceptions.StudentEmptyNameException;
import com.example.studentmanegement.exceptions.StudentNonExistException;
import com.example.studentmanegement.exceptions.UniversityClassInvalidException;
import com.example.studentmanegement.model.Student;
import com.example.studentmanegement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudent() {
        return studentService.getAllStudents();
    }

    @GetMapping("/name")
    // localhost:8080/api/student/name?name=tom
    public List<Student> getStudentsByName(@RequestParam String name) {
        return studentService.getStudentsByName(name);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.addStudent(student);
            return ResponseEntity.ok("Register student " + savedStudent.toString() + " successfully!");
        } catch (StudentEmptyNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/assignclass/{sid}/{cid}")
    public ResponseEntity<String> assignClass(@PathVariable("sid") Long studentId, @PathVariable("cid") Long classId) {
        try {
            Student student = studentService.assignClass(studentId, classId);
            return ResponseEntity.ok("Assigned class: " + student.toString());
        } catch (StudentNonExistException | UniversityClassInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
