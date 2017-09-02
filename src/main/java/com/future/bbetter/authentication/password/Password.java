package com.future.bbetter.authentication.password;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 用於密碼的明碼加密與驗證
 * @author Charles
 * @date 2017年9月2日 下午2:40:10
 */
public class Password {
    public static PasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final int workload = 12;
    
    /**
	 * 將密碼(明碼)加密
	 * @author Charles
	 * @date 2017年9月2日 下午2:49:04
	 * @param plaintextPassword
	 * @return String
	 */
    public static String encrypt(String plaintextPassword) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(plaintextPassword, salt);
        return(hashed_password);
    }
    
    /**
	 * 驗證密碼(明碼)與加密過的密碼是否一樣
	 * @author Charles
	 * @date 2017年9月2日 下午2:49:54
	 * @param param1 type 描述
	 * @return boolean
	 */
    public static boolean isValidatePassword(String password, String hashPassword) {
        return BCrypt.checkpw(password, hashPassword);
    }
    
    /**
	 * 測試
	 * @author Charles
	 * @date 2017年9月2日 下午2:41:03
	 */
    public static void main(String[] args) {
    	//test
        String tmp = "0000";	//$2a$12$dQevIWsWVAVrkzFDk6Z2LOPJWpsNGEEbkFqbltCrQ3kYXuLn2HEpC
        String encryptor  = encrypt(tmp);
        System.out.println("tmp:" + tmp + ", encryptor:" + encryptor );
        
        boolean isValidatePassword = isValidatePassword( tmp, encryptor);
        System.out.println("isValidatePassword:" + isValidatePassword);
        System.out.println("----------");
        String method = "getUserId";
        String MethodOfSetOrGet = (method.toString()).substring(0, 3);
		String property = (method.toString()).substring(3, 4).toLowerCase() + (method.toString()).substring(4);
		
		System.out.println("MethodOfSetOrGet:" + MethodOfSetOrGet);
		System.out.println("property:" + property);
    }
}