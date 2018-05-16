package Server.Repositories.Repository;

import Shared.Model.User;
import Server.Repositories.Interface.ILoginRepo;


public class LoginRepo implements ILoginRepo {

	private ILoginRepo context;

	public LoginRepo(ILoginRepo context)
	{
		this.context = context;
	}

	public User Validate(User user) {
		return this.context.Validate(user);
	}

	public boolean Logout(String name) {
		return this.context.Logout(name);
	}

    public boolean Register(User user) { return this.context.Register(user); }

	public boolean AddEmail(String email, int userId) { return this.context.AddEmail(email, userId); }
	public boolean DeleteEmail(String email, int userId) { return this.context.DeleteEmail(email, userId); }
}
