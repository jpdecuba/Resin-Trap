package Server.Repositories.Repository;

import Client.Model.User;
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

	public boolean RegisterAdmin(User user)
	{
		return this.context.RegisterAdmin(user);
	}

	public boolean Logout(String name) {
		return this.context.Logout(name);
	}

    @Override
    public boolean Register(User user) { return this.context.Register(user); }
}
