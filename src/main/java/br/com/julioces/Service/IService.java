package br.com.julioces.Service;

import org.springframework.social.google.api.oauth2.UserInfo;

public interface IService<T> {

	String GerarURLAuthenticacao(T entity);

	T ExchangeRefreshOrAccessToken(T entity);
}
