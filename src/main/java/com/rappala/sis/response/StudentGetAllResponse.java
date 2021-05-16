package com.rappala.sis.response;

import java.util.List;
import java.util.UUID;

public class StudentGetAllResponse {
    private UUID studentId;
    private String studentName;
    private String mobileNumber;
    private List<CourseResponse> courses;
    private List<AddressResponse> address;

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

    public List<CourseResponse> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseResponse> courses) {
        this.courses = courses;
    }

    public List<AddressResponse> getAddress() {
        return address;
    }

    public void setAddress(List<AddressResponse> address) {
        this.address = address;
    }
}

