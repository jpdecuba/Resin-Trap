package Model;

public class User {
	private int id;
	private String name;
	private String password;
	private int roleId;
	private int session;

	public User(String name, String password)
	{
		this.name = name;
		this.password = password;
	}

	public User(int id, String name, String password, int roleId, int session)
	{
		this(name, password);
		this.id = id;
		this.roleId = roleId;
		this.session = session;
	}
}
