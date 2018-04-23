package Model.Database.Repository;

import Client.Model.Database.Database.LoginDatabase;
import Client.Model.Database.Repository.LoginRepo;
import Client.Model.User;
import Client.Model.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginRepoTest {
    LoginRepo repo;
    User admin;
    User user;

    @Before
    public void setUp() throws Exception {
        repo = new LoginRepo(new LoginDatabase());
        admin = new User("testadmin", "testpassword2", UserRole.Admin, "123456789");
        user = new User("testuser", "testpassword", UserRole.User, "123456789");
        repo.RegisterAdmin(admin);
        repo.Register(user);
    }

    @Test
    public void validate() {
        Assert.assertEquals(0, admin.getSession());
        repo.Validate(admin);
        Assert.assertEquals(1, admin.getSession());
    }

    @Test
    public void logout() {
        repo.Validate(admin);
        Assert.assertEquals(1, admin.getSession());
        repo.Logout("testadmin");
        Assert.assertEquals(0, admin.getSession());
    }

}