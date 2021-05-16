package com.rappala.sis.repositories;



import com.rappala.sis.entities.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StudentRepository extends CrudRepository<Student, UUID> {
}
