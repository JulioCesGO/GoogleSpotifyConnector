package br.com.julioces;

import static org.junit.Assert.*;

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
import org.junit.Test;

public class conexaoSpotify {

	private static String CLIENT_ID = "630159fe1f484e329f7715e33e7c5a51";
	private static String CLIENT_SECRET = "420193c163774c719a8a2d545f8896ee";
	private static String REDIRECT_URL = "http://localhost:8000/callback";
	
	@Test
	public void test() {
		String url = GerarURLParaAutenticacao(CLIENT_ID, REDIRECT_URL, response_type, scope, access_type, state)
	}

	String GerarURLParaAutenticacao(String client_id, String redirect_uri, String response_type,
			String scope, String access_type, String state)
	{
		try {
			String url ="https://accounts.spotify.com/authorize";
			//HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("client_id", client_id));
			params.add(new BasicNameValuePair("redirect_uri", redirect_uri));
			params.add(new BasicNameValuePair("response_type",response_type));
			if (scope!= null)
				params.add(new BasicNameValuePair("scope", scope));
			params.add(new BasicNameValuePair("access_type", access_type));
			//params.add(new BasicNameValuePair("state", state));
			
			URI uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();

			return uri.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return "";
	}
	
	String ExchangeRefreshOrAccessToken(
			String code, String state,
			String client_id, 
			String client_secret, String redirect_url, String grant_type)
	{
		String retorno = null;
		try {
			String _url1 = "https://accounts.spotify.com/api/token";
			
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(_url1);
			httpPost.addHeader("Authorization", "Basic " + Base64.getEncoder() );
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("code",code));
			
			params.add(new BasicNameValuePair("redirect_uri", redirect_url));
			params.add(new BasicNameValuePair("grant_type",grant_type));
			httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			
			if (entity != null)
			{
				InputStream instream = entity.getContent();
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
		
		return retorno;

	}

	String UserInfo(String API_KEY)
	{
	}
	}
}
