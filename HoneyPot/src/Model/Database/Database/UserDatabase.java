package Model.Database.Database;

import Model.Database.Interface.ILoginRepo;
import Model.User;

public class UserDatabase implements ILoginRepo {

	@Override
	public User Validate(User user) {
		
		return null;
	}

	@Override
	public Boolean Logout(User user) {
		return null;
	}
}
