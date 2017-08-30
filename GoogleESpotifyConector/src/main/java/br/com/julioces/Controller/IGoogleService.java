package br.com.julioces.Controller;

import org.springframework.social.google.api.oauth2.UserInfo;

public interface IGoogleService {

	String authorizeURL();

	UserInfo getUserInfo(String codeGoogle);
}
