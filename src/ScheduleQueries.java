
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author irfankhan
 */
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addSchedule;
    private static PreparedStatement getSchedule;
    private static PreparedStatement getEnrolledSeats;
    private static PreparedStatement getScheduledStudents; 
    private static PreparedStatement getWaitlistedStudents;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement updateSchedule;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry schedule){
    
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("insert into app.schedule (SEMESTER, COURSECODE, STUDENTID, STATUS, TIMESTAMP) values (?,?,?,?,?)");
            addSchedule.setString(1, schedule.getSemester());
            addSchedule.setString(2, schedule.getCourseCode());
            addSchedule.setString(3, schedule.getStudentID());
            addSchedule.setString(4, schedule.getStatus());
            addSchedule.setTimestamp(5, schedule.getTimestamp());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleByStudent = new ArrayList();
        try
        {
            getSchedule = connection.prepareStatement("select SEMESTER, COURSECODE, STUDENTID, STATUS, TIMESTAMP from app.schedule where SEMESTER = ? and STUDENTID = ?");
            getSchedule.setString(1, semester);
            getSchedule.setString(2, studentID);
            resultSet = getSchedule.executeQuery();
            
            while(resultSet.next())
            {
                String currentCourseCode = resultSet.getString(2);
                String currentStatus = resultSet.getString(4);
                Timestamp timestamp = resultSet.getTimestamp(5);
                ScheduleEntry schedule = new ScheduleEntry(semester,currentCourseCode, studentID, currentStatus, timestamp);
                scheduleByStudent.add(schedule);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleByStudent;        
    }
    
    public static int getScheduledStudentCount(String semester, String courseCode){
        
        connection = DBConnection.getConnection();
        int courseEnrolledSeat = 0;
        try
        {
            getEnrolledSeats = connection.prepareStatement("select count(STUDENTID) from app.schedule where SEMESTER = ? and COURSECODE = ? and STATUS = ?");
            getEnrolledSeats.setString(1, semester);
            getEnrolledSeats.setString(2, courseCode);  
            getEnrolledSeats.setString(3, "S");
            resultSet = getEnrolledSeats.executeQuery();
            
            while(resultSet.next())
            {
                courseEnrolledSeat = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        System.out.print(courseEnrolledSeat);
        return courseEnrolledSeat;            
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
        
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduledStudents = new ArrayList<>();
        try
        {
            getScheduledStudents = connection.prepareStatement("select * from app.schedule where SEMESTER = ? and COURSECODE = ? AND STATUS = 'S' order by TIMESTAMP");
            getScheduledStudents.setString(1, semester);
            getScheduledStudents.setString(2, courseCode);  
            resultSet = getScheduledStudents.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                scheduledStudents.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        };
        return scheduledStudents;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) {
        
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlistedStudents = new ArrayList<>();
        try
        {
            getWaitlistedStudents = connection.prepareStatement("select * from app.schedule where SEMESTER = ? and COURSECODE = ? AND STATUS = 'W' order by TIMESTAMP");
            getWaitlistedStudents.setString(1, semester);
            getWaitlistedStudents.setString(2, courseCode);  
            resultSet = getWaitlistedStudents.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                waitlistedStudents.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        };
        return waitlistedStudents;
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
        
        connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("DELETE FROM app.schedule WHERE SEMESTER = ? and COURSECODE = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }                
    } 

    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
    
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("DELETE FROM app.schedule WHERE SEMESTER = ? and STUDENTID = ? and COURSECODE = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }           
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        
        connection = DBConnection.getConnection();
        try
        {
            updateSchedule = connection.prepareStatement("UPDATE app.schedule SET STATUS = ? WHERE SEMESTER = ? AND STUDENTID = ? AND COURSECODE = ?");
            updateSchedule.setString(1, entry.getStatus());
            updateSchedule.setString(2, semester);
            updateSchedule.setString(3, entry.getStudentID());
            updateSchedule.setString(4, entry.getCourseCode());
            updateSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }    
        
    }
        
}
