package br.com.julioces.service;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IService<T> {

	String GerarURLAuthenticacao(T entity);

	T ExchangeRefreshOrAccessToken(T entity) throws ServiceAccessTokenException;
}
