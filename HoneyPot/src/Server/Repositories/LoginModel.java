package Server.Repositories;

import Shared.Model.User;
import Server.Repositories.Database.UserDatabase;
import Server.Repositories.Repository.LoginRepo;


public class LoginModel {

	private LoginRepo loginRepo;

	public LoginModel()
	{
		loginRepo = new LoginRepo(new UserDatabase());
	}

	public User Login(User user)
	{
		return this.loginRepo.Validate(user);
	}

	public boolean Logout(String name){ return this.loginRepo.Logout(name); }

	public boolean Register(User user){ return loginRepo.Register(user); }

	public boolean AddEmail(String email, int userId) { return loginRepo.AddEmail(email, userId); }

}
