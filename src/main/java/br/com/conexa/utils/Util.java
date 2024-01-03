package br.com.conexa.utils;

import br.com.conexa.domain.user.RegisterDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Util {
    public static String encryptedPassword(RegisterDTO data){
        return new BCryptPasswordEncoder().encode(data.senha());
    }
}
