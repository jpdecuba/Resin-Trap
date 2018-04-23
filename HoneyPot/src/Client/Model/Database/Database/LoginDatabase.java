package Client.Model.Database.Database;

import Client.Model.Database.Interface.ILoginRepo;
import Client.Model.User;

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
    public boolean RegisterAdmin(User user) {
        if (!list.contains(user)){
            list.add(user);
            return true;
        }
        else return false;
    }

    public ArrayList<User> getList() {
        return list;
    }
    //
}
