package Client.Model.Repositories;

import Client.Model.Repositories.Database.UserDatabase;
import Client.Model.Repositories.Repository.LoginRepo;
import Client.Model.User;


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

	public boolean RegisterAdmin(User user) { return loginRepo.RegisterAdmin(user); }
}
