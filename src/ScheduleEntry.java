/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author irfankhan
 */

import java.sql.Timestamp;

public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp Timestamp;

    public ScheduleEntry(String semester, String courseCode, String studentID, String status, Timestamp Timestamp) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.Timestamp = Timestamp;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String newStatus){
        status = newStatus;
    }

    public Timestamp getTimestamp() {
        return Timestamp;
    }
    
    
    
    
}
