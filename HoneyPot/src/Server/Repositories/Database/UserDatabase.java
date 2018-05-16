package Server.Repositories.Database;

import Shared.Model.User;
import Shared.Model.UserRole;
import Server.Repositories.Interface.ILoginRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class UserDatabase implements ILoginRepo {

	@Override
	public User Validate(User user) {
		try
		{
			String sql = "SELECT id, roleId FROM Account WHERE name = ? AND password = ?";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				User user1 = new User(rs.getInt(1), user.getName(), UserRole.values()[rs.getInt(2)], 1);
				if(user1.getRole() == UserRole.Admin)
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

				ArrayList<String> emails = new ArrayList<>();
				sql = "SELECT address FROM Email WHERE userId = ?";
				statement = Database.connection().prepareStatement(sql);
				statement.setInt(1, user1.getId());
				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next())
				{
					emails.add(resultSet.getString(1));
				}
				user1.setMsgEmail(emails);

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
			PreparedStatement statement = Database.connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getRole().getValue());
			statement.setInt(4, 0);
			int id = 0;
			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();

			if(result.next() && result != null){
				id = result.getInt(1);
				System.out.println("Key: " + id);
				if(id == 0)
				{
					return false;
				}
			}

			String newCode = user.getCode();
			if(newCode == null)
			{
				newCode = "";
			}
			if(user.getRole() == UserRole.Admin) {
				while (newCode == "") {
					String code = StringGenerator();
					if (!SearchCode(code)) {
						newCode = code;
					}
				}
			}

			sql = "INSERT INTO Code (code) VALUES (?)";
			statement = Database.connection().prepareStatement(sql);
			statement.setString(1, newCode);
			statement.execute();

			if(!CreateAccountCode(id, newCode)) {
				return false;
			}
			if(!AddEmail(user.getMsgEmail().get(0), id))
			{
				return false;
			}
			return true;

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

	@Override
	public boolean AddEmail(String email, int id)
	{
		try {
			String sql = "INSERT INTO Email (address, userId) VALUES (?, ?)";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, email);
			statement.setInt(2, id);
			statement.execute();
			return true;
		}
		catch (SQLException e) {}
		return false;
	}

	@Override
	public boolean ChangePassword(String password, int userId) {
		try {
			String sql = "UPDATE Account SET password = ? WHERE id = ?";
			PreparedStatement statement = Database.connection().prepareStatement(sql);
			statement.setString(1, password);
			statement.setInt(2, userId);
			statement.execute();
			return true;
		}
		catch (SQLException e) {}
		return false;
	}

}
