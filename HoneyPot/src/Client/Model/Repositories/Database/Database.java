package Client.Model.Repositories.Database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
	public static Connection connection(){
		Connection con = null;
		Properties properties = new Properties();
		try
		{
			InputStream input = new FileInputStream(new File("config.properties").getAbsolutePath());
			properties.load(input);

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(properties.getProperty("path"), properties.getProperty("user"), properties.getProperty("password"));
		}
		catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
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
