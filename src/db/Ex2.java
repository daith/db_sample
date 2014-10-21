package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * 基於範例1的挑整 how to connect with database
 * preparation
 * 1. db set up
 * 2. jdbc connector(.jar) import
 * 3.  user / password
 * 
 * 將上述 uer / password / url 存放至.properties
 * 
 * */
public class Ex2 {

	public static void main(String[] args) {
		Properties props = new Properties();
        FileInputStream fis = null;
        String filePath = Ex2.class.getResource("config/config.properties").getPath();
        try {
            fis = new FileInputStream(filePath);
            props.load(fis);
 
            // load the Driver Class
            Class.forName(props.getProperty("DB_DRIVER_CLASS"));
 
            // create the connection now
            Connection conn = DriverManager.getConnection(props.getProperty("DB_URL"),
                    props.getProperty("DB_USERNAME"),
                    props.getProperty("DB_PASSWORD"));
            
            if(conn != null && !conn.isClosed()) {
                System.out.println("資料庫連線測試成功！"); 
                conn.close();
            }
            
        } catch (IOException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( ClassNotFoundException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       

	}

}
