package com.rappala.sis.service;

import com.rappala.sis.dto.StudentDTO;
import com.rappala.sis.entities.Address;
import com.rappala.sis.entities.Courses;
import com.rappala.sis.entities.Student;
import com.rappala.sis.exception.AddressNotFoundException;
import com.rappala.sis.exception.CourseDoesNotExistsException;
import com.rappala.sis.exception.StudentNotExistsException;
import com.rappala.sis.repositories.AddressRepository;
import com.rappala.sis.repositories.CourseRepository;
import com.rappala.sis.repositories.StudentRepository;
import com.rappala.sis.response.StudentGetAllResponse;
import com.rappala.sis.response.StudentResponse;
import com.rappala.sis.response.StudentUpdateResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StudentResponse addStudent(StudentDTO studentDto) {

        Student student = modelMapper.map(studentDto, Student.class);
        List<Courses> coursesList = student.getCourses();
        List<Courses> updatedCourseList = new ArrayList<>();
        // TODO use streams, it makes below logic in one line
        for(Courses course:coursesList){
            String courseName = course.getName();
            // TODO use one network call instead of two
            if(courseRepository.existsByName(courseName)){
                Courses existingCourse = courseRepository.findByName(courseName);
                updatedCourseList.add(existingCourse);
            }
            else{
                updatedCourseList.add(course);
            }
        }
        student.setCourses(updatedCourseList);
        Student newStudent = studentRepository.save(student);
        System.out.println("add student successful");
        return mapStudentToDto(newStudent);
    }

    @Override
    public List<StudentGetAllResponse> getAllStudents() {
        List<StudentGetAllResponse> listOfStudents = new ArrayList<>();
        Iterable<Student> extractAllStudents =  studentRepository.findAll();
        Iterator<Student> studentIterator = extractAllStudents.iterator();
        while(studentIterator.hasNext()){
            Student extractStudent = studentIterator.next();
            StudentGetAllResponse studentGetAllResponse = modelMapper.map(extractStudent,StudentGetAllResponse.class);
            listOfStudents.add(studentGetAllResponse);
        }
        return listOfStudents;
    }
    public StudentUpdateResponse updateStudent(StudentDTO studentDto){
        Student updateStudent = modelMapper.map(studentDto,Student.class);
        UUID id= updateStudent.getStudentId();
        Optional<Student> existsOldStudent = studentRepository.findById(id);
        if(!existsOldStudent.isPresent()) {
            //throw student not found exception

            throw new StudentNotExistsException();
        }
        Student oldStudent = existsOldStudent.get();

        oldStudent.setStudentName(updateStudent.getStudentName());
        oldStudent.setMobileNumber(updateStudent.getMobileNumber());
        List<Courses> updateStudentCourses = updateStudent.getCourses();
        List<Courses> updatedCourseList = new ArrayList<>();
        for(Courses updateStudentCourse:updateStudentCourses){
            UUID courseId = updateStudentCourse.getId();
            if(courseRepository.existsById(courseId)){
                Optional<Courses> oldCourse = courseRepository.findById(courseId);
                Courses retrievedOldCourse = oldCourse.get();
                updatedCourseList.add(retrievedOldCourse);
            }
            else{
                throw new CourseDoesNotExistsException();
            }
        }
        oldStudent.setCourses(updatedCourseList);
        List<Address> updateStudentAddresses = updateStudent.getAddress();
        for(Address updateStudentAddress:updateStudentAddresses){
            UUID addressId = updateStudentAddress.getAddressId();
            if(addressRepository.existsByAddressId(addressId)){
                Optional<Address> oldAddress = addressRepository.findById(addressId);
                Address retrievedOldAddress =oldAddress.get();
                retrievedOldAddress.setStreetAddress(updateStudentAddress.getStreetAddress());
            }
            else{
                throw new AddressNotFoundException();
            }
        }
        Student savedStudent = studentRepository.save(oldStudent);
        return modelMapper.map(savedStudent,StudentUpdateResponse.class);
    }

    public StudentResponse mapStudentToDto(Student student){
        StudentResponse studentCreateResponse = new StudentResponse();
        studentCreateResponse.setStudentId(student.getStudentId());
        // comments: use right names
        // TODO use streams to reduce the number of lines
        List<Courses> course = student.getCourses();
        List<String> courseIds = new ArrayList<>();
        List<Address> address = student.getAddress();
        List<String> addressIds = new ArrayList<>();
        for(Courses enrolledCourse:course){
            String courseId = enrolledCourse.getId().toString();
            courseIds.add(courseId);
        }
        studentCreateResponse.setCourseId(courseIds);
        for(Address getAddress:address){
            String addressId = getAddress.getAddressId().toString();
            addressIds.add(addressId);
        }
        studentCreateResponse.setAddressId(addressIds);
        return studentCreateResponse;
    }
}

