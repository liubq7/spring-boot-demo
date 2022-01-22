package com.example.studentmanegement.api;

import com.example.studentmanegement.exceptions.StudentEmptyNameException;
import com.example.studentmanegement.exceptions.UniversityClassInvalidException;
import com.example.studentmanegement.model.Student;
import com.example.studentmanegement.model.UniversityClass;
import com.example.studentmanegement.service.UniversityClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/class")
public class UniversityClassController {
    private UniversityClassService universityClassService;

    @Autowired
    public UniversityClassController(UniversityClassService universityClassService) {
        this.universityClassService = universityClassService;
    }

    @GetMapping
    public List<UniversityClass> getAllClasses() {
        return universityClassService.getAllClasses();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addClass(@RequestBody UniversityClass universityClass) {
        try {
            UniversityClass addedUniversityClass = universityClassService.addClass(universityClass);
            return ResponseEntity.ok("Added " + addedUniversityClass.toString() + " successfully!");
        } catch (UniversityClassInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
