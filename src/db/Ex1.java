package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * �d�� how to connect with database
 * preparation
 * 1. db set up
 * 2. jdbc connector(.jar) import
 * 3.  user / password
 * */
public class Ex1 {

	public static void main(String[] args) {
		String driver = "com.mysql.jdbc.Driver"; 
        String url = "jdbc:mysql://localhost:3306/myDB"; 
        String user = "root"; 
        String password = "a1s2d3f4"; 
        try { 
            Class.forName(driver); 
            Connection conn = 
               DriverManager.getConnection(url, 
                                  user, password);
 
            if(conn != null && !conn.isClosed()) {
                System.out.println("��Ʈw�s�u���զ��\�I"); 
                conn.close();
            }
            
        } 
        catch(ClassNotFoundException e) { 
            System.out.println("�䤣���X�ʵ{�����O"); 
            e.printStackTrace(); 
        } 
        catch(SQLException e) { 
            e.printStackTrace(); 
        } 

	}

}
