package models;

import static org.junit.Assert.*;
import ninja.NinjaTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserTest extends NinjaTest {

    @Test
    public void testConnect() {
        String email = "tarou@test.com";
        String password = "secret";
        User user = User.connect(email, password);
        assertNotNull(user);
        assertNotNull(user.getFullname());
        assertEquals(Long.valueOf(1), user.getId());
        assertFalse(user.isAdmin());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

}
