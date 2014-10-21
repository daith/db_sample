package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqlConnector {

	private static Properties props = null;
	// Database objects
	private Connection con = null;
	private Statement stat = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	
	static {
		Properties props = new Properties();
		FileInputStream fis = null;
		String filePath = Ex2.class.getResource("config/config.properties")
				.getPath();
		try {
			fis = new FileInputStream(filePath);
			props.load(fis);

			// load the Driver Class
			Class.forName(props.getProperty("DB_DRIVER_CLASS"));
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	public SqlConnector() {
		try {
			// create the connection now
			con = DriverManager.getConnection(props.getProperty("DB_URL"),
					props.getProperty("DB_USERNAME"),
					props.getProperty("DB_PASSWORD"));
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public Statement getStatement() {
		if (null == this.stat) {
			try {
				stat = this.con.createStatement();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return stat;
	}

	// �d�߸��
	// �i�H�ݬݦ^�ǵ��G���Ψ��o��Ƥ覡
	public ResultSet SelectTable(String selectSQL) {
		try {
			stat = getStatement();
			rs = stat.executeQuery(selectSQL);

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			sqlClose();
		}
		return rs;
	}

	// �إ�table���覡
	// �i�H�ݬ�Statement���ϥΤ覡
	public void createTable(String sql) {
		try {
			stat = getStatement();
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		} finally {
			sqlClose();
		}
	}

	// �R��Table,
	// ��إ�table�ܹ�
	public void dropTable(String sql) {
		createTable(sql);
	}

	public void sqlClose() {
		this.Close();
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
}
