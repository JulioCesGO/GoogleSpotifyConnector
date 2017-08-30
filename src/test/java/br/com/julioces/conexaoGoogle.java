package br.com.julioces;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

public class conexaoGoogle {

	@Test
	public void test() {
		GoogleConnectionFactory googleConnection = new GoogleConnectionFactory(
				"750857276898-1nrl3bt409oohokl0dlsni89q5ruig12.apps.googleusercontent.com", "7wqEHEn-icUGpY4NsBus-3le");
		OAuth2Operations oauth2Operation = googleConnection.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setScope("profile");
		params.setRedirectUri("http://localhost:8080/julioces/");
		String authorizeURL = oauth2Operation.buildAuthenticateUrl(params);
		
		System.out.println(authorizeURL);
		
	}

}
