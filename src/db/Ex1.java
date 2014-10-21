package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * 範例 how to connect with database
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
                System.out.println("資料庫連線測試成功！"); 
                conn.close();
            }
            
        } 
        catch(ClassNotFoundException e) { 
            System.out.println("找不到驅動程式類別"); 
            e.printStackTrace(); 
        } 
        catch(SQLException e) { 
            e.printStackTrace(); 
        } 

	}

}
