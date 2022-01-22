package com.example.studentmanagement.api;

import com.example.studentmanagement.exceptions.UniversityClassInvalidException;
import com.example.studentmanagement.model.UniversityClass;
import com.example.studentmanagement.service.UniversityClassService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/class")
public class UniversityClassController {
    private final UniversityClassService universityClassService;

    @Autowired
    public UniversityClassController(UniversityClassService universityClassService) {
        this.universityClassService = universityClassService;
    }

    @GetMapping
    @RequiresPermissions("class:read")
    public List<UniversityClass> getAllClasses() {
        return universityClassService.getAllClasses();
    }

    @PostMapping("/add")
    @RequiresPermissions("class:write")
    public ResponseEntity<String> addClass(@RequestBody UniversityClass universityClass) {
        try {
            UniversityClass addedUniversityClass = universityClassService.addClass(universityClass);
            return ResponseEntity.ok("Added " + addedUniversityClass.toString() + " successfully!");
        } catch (UniversityClassInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
