package com.vivek.security.student.controller;

import com.vivek.security.student.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class studentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "James Bond"),
            new Student(2, "Maria Jones"),
            new Student(3, "Anna Smith")
    );

    @GetMapping("/{studentId}")
    public Student getStidents(@PathVariable("studentId") int studentId){
        return STUDENTS.stream().filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(()->new IllegalStateException("Student "+studentId +" does not exists."));
    }
}
