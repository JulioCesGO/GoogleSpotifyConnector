package br.com.julioces.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.annotation.Scope;

import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.julioces.api.Google;

@Service
@Scope
public class GoogleService implements IGooleService  {

	
	private static String clientId = "750857276898-1nrl3bt409oohokl0dlsni89q5ruig12.apps.googleusercontent.com";
	private static String key = "AIzaSyAuhkeREPdlVTQJdaHdto94wfqq-zzBjn0";
	

	/*
	 * (non-Javadoc)
	 * @see br.com.julioces.Service.IService#GerarURLAuthenticacao(java.lang.Object)
	 */
	@Override
	public String GerarURLAuthenticacao(Google entity) {
		try {
			String url ="https://accounts.google.com/o/oauth2/v2/auth";
			//HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("client_id", entity.getClient_id()));
			params.add(new BasicNameValuePair("redirect_uri", entity.getRedirect_uri()));
			params.add(new BasicNameValuePair("response_type", entity.getResponse_type()));
			params.add(new BasicNameValuePair("scope", entity.getScope()));
			params.add(new BasicNameValuePair("access_type", entity.getAccess_type()));
			//params.add(new BasicNameValuePair("state", state));
			
			URI uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();

			return uri.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return "";
	}

	@Override
	public Google ExchangeRefreshOrAccessToken(Google entity) {
		
		String retorno = null;
		try {
			String _url1 = "https://www.googleapis.com/oauth2/v4/token";
			
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(_url1);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("code", entity.getCode()));
			params.add(new BasicNameValuePair("client_id", entity.getClient_id()));
			params.add(new BasicNameValuePair("client_secret",entity.getClient_secret()));
			params.add(new BasicNameValuePair("redirect_uri", entity.getRedirect_uri()));
			params.add(new BasicNameValuePair("grant_type",entity.getGrant_type()));
			httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity Httpentity = response.getEntity();
			
			if (entity != null)
			{
				InputStream instream = Httpentity.getContent();
				try
				{
					StringWriter writer = new StringWriter();
					org.apache.commons.io.IOUtils.copy(instream, writer);
					retorno = writer.toString();
					
				}
				finally {
					instream.close();
				}
			}		
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return entity;
		
	}
}
