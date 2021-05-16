package com.rappala.sis.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Student {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID studentId;

    private String studentName;
    private String mobileNumber;


    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(
            name = "student_courses",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "studentId")},
            inverseJoinColumns = {@JoinColumn(name="course_id", referencedColumnName = "id")}
    )

    private List<Courses> courses;


    @OneToMany(mappedBy = "student",cascade=CascadeType.ALL)
    private List<Address> address;

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {

        this.address = address;
        address.stream().forEach(adr -> adr.setStudent(this));
    }


}
