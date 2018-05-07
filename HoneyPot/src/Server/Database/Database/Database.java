package Server.Database.Database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
	public static Connection connection(){
		Connection con = null;
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection("jdbc:sqlserver://bp4.database.windows.net;database=achmeaHoneypot", "dbi365995", "typo123T");
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return con;
	}
}
