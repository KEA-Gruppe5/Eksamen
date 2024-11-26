package kea.eksamen.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class PasswordEncoder {

    public String encode(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Error encoding password", e);
        }
    }

    public boolean matches(String rawPassword, String encodedPassword){
        String newEncodedPassword = encode(rawPassword);
        return newEncodedPassword.equals(encodedPassword);
    }
}
