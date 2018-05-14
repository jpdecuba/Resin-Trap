package Model;

import Shared.Model.User;
import Shared.Model.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    User user1;
    User user2;

    @Before
    public void Setup(){
        user1 = new User("john doe", "password", UserRole.Admin, "codetest");
        user2 = new User(2, "johnny does", UserRole.Admin, 1);
    }


    @Test
    public void getId() {
        Assert.assertEquals(2, user2.getId());
    }

    @Test
    public void getName() {
        Assert.assertEquals("john doe", user1.getName());
    }

    @Test
    public void getPassword() {
        Assert.assertEquals("password", user1.getPassword());
    }

    @Test
    public void getRole() {
        Assert.assertEquals(UserRole.Admin, user1.getRole());
    }

    @Test
    public void getSession() {
        Assert.assertEquals(1, user2.getSession());
    }

    @Test
    public void setSession() {
        user2.setSession(0);
        Assert.assertEquals(0, user2.getSession());
    }

    @Test
    public void getCode() {
        Assert.assertEquals("codetest", user1.getCode());
    }

    @Test
    public void setCode() {
        user1.setCode("testcode");
        Assert.assertEquals("testcode", user1.getCode());
    }
}