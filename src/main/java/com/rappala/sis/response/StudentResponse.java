package com.rappala.sis.response;


import java.util.List;
import java.util.UUID;

public class StudentResponse {
    private UUID studentId;
    private List<String> courseId;
    private List<String> addressId;

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public List<String> getCourseId() {
        return courseId;
    }

    public void setCourseId(List<String> courseId) {
        this.courseId = courseId;
    }

    public List<String> getAddressId() {
        return addressId;
    }

    public void setAddressId(List<String> addressId) {
        this.addressId = addressId;
    }
}

