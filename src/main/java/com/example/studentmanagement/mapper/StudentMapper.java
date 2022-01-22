package com.example.studentmanagement.mapper;

import com.example.studentmanagement.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student WHERE name LIKE #{str}")
    List<Student> getStudentsWithName(@Param("str") String str);

    @Select("SELECT * FROM student WHERE university_class_id IN" +
        "(SELECT id FROM university_class WHERE year = #{year} AND number = #{number})")
    List<Student> getStudentsInClass(@Param("year") Integer year, @Param("number") Integer number);
}
