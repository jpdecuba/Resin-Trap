package Server.Repositories.Interface;

import Shared.Model.User;


public interface ILoginRepo {
	User Validate(User user);
	boolean Logout(String name);
	boolean Register(User user);
	boolean AddEmail(String email, int userId);
	boolean DeleteEmail(String email, int userId);
}
