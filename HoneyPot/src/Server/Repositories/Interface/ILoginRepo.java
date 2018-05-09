package Server.Repositories.Interface;

import Client.Model.User;


public interface ILoginRepo {
	User Validate(User user);
	boolean Logout(String name);
	boolean Register(User user);
}
