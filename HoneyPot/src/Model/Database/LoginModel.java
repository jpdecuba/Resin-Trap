package Model.Database;

import Model.Database.Database.UserDatabase;
import Model.Database.Repository.LoginRepo;
import Model.User;

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
