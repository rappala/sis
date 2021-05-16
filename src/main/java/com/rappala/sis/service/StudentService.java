package com.rappala.sis.service;


import com.rappala.sis.dto.StudentDTO;
import com.rappala.sis.response.StudentGetAllResponse;
import com.rappala.sis.response.StudentResponse;
import com.rappala.sis.response.StudentUpdateResponse;

import java.util.List;

public interface StudentService {
    public StudentResponse addStudent(StudentDTO studentDto);
    public List<StudentGetAllResponse> getAllStudents();
    public StudentUpdateResponse updateStudent(StudentDTO studentDto);
}
