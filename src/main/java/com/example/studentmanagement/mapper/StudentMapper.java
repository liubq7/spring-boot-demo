package com.example.studentmanagement.mapper;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.UniversityClass;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student WHERE name LIKE #{str}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "universityClass", column = "university_class_id", one = @One(select = "selectClass"))
    })
    List<Student> getStudentsWithName(@Param("str") String str);

    @Select("SELECT * FROM university_class WHERE id = #{id}")
    UniversityClass selectClass(@Param("id") Long id);

    @Select("SELECT * FROM student WHERE university_class_id IN" +
            "(SELECT id FROM university_class WHERE year = #{year} AND number = #{number})")
    List<Student> getStudentsInClass(@Param("year") Integer year, @Param("number") Integer number);
}
