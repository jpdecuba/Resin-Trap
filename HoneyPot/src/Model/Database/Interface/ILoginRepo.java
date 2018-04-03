package Model.Database.Interface;

import Model.User;

public interface ILoginRepo {
	User Validate(User user);
	boolean Logout(String name);
	boolean Register(User user);
	boolean RegisterAdmin(User user);
}
