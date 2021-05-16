package com.rappala.sis.repositories;

import com.rappala.sis.entities.Courses;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CourseRepository extends CrudRepository<Courses, UUID>{

    Courses findByName(String name);
    boolean existsByName(String name);
}


