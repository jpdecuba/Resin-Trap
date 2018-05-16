package Server.Repositories.Database;

import Shared.Model.User;
import Server.Repositories.Interface.ILoginRepo;

import java.util.ArrayList;

public class LoginDatabase implements ILoginRepo {
    ArrayList<User> list = new ArrayList<>();
    @Override
    public User Validate(User user) {
        if (list.contains(user) && user.getSession() == 0)
        {
            user.setSession(1);
            return user;
        } else return null;
    }

    @Override
    public boolean Logout(String name) {
        for (User user :
                list) {
            if (user.getName().equals(name)){
                user.setSession(0);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean Register(User user) {
        if (!list.contains(user)){
            list.add(user);
            return true;
        }
        else return false;
    }

    @Override
    public boolean AddEmail(String email, int userId) {
        return false;
    }

    @Override
    public boolean ChangePassword(String password, int userId) {
        return false;
    }

    public ArrayList<User> getList() {
        return list;
    }
    //
}
