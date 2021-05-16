package com.rappala.sis.controller;

import com.rappala.sis.dto.StudentDTO;
import com.rappala.sis.entities.Student;
import com.rappala.sis.exception.StudentNotExistsException;
import com.rappala.sis.repositories.CourseRepository;
import com.rappala.sis.repositories.StudentRepository;
import com.rappala.sis.response.StudentGetAllResponse;
import com.rappala.sis.response.StudentResponse;
import com.rappala.sis.response.StudentUpdateResponse;
import com.rappala.sis.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(path="/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StudentService studentService;

    @PostMapping(path="/addstudent")
    public @ResponseBody
    StudentResponse addStudent(@RequestBody StudentDTO studentDto){
        return studentService.addStudent(studentDto);
    }

    @GetMapping(path="allstudents")
    public @ResponseBody List<StudentGetAllResponse> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PutMapping(path="/updatestudent")
    public @ResponseBody
    StudentUpdateResponse updateStudent(@RequestBody StudentDTO studentDto){
        return studentService.updateStudent(studentDto);
    }

    @DeleteMapping(path="/deletestudent/{id}")
    public @ResponseBody void deleteStudent(@PathVariable final UUID id){
        Optional<Student> extractStudent = studentRepository.findById(id);
        if(extractStudent.isPresent()) {
            studentRepository.deleteById(id);
        }
        else{
            throw new StudentNotExistsException();
        }
    }

}

