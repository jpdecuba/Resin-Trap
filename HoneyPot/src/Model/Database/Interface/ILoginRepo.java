package Model.Database.Interface;

import Model.User;

public interface ILoginRepo {
	User Validate(User user);
	Boolean Logout(User user);
}
