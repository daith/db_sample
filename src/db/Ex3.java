package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Ex3 {
	// Database objects
	private Connection con = null;
	// 連接object
	private Statement stat = null;
	// 執行,傳入之sql為完整字串
	private ResultSet rs = null;
	// 結果集
	private PreparedStatement pst = null;
	
	// 執行,傳入之sql為預儲之字申,需要傳入變數之位置
	// 先利用?來做標示
	private String dropdbSQL = "DROP TABLE User ";
	private String createdbSQL = "CREATE TABLE User (" + "    id     INTEGER "
			+ "  , name    VARCHAR(20) " + "  , passwd  VARCHAR(20))";

	private String insertdbSQL = "insert into User(id,name,passwd) "
			+ "select ifNULL(max(id),0)+1,?,? FROM User";

	private String selectSQL = "select * from User ";
	

	public Ex3() {
		Properties props = new Properties();
        FileInputStream fis = null;
        String filePath = Ex2.class.getResource("config/config.properties").getPath();
        try {
            fis = new FileInputStream(filePath);
            props.load(fis);
 
            // load the Driver Class
            Class.forName(props.getProperty("DB_DRIVER_CLASS"));
 
            // create the connection now
            con = DriverManager.getConnection(props.getProperty("DB_URL"),
                    props.getProperty("DB_USERNAME"),
                    props.getProperty("DB_PASSWORD"));
            
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

	// 建立table的方式
	// 可以看看Statement的使用方式
	public void createTable() {
		try {
			stat = con.createStatement();
			stat.executeUpdate(createdbSQL);
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	// 新增資料
	// 可以看看PrepareStatement的使用方式
	public void insertTable(String name, String passwd) {
		try {
			pst = con.prepareStatement(insertdbSQL);

			pst.setString(1, name);
			pst.setString(2, passwd);
			pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("InsertDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	// 刪除Table,
	// 跟建立table很像
	public void dropTable() {
		try {
			stat = con.createStatement();
			stat.executeUpdate(dropdbSQL);
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	// 查詢資料
	// 可以看看回傳結果集及取得資料方式
	public void SelectTable() {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectSQL);
			System.out.println("ID\t\tName\t\tPASSWORD");
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t\t"
						+ rs.getString("name") + "\t\t"
						+ rs.getString("passwd"));
			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	// 完整使用完資料庫後,記得要關閉所有Object
	// 否則在等待Timeout時,可能會有Connection poor的狀況
	private void Close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stat != null) {
				stat.close();
				stat = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			System.out.println("Close Exception :" + e.toString());
		}
	}

	public static void main(String[] args) {
		// 測看看是否正常
		Ex3 test = new Ex3();
		test.dropTable();
		test.createTable();
		test.insertTable("yku", "12356");
		test.insertTable("yku2", "7890");
		test.SelectTable();

	}
}
