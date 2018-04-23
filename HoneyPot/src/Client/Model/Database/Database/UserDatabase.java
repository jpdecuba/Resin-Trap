package Client.Model.Database.Database;

import Client.Model.Database.Interface.ILoginRepo;
import Client.Model.User;
import Client.Model.UserRole;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class UserDatabase implements ILoginRepo {

	@Override
	public User Validate(User user) {
		try
		{
			String sql = "SELECT id, roleId FROM Account WHERE name = ? AND password = ? AND [online] = 0";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				User user1 = new User(rs.getInt(1), user.getName(), UserRole.values()[rs.getInt(2)], 1);
				if(user.getRole() == UserRole.Admin)
				{
					sql = "SELECT code FROM Code WHERE id IN (SELECT codeID FROM AccountCode WHERE accountID = ?)";
					statement = Database.connection().prepareStatement(sql);
					statement.setInt(1, user1.getId());
					ResultSet sk = statement.executeQuery();
					if(sk.next())
					{
						user1.setCode(sk.getString(1));
					}
				}
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
			if(SearchCode(user.getCode())) {
				String sql = "INSERT INTO Account (name, password, roleID, [online]) VALUES (?, ?, ?, ?)";
				PreparedStatement statement = Database.connection().prepareStatement(sql);
				statement.setString(1, user.getName());
				statement.setString(2, user.getPassword());
				statement.setInt(3, user.getRole().getValue());
				statement.setInt(4, 0);
				statement.execute();

				int id = GetID(user.getName());
				if(id == 0)
				{
					return false;
				}
				if(!CreateAccountCode(id, user.getCode()))
				{
					return false;
				}

				return true;
			}
		} catch(SQLException sqle){
		}
		return false;
	}

	@Override
	public boolean RegisterAdmin(User user) {
		try{
			String sql = "INSERT INTO Account (name, password, roleID, [online]) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getRole().getValue());
			statement.setInt(4, 0);
			statement.execute();

			String newCode = "";
			while (newCode == "")
			{
				String code = StringGenerator();
				if(!SearchCode(code))
				{
					newCode = code;
				}
			}

			int id = GetID(user.getName());
			if(id == 0)
			{
				return false;
			}

			sql = "INSERT INTO Code (code) VALUES (?)";
			statement = Database.connection().prepareStatement(sql);
			statement.setString(1, newCode);
			statement.execute();

			if(CreateAccountCode(id, newCode)) {
				return true;
			}
		} catch(SQLException sql){

		}
		return false;
	}

	private int GetID(String name)
	{
		int id = 0;
		try {
			String sql = "SELECT id FROM Account WHERE name = ?";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			} else {
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	private boolean CreateAccountCode(int id, String code)
	{
		try {
			String sql = "SELECT id FROM Code WHERE code = ?";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, code);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				int codeId = rs.getInt(1);
				sql = "INSERT INTO AccountCode (codeID, accountID) VALUES (?, ?)";
				statement = Database.connection().prepareStatement(sql);
				statement.setInt(1, codeId);
				statement.setInt(2, id);
				statement.execute();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean SearchCode(String code)
	{
		try {
			String sql = "SELECT code FROM Code WHERE code = ?";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, code);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String StringGenerator()
	{
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=?<>,.{}[]";
		StringBuilder returnstring = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 25; i++)
		{
			returnstring.append(characters.charAt(random.nextInt(characters.length())));
		}
		return returnstring.toString();
	}
	//
}
