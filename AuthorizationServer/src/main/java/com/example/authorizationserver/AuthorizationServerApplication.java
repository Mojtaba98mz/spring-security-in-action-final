package com.example.authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
		SecureRandom secureRandom = new SecureRandom();
		byte [] code = new byte[32];
		secureRandom.nextBytes(code);
		String codeVerifier = Base64.getUrlEncoder()
				.withoutPadding()
				.encodeToString(code);
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte [] digested = messageDigest.digest(codeVerifier.getBytes());
		String codeChallenge = Base64.getUrlEncoder()
				.withoutPadding()
				.encodeToString(digested);

		System.out.println("Code Verifier:"+codeVerifier);
		System.out.println("Code Challenge:"+codeChallenge);
	}

}