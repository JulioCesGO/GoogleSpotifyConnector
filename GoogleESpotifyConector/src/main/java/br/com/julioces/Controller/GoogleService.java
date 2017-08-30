package br.com.julioces.Controller;

import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.oauth2.UserInfo;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class GoogleService implements IGoogleService {

	GoogleConnectionFactory googleConnection = null;
	private static String clientId = "750857276898-1nrl3bt409oohokl0dlsni89q5ruig12.apps.googleusercontent.com";
	private static String key = "AIzaSyAuhkeREPdlVTQJdaHdto94wfqq-zzBjn0";
	OAuth2Operations oauth2Operation;
	
	public GoogleService() {
		
		googleConnection = new GoogleConnectionFactory(clientId, key);
		oauth2Operation = googleConnection.getOAuthOperations();
	}
	
	@Override
	public String authorizeURL()	
	{
		OAuth2Parameters params = new OAuth2Parameters();
		params.setScope("profile");
		params.setRedirectUri("http://localhost:8080/julioces/");
		return  oauth2Operation.buildAuthenticateUrl(params);
	}

	@Override
	public UserInfo getUserInfo(String codeGoogle) {
		AccessGrant accessGrant = oauth2Operation.exchangeCredentialsForAccess(codeGoogle, "http://localhost:8080/julioces/", null);
		
		Google google = new GoogleTemplate(accessGrant.getAccessToken());
		
		return google.oauth2Operations().getUserinfo();
		
	}
}
