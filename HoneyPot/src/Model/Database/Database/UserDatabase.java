package Model.Database.Database;

import Model.Database.Interface.ILoginRepo;
import Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabase implements ILoginRepo {

	@Override
	public User Validate(User user) {
		User user1 = null;
		try
		{
			String sql = "SELECT id, roleId FROM Account WHERE name = ? AND password = ? AND [online] = 0";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				user1 = new User(rs.getInt(1), user.getName(), rs.getInt(2), 1);
				sql = "UPDATE Account SET [online] = ? WHERE name = ?";
				statement = Database.connection().prepareStatement(sql);
				statement.setInt(1, 1);
				statement.setString(2, user.getName());
				statement.execute();
				return user1;
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean Logout(String name) {
		try
		{
			String sql = "UPDATE Account SET [online] = 0 WHERE name = ?";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, name);
			statement.execute();
			return true;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean Register(User user) {
		try{
			String sql = "INSERT INTO Account (name, password, roleID, [online]) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getRoleId());
			statement.setInt(4, 0);
			statement.execute();
			return true;
		} catch(SQLException sqle){
			return false;
		}
	}
}
