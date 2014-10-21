package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Ex3 {
	// Database objects
	private Connection con = null;
	// �s��object
	private Statement stat = null;
	// ����,�ǤJ��sql������r��
	private ResultSet rs = null;
	// ���G��
	private PreparedStatement pst = null;
	
	// ����,�ǤJ��sql���w�x���r��,�ݭn�ǤJ�ܼƤ���m
	// ���Q��?�Ӱ��Х�
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

	// �إ�table���覡
	// �i�H�ݬ�Statement���ϥΤ覡
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

	// �s�W���
	// �i�H�ݬ�PrepareStatement���ϥΤ覡
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

	// �R��Table,
	// ��إ�table�ܹ�
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

	// �d�߸��
	// �i�H�ݬݦ^�ǵ��G���Ψ��o��Ƥ覡
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

	// ����ϥΧ���Ʈw��,�O�o�n�����Ҧ�Object
	// �_�h�b����Timeout��,�i��|��Connection poor�����p
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
		// ���ݬݬO�_���`
		Ex3 test = new Ex3();
		test.dropTable();
		test.createTable();
		test.insertTable("yku", "12356");
		test.insertTable("yku2", "7890");
		test.SelectTable();

	}
}
