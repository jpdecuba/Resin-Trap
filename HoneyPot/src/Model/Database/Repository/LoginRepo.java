package Model.Database.Repository;

import Model.Database.Interface.ILoginRepo;
import Model.User;

public class LoginRepo{

	private ILoginRepo context;

	public LoginRepo(ILoginRepo context)
	{
		this.context = context;
	}

	public User Validate(User user) {
		return this.context.Validate(user);
	}


	public Boolean Logout(String name) {
		return this.context.Logout(name);
	}
}
