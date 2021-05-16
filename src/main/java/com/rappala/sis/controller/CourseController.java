package com.rappala.sis.controller;

import com.rappala.sis.dto.CoursePatchDTO;
import com.rappala.sis.entities.Courses;
import com.rappala.sis.exception.CourseDoesNotExistsException;
import com.rappala.sis.exception.CourseExistException;
import com.rappala.sis.repositories.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(path = "/courses")
public class CourseController {

    Logger log = LoggerFactory.getLogger(CourseController.class);
    @Autowired
    private CourseRepository courseRepository;

    @PostMapping(path = "/addcourse")
    public @ResponseBody
    Courses addCourse(@RequestBody Courses courses) {
        String courseName = courses.getName();
        if(!courseRepository.existsByName(courseName)) {
            Courses addCourse = courseRepository.save(courses);
            System.out.println("added course into courses" + addCourse);
            return addCourse;
        }
        throw new CourseExistException(courseName);
    }

    @GetMapping(path="/allcourses")
    public @ResponseBody Iterable<Courses> getAllCourses(){
        Iterable<Courses> allCourses = courseRepository.findAll();
        System.out.println("retrieved all courses");
        return allCourses;
    }

    @GetMapping(path = "/course/name/{name}")
    public @ResponseBody Courses getCourseByName(@PathVariable final String name){
        System.out.println("request to get course" + name);
        Courses getCourse = courseRepository.findByName(name);
        System.out.println("retrieved course" + getCourse);
        return getCourse;
    }

    @PutMapping(path = "/updatecourse")
    public @ResponseBody Courses updateCourse(@RequestBody Courses newCourse){
        Optional<Courses> getCourse = courseRepository.findById(newCourse.getId());
        if(!getCourse.isPresent()){
            System.out.println("Course " + newCourse.getName() + " does not exists!");
            return null;
        }
        return courseRepository.save(newCourse);
    }

    @DeleteMapping(path = "/deletecourse/{id}")
    public @ResponseBody void deleteCourse(@PathVariable final UUID id){
        Optional<Courses> course = courseRepository.findById(id);
        if(course.isPresent()){
            courseRepository.deleteById(id);
        }
        else {
            throw new CourseDoesNotExistsException();
        }
    }

    @PatchMapping(path="/coursename/{id}")
    public @ResponseBody
    Courses patchCourseName(@PathVariable final UUID id, @RequestBody CoursePatchDTO coursePatchDto){
        log.info("patchCourseName: Course Id {}", id);
        Optional<Courses> existCourse = courseRepository.findById(id);
        if(existCourse.isPresent()){
            Courses retrievedCourse = existCourse.get();
            retrievedCourse.setName(coursePatchDto.getName());
            Courses updatedCourse =  courseRepository.save(retrievedCourse);
            System.out.println("updatedCourse: update name " + updatedCourse.getName());
            return updatedCourse;
        }
        else{
            throw new CourseDoesNotExistsException();
        }
    }
}
