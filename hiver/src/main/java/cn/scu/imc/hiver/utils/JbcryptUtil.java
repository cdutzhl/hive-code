package cn.scu.imc.hiver.utils;

import org.mindrot.jbcrypt.BCrypt;

public class JbcryptUtil {


    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public static boolean match(String rawPassword, String encodedPassword){
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }


}
