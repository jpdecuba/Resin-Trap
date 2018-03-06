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
}
