package com.anlohse.minesweeper.commons.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("passwordEncoder2")
public class SHA256PasswordEncoder implements PasswordEncoder {

	@Value("${minesweeper.config.salty.password}")
	private String passwordSalty;
	
	@Override
	public String encode(CharSequence rawPassword) {
		return Base64.encodeBase64String(DigestUtils.sha256(passwordSalty + rawPassword.toString()));
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}

	public static String encodeNoSalt(String rawPassword) {
		return Base64.encodeBase64String(DigestUtils.sha256(rawPassword.toString()));
	}

	public static void main(String[] args) {
		System.out.println(encodeNoSalt("123"));
	}

}
