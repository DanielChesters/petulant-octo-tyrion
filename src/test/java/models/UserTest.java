package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import ninja.NinjaTest;
import ninja.utils.Crypto;
import ninja.utils.NinjaMode;
import ninja.utils.NinjaPropertiesImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserTest extends NinjaTest {

    private Crypto cryto = new Crypto(new NinjaPropertiesImpl(NinjaMode.test));

    @Test
    public void testConnect() {
        String email = "tarou@test.com";
        String password = "secret";
        User user = User.connect(email, cryto.signHmacSha1(password));
        assertNotNull(user);
        assertNotNull(user.getFullname());
        assertEquals(Long.valueOf(1), user.getId());
        assertFalse(user.isAdmin());
        assertEquals(email, user.getEmail());
        assertEquals(cryto.signHmacSha1(password), user.getPassword());
    }

}
