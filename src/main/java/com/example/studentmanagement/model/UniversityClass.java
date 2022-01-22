package com.example.studentmanagement.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "university_class")
public class UniversityClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer number;

    @OneToMany(mappedBy = "universityClass")
    private List<Student> students;

    public UniversityClass(Long id, Integer year, Integer number) {
        this.id = id;
        this.year = year;
        this.number = number;
    }

    public UniversityClass() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Class ID: " + getId() + " Class: " + getYear() + ", " + getNumber();
    }
}
