
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement getCourseCodes; 
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (SEMESTER, COURSECODE, DESCRIPTION, SEATS) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getCourseDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<CourseEntry> getAllCourses(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList();
        try
        {
            getCourseList = connection.prepareStatement("select * from app.course WHERE SEMESTER =" + "'" + semester + "'");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                CourseEntry allCourse = new CourseEntry(semester, resultSet.getString("COURSECODE"),resultSet.getString("DESCRIPTION"),resultSet.getInt("SEATS"));
                courses.add(allCourse);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;        
    } 
    
    public static int getCourseSeats(String semester, String courseCode){
        
        connection = DBConnection.getConnection();
        int courseSeat = 0;
        try
        {
            getCourseSeats = connection.prepareStatement("select SEATS from app.course where SEMESTER =" + "'" + semester + "'" + "AND COURSECODE =" + "'" + courseCode + "'");
            resultSet = getCourseSeats.executeQuery();
            
            while(resultSet.next())
            {
                courseSeat = resultSet.getInt("SEATS");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        System.out.print(courseSeat);
        return courseSeat;            
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester) {
        
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList();
        try
        {
            getCourseCodes = connection.prepareStatement("select COURSECODE from app.course WHERE SEMESTER =" + "'" + semester + "'");
            resultSet = getCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString("COURSECODE"));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
        
    }
    
    public static void dropCourse(String semester, String courseCode){
        
        connection = DBConnection.getConnection();
        try
        {
            dropCourse = connection.prepareStatement("DELETE FROM app.course WHERE SEMESTER = ? and COURSECODE = ?");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }                
    }     

//    static ArrayList<CourseEntry> getCourseSeats(String currentSemester, int course) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }    
}
