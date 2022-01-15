
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
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudents;
    private static PreparedStatement dropStudent;    
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (STUDENTID, FIRSTNAME, LASTNAME) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList();
        try
        {
            getStudents = connection.prepareStatement("select * from app.student");
            resultSet = getStudents.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry allStudents = new StudentEntry(resultSet.getString("STUDENTID"),resultSet.getString("FIRSTNAME"),resultSet.getString("LASTNAME"));
                students.add(allStudents);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
  
    }
    
        public static StudentEntry getStudent(String studentID) 
    {
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try
        {
            getStudents = connection.prepareStatement("select * from app.student WHERE STUDENTID =" + "'" + studentID + "'");
            resultSet = getStudents.executeQuery();
            
            while(resultSet.next())
            {
                student = new StudentEntry(resultSet.getString("STUDENTID"),resultSet.getString("FIRSTNAME"),resultSet.getString("LASTNAME"));                
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;  
    }
        
        public static void dropStudent(String studentID){
            
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("DELETE FROM app.student WHERE STUDENTID = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }                
    }     
}
