package com.example.studentmanagement.api;

import com.example.studentmanagement.exceptions.StudentEmptyNameException;
import com.example.studentmanagement.exceptions.StudentNonExistException;
import com.example.studentmanagement.exceptions.UniversityClassInvalidException;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @RequiresPermissions("student:read")
    public List<Student> getAllStudent() {
        return studentService.getAllStudents();
    }

    @PutMapping
    @RequiresPermissions("student:write")
    public ResponseEntity<String> updateStudent(@RequestBody Student student) {
        try {
            studentService.updateStudent(student);
            return ResponseEntity.ok("Updated student successfully");
        } catch (StudentNonExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/name")
    @RequiresPermissions("student:read")
    // localhost:8080/api/student/name?name=tom
    public List<Student> getStudentsByName(@RequestParam String name) {
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/with_name")
    @RequiresPermissions("student:read")
    // localhost:8080/api/student/with_name?str=t
    public List<Student> getStudentsWithName(@RequestParam String str) {
        return studentService.getStudentsWithName(str);
    }

    @GetMapping("/class")
    @RequiresPermissions("student:read")
    // localhost:8080/api/student/class?year=2022&number=1
    public List<Student> getStudentsInClass(@RequestParam Integer year, @RequestParam Integer number) {
        return studentService.getStudentsInClass(year, number);
    }

    @PostMapping("/register")
    @RequiresPermissions("student:write")
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.addStudent(student);
            return ResponseEntity.ok("Register student " + savedStudent.toString() + " successfully!");
        } catch (StudentEmptyNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/assignclass/{sid}/{cid}")
    @RequiresPermissions("student:write")
    public ResponseEntity<String> assignClass(@PathVariable("sid") Long studentId, @PathVariable("cid") Long classId) {
        try {
            Student student = studentService.assignClass(studentId, classId);
            return ResponseEntity.ok("Assigned class: " + student.toString());
        } catch (StudentNonExistException | UniversityClassInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @RequiresPermissions("student:write")
    public ResponseEntity<String> deleteStudentById(@PathVariable("id") Long studentId) {
        try {
            studentService.deleteStudentById(studentId);
            return ResponseEntity.ok("Delete student successfully");
        } catch (StudentNonExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
