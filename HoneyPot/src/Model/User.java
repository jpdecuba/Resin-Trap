package Model;

import java.io.Serializable;

public class User implements Serializable{
	private int id;
	private String name;
	private String password;
	private UserRole role;
	private int session;

	private String code;

	public User(String name, String password)
	{
		this.name = name;
		this.password = password;
	}

	public User(String name, String password, UserRole role)
	{
		this(name, password);
		this.role = role;
	}

	public User(String name, String password, UserRole role, String code)
	{
		this(name, password, role);
		this.code = code;
	}

	public User(int id, String name, UserRole role, int session)
	{
		this(name, "", role);
		this.id = id;
		this.session = session;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public UserRole getRole() {
		return role;
	}

	public int getSession() {
		return session;
	}

	public void setSession(int session) {
		this.session = session;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {this.code = code; }
}
