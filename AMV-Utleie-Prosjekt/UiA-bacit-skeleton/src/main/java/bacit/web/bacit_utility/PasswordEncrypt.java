package bacit.web.bacit_utility;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncrypt {
    private final static String PASSWORD_SECRET = "12Hr!ksl_ww%123!";

    public static String encrypt(String password) {
        return DigestUtils.md5Hex(password + PASSWORD_SECRET).toUpperCase();
    }
}
